package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchange;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CouponStoryExchangeDao extends JpaRepository<CouponStoryExchange, Integer> ,JpaSpecificationExecutor {

    @Cacheable(cacheNames = "CouponStoryExchange_findOne_",key = "'CouponStoryExchange_findOne_'+#p0")
    @Override
    CouponStoryExchange findOne(Integer id);
}
