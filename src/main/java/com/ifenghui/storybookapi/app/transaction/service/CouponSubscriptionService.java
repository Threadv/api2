package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 优惠钱的
 * Created by 订阅 on 2017/7/3.
 */
public interface CouponSubscriptionService {

    Page<CouponSubscription> getCouponSubscriptions(CouponSubscription couponSubscription, PageRequest pageRequest);

    void deleteCouponOrderByOrderId(Long orderId);
}
