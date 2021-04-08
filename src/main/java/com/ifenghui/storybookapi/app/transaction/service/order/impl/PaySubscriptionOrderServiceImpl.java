package com.ifenghui.storybookapi.app.transaction.service.order.impl;

import com.ifenghui.storybookapi.app.transaction.dao.PaySubscriptionOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.PaySubscriptionPriceDao;
import com.ifenghui.storybookapi.app.transaction.dao.SubscriptionRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionPrice;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;
import com.ifenghui.storybookapi.app.transaction.service.CouponDeferredSubscriptionService;
import com.ifenghui.storybookapi.app.transaction.service.CouponSubscriptionService;
import com.ifenghui.storybookapi.app.transaction.service.PayAfterOrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.PaySubscriptionOrderService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.RechargeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaySubscriptionOrderServiceImpl implements PaySubscriptionOrderService {

    @Autowired
    PaySubscriptionOrderDao paySubscriptionOrderDao;

    @Autowired
    PayAfterOrderService payAfterOrderService;

    @Autowired
    CouponSubscriptionService couponSubscriptionService;

    @Autowired
    CouponDeferredSubscriptionService couponDeferredSubscriptionService;

    @Autowired
    SubscriptionRecordDao subscriptionRecordDao;

    @Autowired
    PaySubscriptionPriceDao paySubscriptionPriceDao;

    @Override
    public Integer getPaySubscriptionOrderTimes(Integer month, Long userId) {
        Integer subscriptionCount = paySubscriptionOrderDao.getPaySubscriptionOrderTimes(month, userId);
        return subscriptionCount;
    }

    @Override
    public void cancelPaySubscriptionOrder(PaySubscriptionOrder paySubscriptionOrder, Long orderId, RechargeStyle rechargeStyle) {
        //修改此订单状态2
        paySubscriptionOrder.setStatus(2);

        paySubscriptionOrderDao.save(paySubscriptionOrder);

        /**
         * 删除订单与优惠券关联关系
         */
        couponSubscriptionService.deleteCouponOrderByOrderId(orderId);
        /**
         *删除订单与赠阅券关联关系
         */
        couponDeferredSubscriptionService.deleteCouponDeferredSubscriptionByOrderId(orderId);
    }

    @Override
    public List<SubscriptionRecord> getSubscriptionRecordByUserId(User user) {
        if(user == null){
            return new ArrayList<>();
        }
        return subscriptionRecordDao.getSubscriptionRecordsByUserId(user.getId());
    }

    @Override
    public void cancelOrder(Integer orderId) throws Exception{

        PaySubscriptionOrder paySubscriptionOrder=paySubscriptionOrderDao.findOne(orderId.longValue());
        //修改此订单状态2
        paySubscriptionOrder.setStatus(2);

        paySubscriptionOrderDao.save(paySubscriptionOrder);

        /**
         * 删除订单与优惠券关联关系
         */
        couponSubscriptionService.deleteCouponOrderByOrderId(orderId.longValue());
        /**
         *删除订单与赠阅券关联关系
         */
        couponDeferredSubscriptionService.deleteCouponDeferredSubscriptionByOrderId(orderId.longValue());
    }

    @Override
    public void setDataToOrderMix(OrderMix orderMix) {
        PaySubscriptionOrder paySubscriptionOrder;
        PaySubscriptionPrice paySubscriptionPrice;
        paySubscriptionOrder = paySubscriptionOrderDao.findOne(orderMix.getOrderId().longValue());
        paySubscriptionPrice = paySubscriptionPriceDao.findOne(paySubscriptionOrder.getPriceId());
        orderMix.setPaySubscriptionPrice(paySubscriptionPrice);
        orderMix.setStorys(null);
        orderMix.setSerialStory(null);
        orderMix.setBuyType(paySubscriptionOrder.getType());
        orderMix.setAmount(paySubscriptionOrder.getAmount());
        orderMix.setCreateTime(paySubscriptionOrder.getCreateTime());
        orderMix.setSuccessTime(paySubscriptionOrder.getSuccessTime());
        orderMix.setOriginalPrice(paySubscriptionOrder.getOriginalPrice());
        orderMix.setIsDel(paySubscriptionOrder.getIsDel());
    }

    @Override
    public void deleteOrder(Integer orderId) {
        PaySubscriptionOrder paySubscriptionOrder = paySubscriptionOrderDao.findOne(orderId.longValue());
        paySubscriptionOrder.setIsDel(1);
        paySubscriptionOrderDao.save(paySubscriptionOrder);
    }

    @Override
    public Integer getShareTradeAmount(Integer orderId) {
        //订阅订单已经不再处理
        return 0;
    }
}
