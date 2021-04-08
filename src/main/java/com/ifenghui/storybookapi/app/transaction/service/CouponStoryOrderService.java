package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryOrder;

import java.util.List;

public interface CouponStoryOrderService {
    CouponStoryOrder addCouponStoryOrder(Integer userId, Integer orderId, Integer couponId);

    /**
     * 添加故事订单和优惠券关联
     * @param couponIds
     * @param userId
     * @param orderId
     */
    void addCouponOrderByCouponIdsStr(List<Integer> couponIds, Integer userId, Integer orderId);

    /**
     * 取消订单时 取消故事订单和优惠券关联
     * @param userId
     * @param orderId
     */
    void deleteCouponOrderByUserIdAndOrderId(Integer userId, Integer orderId);

    /**
     * 通过订单号获得兑换券和故事订单关联
     * @param orderId
     * @return
     */
    List<CouponStoryOrder> getCouponStoryOrderListByOrderId(Integer orderId);

}
