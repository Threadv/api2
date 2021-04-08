package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeUser;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;

import java.util.List;

/**
 * Created by wml on 2017/05/19
 */
public class GetCouponByShareResponse extends ApiResponse {

    CouponUser couponUser;

    List<CouponStoryExchangeUser> couponStoryUserList;

    public CouponUser getCouponUser() {
        return couponUser;
    }

    public void setCouponUser(CouponUser couponUser) {
        this.couponUser = couponUser;
    }

    public List<CouponStoryExchangeUser> getCouponStoryUserList() {
        return couponStoryUserList;
    }

    public void setCouponStoryUserList(List<CouponStoryExchangeUser> couponStoryUserList) {
        this.couponStoryUserList = couponStoryUserList;
    }
}
