package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


/**
 * Created by wml on 2017/6/22.
 */
@Transactional
public interface CouponDeferredSubscriptionDao extends JpaRepository<CouponDeferredSubscription, Long> {

    CouponDeferredSubscription getByOrderId(Long orderId);

}