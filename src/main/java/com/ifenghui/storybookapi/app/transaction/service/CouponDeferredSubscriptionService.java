package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 优惠时间的
 * Created by 订阅 on 2017/7/3.
 */
public interface CouponDeferredSubscriptionService {

    /**
     * 优惠时间的
     * @param couponDeferredSubscription
     * @param pageRequest
     * @return
     */
    Page<CouponDeferredSubscription> getCouponDeferredSubscriptions(
            CouponDeferredSubscription couponDeferredSubscription, PageRequest pageRequest
    );

    void deleteCouponDeferredSubscriptionByOrderId(Long orderId);
}
