package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.transaction.dao.AbilityPlanOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.entity.vip.PayVipOrder;
import com.ifenghui.storybookapi.app.transaction.response.AbilityPlanPrice;
import com.ifenghui.storybookapi.app.transaction.response.SvipPrice;
import com.ifenghui.storybookapi.app.transaction.service.AbilityPlanOrderService;
import com.ifenghui.storybookapi.app.transaction.service.CouponOrderRelateService;
import com.ifenghui.storybookapi.app.transaction.service.PayAbilityPlanOrderService;
import com.ifenghui.storybookapi.exception.ApiNoPermissionDelException;
import com.ifenghui.storybookapi.exception.ApiNotThisUserException;
import com.ifenghui.storybookapi.style.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayAbilityPlanOrderServiceImpl implements PayAbilityPlanOrderService {

    @Autowired
    AbilityPlanOrderService abilityPlanOrderService;

    @Autowired
    AbilityPlanOrderDao abilityPlanOrderDao;

    @Autowired
    CouponOrderRelateService couponOrderRelateService;

    @Override
    public void cancelOrder(Integer orderId) {
        AbilityPlanOrder abilityPlanOrder = abilityPlanOrderDao.findOne(orderId);

        if (abilityPlanOrder == null) {
            throw new ApiNoPermissionDelException("没有此订单！");
        }

        if (!abilityPlanOrder.getUserId().equals(abilityPlanOrder.getUserId())) {
            throw new ApiNotThisUserException("参数错误，用户与订单信息不符");
        }

        abilityPlanOrder.setStatus(2);
        abilityPlanOrderDao.save(abilityPlanOrder);
        couponOrderRelateService.deleteCouponOrderByUserIdAndOrderId(abilityPlanOrder.getUserId(), orderId, OrderStyle.ABILITY_PLAN_ORDER);

    }

    @Override
    public void setDataToOrderMix(OrderMix orderMix) {

        AbilityPlanOrder abilityPlanOrder = abilityPlanOrderService.getAbilityPlanOrder(orderMix.getOrderId());
        orderMix.setOriginalPrice(abilityPlanOrder.getOriginalPrice());
        orderMix.setCreateTime(abilityPlanOrder.getCreateTime());
        orderMix.setSuccessTime(abilityPlanOrder.getSuccessTime());
        orderMix.setIsDel(abilityPlanOrder.getIsDel());
        orderMix.setBuyType(abilityPlanOrder.getType());
        orderMix.setAmount(abilityPlanOrder.getAmount());

//        AbilityPlanPrice abilityPlanPrice = new AbilityPlanPrice();
        VipGoodsStyle vipGoodsStyle = null;
        if(abilityPlanOrder.getPriceId() ==0){
            vipGoodsStyle = VipGoodsStyle.getById(40);
        }else {
            vipGoodsStyle = VipGoodsStyle.getById(abilityPlanOrder.getPriceId());
        }
        AbilityPlanCodeStyle abilityPlanCodeStyle = vipGoodsStyle.getAbilityPlanCodeStyle();
        String title="宝宝会读("+abilityPlanCodeStyle.getTitle()+")";
        if(abilityPlanOrder.getOnlineOnly()!=null&&abilityPlanOrder.getOnlineOnly()==1){
            title="线上专属权益("+abilityPlanCodeStyle.getTitle()+")";
        }
        AbilityPlanPrice abilityPlanPrice = new AbilityPlanPrice(
                title,
                title,
                title,
                abilityPlanCodeStyle.getDays(),
                abilityPlanCodeStyle.getRealPrice(),
                abilityPlanOrder.getPlaneType(),
               0,
//                abilityPlanCodeStyle.getRealPrice(),
                abilityPlanOrder.getOriginalPrice(),
                abilityPlanCodeStyle.getBuyNum(),
                0,
                abilityPlanOrder.getOnlineOnly()
        );

        orderMix.setAbilityPlanPrice(abilityPlanPrice);

    }

    @Override
    public void deleteOrder(Integer orderId) {

        AbilityPlanOrder abilityPlanOrder = abilityPlanOrderService.getAbilityPlanOrder(orderId);
        abilityPlanOrder.setIsDel(1);

        abilityPlanOrderDao.save(abilityPlanOrder);

    }

    @Override
    public Integer getShareTradeAmount(Integer orderId) {
        AbilityPlanOrder abilityPlanOrder = abilityPlanOrderDao.findOne(orderId);
        if(
                abilityPlanOrder.getStatus().equals(OrderStatusStyle.PAY_SUCCESS.getId()) &&
                        !abilityPlanOrder.getType().equals(OrderPayStyle.CODE.getId()) &&
                        !abilityPlanOrder.getType().equals(OrderPayStyle.DEFAULT_NULL.getId()) &&
                        !abilityPlanOrder.getType().equals(OrderPayStyle.STORY_COUPON.getId())
                ){
            return abilityPlanOrder.getAmount();
        }
        return 0;
    }
}
