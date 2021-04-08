package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.transaction.dao.CouponStoryExchangeDao;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchange;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CouponStoryExchangeServiceImpl implements CouponStoryExchangeService {

    @Autowired
    CouponStoryExchangeDao couponStoryExchangeDao;

    @Override
    public CouponStoryExchange getCouponStoryExchangeById(Integer couponId) {
        CouponStoryExchange couponStoryExchange = couponStoryExchangeDao.findOne(couponId);
        if(couponStoryExchange == null){
            throw new ApiNotFoundException("没有该故事兑换券");
        }
        return couponStoryExchange;
    }
}
