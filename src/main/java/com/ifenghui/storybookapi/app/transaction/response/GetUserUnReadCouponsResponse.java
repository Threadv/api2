package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;

import java.util.List;

/**
 * Created by wml on 2017/05/23
 */
public class GetUserUnReadCouponsResponse extends ApiPageResponse {


    List<CouponUser> CouponUsers;

    public List<CouponUser> getCouponUsers() {
        return CouponUsers;
    }

    public void setCouponUsers(List<CouponUser> couponUsers) {
        CouponUsers = couponUsers;
    }
}
