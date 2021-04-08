package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponSubscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


/**
 * Created by wml on 2017/5/19.
 */
@Transactional
public interface CouponSubscriptionDao extends JpaRepository<CouponSubscription, Long> {

    List<CouponSubscription> getByOrderId(Long orderId);

}