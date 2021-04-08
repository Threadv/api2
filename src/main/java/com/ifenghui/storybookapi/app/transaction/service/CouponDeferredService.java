package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredUser;
import com.ifenghui.storybookapi.exception.ApiException;
import org.springframework.data.domain.Page;

/**
 * Created by wml on 2017/06/22.
 */
public interface CouponDeferredService {

    Void collectDeferredCoupon(Long userId, Long couponId,String channel) throws ApiException;
    Page<CouponDeferredUser> getUserValidityDeferredCoupons(Long userId, Integer pageNo, Integer pageSize,Long priceId);
    Page<CouponDeferredUser> getUserDeferredCoupons(Long userId,Integer type, Integer pageNo, Integer pageSize);
    Integer getUserValidityDeferredCouponsCount(Long userId);//可用数量
    Integer getUserDeferredCouponsCount(Long userId);//未过期数量

}
