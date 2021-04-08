package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeUser;

import java.util.List;

public class GetUserStoryCouponsResponse extends ApiPageResponse {

    List<CouponStoryExchangeUser> couponStoryExchangeUserList;

    String couponRuleUrl;

    public List<CouponStoryExchangeUser> getCouponStoryExchangeUserList() {
        return couponStoryExchangeUserList;
    }

    public void setCouponStoryExchangeUserList(List<CouponStoryExchangeUser> couponStoryExchangeUserList) {
        this.couponStoryExchangeUserList = couponStoryExchangeUserList;
    }

    public String getCouponRuleUrl() {
        return couponRuleUrl;
    }

    public void setCouponRuleUrl(String couponRuleUrl) {
        this.couponRuleUrl = couponRuleUrl;
    }
}
