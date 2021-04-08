package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponOrderRelate;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.CouponLessonOrder;
import com.ifenghui.storybookapi.style.OrderStyle;

import java.util.List;

public interface CouponOrderRelateService {

    /**
     * 增加使用优惠券
     * @param userId
     * @param orderId
     * @param couponId
     * @return
     */
    CouponOrderRelate addCouponOrderRelate(Integer userId, Integer orderId, Integer couponId, OrderStyle orderStyle);

    /**
     * 增加使用优惠券
     * @param couponIds
     * @param userId
     * @param orderId
     */
    void addCouponOrderByCouponIdsStr(List<Integer> couponIds, Integer userId, Integer orderId, OrderStyle orderStyle);

    /**
     * 删除已使用优惠券记录
     */
    void deleteCouponOrderByUserIdAndOrderId(Integer userId, Integer orderId, OrderStyle orderStyle);

    List<CouponOrderRelate> getCouponOrderRelatesByUserIdAndAndOrderIdAndOrderType(Integer userId, Integer orderId, OrderStyle orderStyle);

}
