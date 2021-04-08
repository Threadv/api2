package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponStoryExchangeOrderDao extends JpaRepository<CouponStoryExchangeOrder, Integer> {
}
