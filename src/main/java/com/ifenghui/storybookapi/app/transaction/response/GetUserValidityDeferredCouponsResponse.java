package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredUser;

import java.util.List;

/**
 * Created by wml on 2017/06/22
 */
public class GetUserValidityDeferredCouponsResponse extends ApiPageResponse {


    List<CouponDeferredUser> couponDeferredUsers;
    String couponRuleUrl;

    public String getCouponRuleUrl() {
        return couponRuleUrl;
    }

    public void setCouponRuleUrl(String couponRuleUrl) {
        this.couponRuleUrl = couponRuleUrl;
    }

    public List<CouponDeferredUser> getCouponDeferredUsers() {
        return couponDeferredUsers;
    }

    public void setCouponDeferredUsers(List<CouponDeferredUser> couponDeferredUsers) {
        this.couponDeferredUsers = couponDeferredUsers;
    }
}
