package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponSerialStoryOrder;

import java.util.List;

public interface CouponSerialStoryOrderService {

    /**
     * 添加系列故事订单和优惠券关联
     * @param couponsResult
     * @param userId
     * @param orderId
     */
    void addCouponSerialOrderByCouponsResult(CouponsResult couponsResult, Integer userId, Integer orderId);

    /**
     * 删除系列故事订单和优惠券关联
     * @param orderId
     */
    void deleteCouponSerialOrderByOrderId(Long orderId);

    /**
     * 获取系列故事订单和优惠券关联列表
     * @param orderId
     * @return
     */
    List<CouponSerialStoryOrder> getCouponSerialStoryOrderListByOrderId(Long orderId);
}
