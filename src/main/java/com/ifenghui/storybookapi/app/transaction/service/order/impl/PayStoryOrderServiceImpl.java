package com.ifenghui.storybookapi.app.transaction.service.order.impl;

import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.transaction.dao.OrderStoryDao;
import com.ifenghui.storybookapi.app.transaction.dao.PayStoryOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.OrderStory;
import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryOrderService;
import com.ifenghui.storybookapi.app.transaction.service.PayAfterOrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderStoryService;
import com.ifenghui.storybookapi.app.transaction.service.order.PayStoryOrderService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.OrderStatusStyle;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PayStoryOrderServiceImpl implements PayStoryOrderService {

    @Autowired
    PayStoryOrderDao payStoryOrderDao;

    @Autowired
    PayAfterOrderService payAfterOrderService;

    @Autowired
    CouponStoryOrderService couponStoryOrderService;

    @Autowired
    OrderMixService orderMixService;

    @Autowired
    OrderStoryDao orderStoryDao;

    @Autowired
    StoryDao storyDao;

    @Override
    public Integer getStoryOrderTimes(Integer price, Long userId) {
        Integer storyOrderCount = payStoryOrderDao.getPayStoryOrderTimes(price,userId);
        return storyOrderCount;
    }



    /**
     * 取消故事购买的订单
     * @param payStoryOrder
     * @param orderId
     */
    @Override
    public void cancelPayStoryOrder(PayStoryOrder payStoryOrder, Long orderId) {
//        Long subscriptionOrderId=  Long.parseLong(payAfterOrder.getOrderCode().split("_")[1]);
        //获取订阅订单
//        PaySubscriptionOrder paySubscriptionOrder = paySubscriptionOrderDao.findOne(orderId);
        //修改此订单状态2
        payStoryOrder.setStatus(2);
        payStoryOrderDao.save(payStoryOrder);
        couponStoryOrderService.deleteCouponOrderByUserIdAndOrderId(payStoryOrder.getUserId().intValue(), orderId.intValue());
        //删除order_story数据,--不能删除，因为取消以后不展示取消订单故事情况
//        List<OrderStory> orderStories = orderStoryDao.getOrderStoryByOrderId(orderId);
//        for (OrderStory item:orderStories){
//            orderStoryDao.delete(item.getId());
//        }
    }

    @Transactional
    @Override
    public PayStoryOrder addPayStoryOrder(User user, Integer originalPrice, Integer couponAmount, Integer amount, Integer num, OrderPayStyle payStyle, Integer userDiscount, String channel) {
        PayStoryOrder payStoryOrder = new PayStoryOrder();
        payStoryOrder.setUserId(user.getId());
        payStoryOrder.setOriginalPrice(originalPrice);//原价
        payStoryOrder.setCouponAmount(couponAmount);
        payStoryOrder.setAmount(amount);//折扣后的金额
        payStoryOrder.setOrderCode("");//订单号
        payStoryOrder.setSuccessTime(new Date());
        payStoryOrder.setStatus(0);
        payStoryOrder.setPayStyle(payStyle);
        payStoryOrder.setTargetType(1);
        payStoryOrder.setPayTarget(0L);
        payStoryOrder.setNum(num);
        payStoryOrder.setDiscount(1);
        payStoryOrder.setCreateTime(new Date());
        payStoryOrder.setIsDel(0);
        payStoryOrder.setUserDiscount(userDiscount);
        payStoryOrder.setIsTest(user.getIsTest());//测试账户
        payStoryOrder.setChannel(channel);//渠道
        payStoryOrder.setCouponNum(0);
        payStoryOrder.setRemark("");
        payStoryOrder = payStoryOrderDao.save(payStoryOrder);

        //增加总订单
        OrderMix orderMix = orderMixService.addOrderMix(OrderStyle.STORY_ORDER,payStoryOrder.getId().intValue(),user.getId().intValue());
        payStoryOrder.setMixOrderId(orderMix.getId());
        return payStoryOrder;
    }

    @Override
    public void cancelOrder(Integer orderId) {
        PayStoryOrder payStoryOrder= payStoryOrderDao.findOne(orderId.longValue());
        payStoryOrder.setStatus(OrderStatusStyle.PAY_BACK.getId());
        payStoryOrderDao.save(payStoryOrder);
        couponStoryOrderService.deleteCouponOrderByUserIdAndOrderId(payStoryOrder.getUserId().intValue(), orderId);
    }

    @Override
    public void setDataToOrderMix(OrderMix orderMix) {
        PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(orderMix.getOrderId().longValue());
        orderMix.setBuyType(payStoryOrder.getBuyType());
        orderMix.setStorys(new ArrayList<>());
        List<OrderStory> orderStoryList=orderStoryDao.getOrderStoryByOrderId(orderMix.getOrderId().longValue());
        for(OrderStory orderStory:orderStoryList){
            Story one = storyDao.findOne(orderStory.getStoryId());
            orderMix.getStorys().add(one);
        }
        orderMix.setAmount(payStoryOrder.getAmount());
        orderMix.setCreateTime(payStoryOrder.getCreateTime());
        orderMix.setSuccessTime(payStoryOrder.getSuccessTime());
        orderMix.setOriginalPrice(payStoryOrder.getOriginalPrice());
        orderMix.setIsDel(payStoryOrder.getIsDel());

    }

    @Override
    public PayStoryOrder getPayStoryOrderById(Long Id) {
        return payStoryOrderDao.findOne(Id);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        PayStoryOrder payStoryOrder= payStoryOrderDao.findOne(orderId.longValue());
        payStoryOrder.setIsDel(1);
        payStoryOrderDao.save(payStoryOrder);
    }

    @Override
    public Integer getShareTradeAmount(Integer orderId) {
        PayStoryOrder payStoryOrder = this.getPayStoryOrderById(orderId.longValue());
        if(
                payStoryOrder.getStatus().equals(OrderStatusStyle.PAY_SUCCESS.getId()) &&
                        !payStoryOrder.getBuyType().equals(OrderPayStyle.CODE.getId()) &&
                        !payStoryOrder.getBuyType().equals(OrderPayStyle.DEFAULT_NULL.getId()) &&
                        !payStoryOrder.getBuyType().equals(OrderPayStyle.STORY_COUPON.getId())
                ){
            return payStoryOrder.getAmount();
        }
        return 0;
    }
}
