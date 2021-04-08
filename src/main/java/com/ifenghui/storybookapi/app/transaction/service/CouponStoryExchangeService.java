package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchange;

public interface CouponStoryExchangeService {

    /**
     * 获取故事兑换券
     * @param couponId
     * @return
     */
    CouponStoryExchange getCouponStoryExchangeById(Integer couponId);

}
