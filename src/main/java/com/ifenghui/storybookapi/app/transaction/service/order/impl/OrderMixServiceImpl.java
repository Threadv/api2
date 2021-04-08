package com.ifenghui.storybookapi.app.transaction.service.order.impl;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.app.transaction.dao.AbilityPlanOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.OrderMixDao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.service.PayAbilityPlanOrderService;
import com.ifenghui.storybookapi.app.transaction.service.PayVipOrderService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonOrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.*;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.exception.ApiNotThisUserException;
import com.ifenghui.storybookapi.style.OrderStatusStyle;
import com.ifenghui.storybookapi.style.OrderStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 不加事物注解 在需要的方法上加
 */
@Component
public class OrderMixServiceImpl implements OrderMixService {

    Logger logger= Logger.getLogger(OrderMixServiceImpl.class);

    @Autowired
    OrderMixDao orderMixDao;

    @Autowired
    AbilityPlanOrderDao planOrderDao;
    
    @Autowired
    PaySubscriptionOrderService paySubscriptionOrderService;

    @Autowired
    PaySerialStoryOrderService paySerialStoryOrderService;

    @Autowired
    PayStoryOrderService payStoryOrderService;

    @Autowired
    PayLessonOrderService payLessonOrderService;

    @Autowired
    PayActivityOrderService payActivityOrderService;

    @Autowired
    UserShareTradeRecordService userShareTradeRecordService;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    PayVipOrderService payVipOrderService;

    @Autowired
    PayAbilityPlanOrderService payAbilityPlanOrderService;

    @Override
    public void timingCancelOrder() {

        int pageNo=0;
        int pageSize=20;
        PageRequest pageRequest=new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.ASC,"id"));

        Page<OrderMix> orderMixPage = orderMixDao.findAllOrderMix(pageRequest);
        this.cancelOrderTiming(orderMixPage.getContent());

        for(int i=1;i<orderMixPage.getTotalPages();i++){
            pageRequest=new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.ASC,"id"));
                orderMixPage = orderMixDao.findAllOrderMix(pageRequest);
                this.cancelOrderTiming(orderMixPage.getContent());
        }
    }
    private void cancelOrderTiming(List<OrderMix> orderMixList){
        for (OrderMix o:orderMixList) {
            if((System.currentTimeMillis() - o.getCreateTime().getTime())/1000/60 >15){
                try{
                    this.cancelOrder(o.getUserId(),o.getOrderId(),RechargeStyle.getById(o.getOrderType()));
                }catch (Exception e){
                  logger.info(e);
                }
            }
        }
    }


    @Transactional
    @Override
    public OrderMix addOrderMix(OrderStyle orderStyle, Integer orderId, Integer userId) {
        OrderMix orderMix=orderMixDao.findOneByOrderIdAndType(orderId,orderStyle.getId());
        if(orderMix!=null){
            return orderMix;
        }
        orderMix=new OrderMix();
        orderMix.setCreateTime(new Date());
        orderMix.setIsDel(0);
        orderMix.setOrderId(orderId);
        orderMix.setUserId(userId);
        orderMix.setStatus(0);
        orderMix.setOrderType(orderStyle.getId());

        return orderMixDao.save(orderMix);
    }

    @Transactional
    @Override
    public OrderMix updateOrderMixStatus(OrderStyle orderStyle, Integer orderId, OrderStatusStyle status) {
        OrderMix orderMix = orderMixDao.findOneByOrderIdAndType(orderId,orderStyle.getId());
        orderMix.setStatus(status.getId());
        orderMixDao.save(orderMix);
        if(status.equals(OrderStatusStyle.PAY_SUCCESS)){
            this.checkNeedGiveShareTradeMoney(orderMix, orderStyle);
        }
        return orderMix;
    }

    @Override
    public OrderMix getOrderMixById(Integer id) {
        return orderMixDao.findOne(id);
    }

    @Transactional
    @Override
    public void checkNeedGiveShareTradeMoney(OrderMix orderMix, OrderStyle orderStyle){
        UserExtend userExtend = userExtendService.findUserExtendByUserId(orderMix.getUserId().longValue());
        if(userExtend == null || userExtend.getUserParentId().equals(0)){
            return ;
        }
        //如果总订单状态是支付成功
        //在没有使用券和兑换码的情况创建邀请好友订单
        if(orderMix.getStatus().equals(OrderStatusStyle.PAY_SUCCESS.getId())){
            OrderPayProcess orderPayProcess= this.getOrderByProcessByOrderType(RechargeStyle.getById(orderStyle.getId()));

            Integer orderAmount = orderPayProcess.getShareTradeAmount(orderMix.getOrderId());

            if(orderAmount != null && orderAmount > 0){
                userShareTradeRecordService.createUserShareTradeRecord(orderMix.getId(), orderMix.getUserId(), orderAmount);
            }
        }
    }

    @Transactional
    @Override
    public Page<OrderMix> getListByStatus(Integer userId, Integer status, Integer pageNo, Integer pageSize) {
        Pageable pageable=new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"createTime"));
        Page<OrderMix> orderMixes;
        if(status==null){
            orderMixes= orderMixDao.findByUserId(userId,pageable);
        }else{
            orderMixes= orderMixDao.findByUserIdAndStatus(userId,status,pageable);
        }
        for(OrderMix orderMix:orderMixes.getContent()){
            this.addDataToMix(orderMix,RechargeStyle.getById(orderMix.getOrderType()));
            //修改价格图片
            AbilityPlanOrder planOrder =  planOrderDao.findOne(orderMix.getOrderId());
            Random r = new Random();
            int tt = r.nextInt(1000000)+1000;
            if (null != orderMix.getAbilityPlanPrice()) {
                if (planOrder.getOnlineOnly() == 1) {
                    orderMix.getAbilityPlanPrice().setImgPath("http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/pay_ability_plan-online_card.png?tt=" + tt);
                } else {
                    orderMix.getAbilityPlanPrice().setImgPath("http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/pay_ability_plan_card.png?tt=" + tt);
                }
            }

        }

//        int  uid = Integer.parseInt(String.valueOf(userId));
//        AbilityPlanOrder planOrder =  planOrderDao.findAbilityPlanOrdersByUserIdAndPlaneType(uid,0);



        return orderMixes;
    }

    private OrderPayProcess getOrderByProcessByOrderType(RechargeStyle rechargeStyle){
        if(rechargeStyle == RechargeStyle.SERIAL){
            return this.paySerialStoryOrderService;
        }else if(rechargeStyle == RechargeStyle.SUBSCRIPTION){
            return this.paySubscriptionOrderService;
        }else if(rechargeStyle == RechargeStyle.BUY_STORY){
            return this.payStoryOrderService;
        }else if(rechargeStyle == RechargeStyle.LESSON){
            return this.payLessonOrderService;
        }else if(rechargeStyle == RechargeStyle.ACTIVITY_GOODS){
            return this.payActivityOrderService;
        } else if(rechargeStyle == RechargeStyle.BUY_SVIP){
            return this.payVipOrderService;
        } else if(rechargeStyle == RechargeStyle.BUY_ABILITY_PLAN){
            return this.payAbilityPlanOrderService;
        }

        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelOrder(Integer userId, Integer orderId, RechargeStyle type) throws ApiException {
        //还未实现,准备替代view中的功能
        OrderMix orderMix = orderMixDao.findOneByOrderIdAndType(orderId,type.getId());
        if(orderMix == null){
            throw new ApiNotFoundException("没有找到该订单！");
        }
        if(!userId.equals(orderMix.getUserId()) ){
            throw new ApiNotThisUserException("参数错误，用户与订单信息不符");
        }
        OrderPayProcess orderPayProcess=this.getOrderByProcessByOrderType(type);
        try{
            orderPayProcess.cancelOrder(orderId);
        }catch (Exception e){
            logger.info(e);
        }
        this.updateOrderMixStatus(OrderStyle.getById(type.getId()),orderId, OrderStatusStyle.PAY_BACK);

    }

    @Transactional
    @Override
    public void addDataToMix(OrderMix orderMix, RechargeStyle type) {
        //还未实现,准备替代view中的功能
        OrderPayProcess orderPayProcess=this.getOrderByProcessByOrderType(type);
        orderPayProcess.setDataToOrderMix(orderMix);
    }

    @Transactional
    @Override
    public OrderMix getUserOrderMixDetail(OrderStyle orderStyle, Integer orderId) {
        OrderMix orderMix = orderMixDao.findOneByOrderIdAndType(orderId, orderStyle.getId());
        if(orderMix == null){
            throw new ApiNotFoundException("未找到该订单！");
        }
        this.addDataToMix(orderMix, RechargeStyle.getById(orderStyle.getId()));
        return orderMix;
    }

    @Transactional
    @Override
    public void deleteOrder(Integer userId, Integer orderId, RechargeStyle type) throws ApiException {
        OrderMix orderMix = orderMixDao.findOneByOrderIdAndType(orderId,type.getId());
        if(!userId.equals(orderMix.getUserId()) ){
            throw new ApiNotThisUserException("参数错误，用户与订单信息不符");
        }
        orderMix.setIsDel(1);
        orderMixDao.save(orderMix);
        OrderPayProcess orderPayProcess = this.getOrderByProcessByOrderType(type);
        orderPayProcess.deleteOrder(orderId);
    }
}
