package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;

import java.util.List;

/**
 * Created by wml on 2017/05/318
 */
public class GetUserCouponsResponse extends ApiPageResponse {


    List<CouponUser> CouponUsers;
    String couponRuleUrl;//优惠券使用规则

    public String getCouponRuleUrl() {
        return couponRuleUrl;
    }

    public void setCouponRuleUrl(String couponRuleUrl) {
        this.couponRuleUrl = couponRuleUrl;
    }
    public List<CouponUser> getCouponUsers() {
        return CouponUsers;
    }

    public void setCouponUsers(List<CouponUser> couponUsers) {
        CouponUsers = couponUsers;
    }
}
