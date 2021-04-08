package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class UserHasCouponNumberResponse extends ApiResponse {

    Integer cashCouponNumber;

    Integer storyCouponNumber;

    Integer storyCouponNeedNumber;

    public Integer getCashCouponNumber() {
        return cashCouponNumber;
    }

    public void setCashCouponNumber(Integer cashCouponNumber) {
        this.cashCouponNumber = cashCouponNumber;
    }

    public Integer getStoryCouponNumber() {
        return storyCouponNumber;
    }

    public void setStoryCouponNumber(Integer storyCouponNumber) {
        this.storyCouponNumber = storyCouponNumber;
    }

    public Integer getStoryCouponNeedNumber() {
        return storyCouponNeedNumber;
    }

    public void setStoryCouponNeedNumber(Integer storyCouponNeedNumber) {
        this.storyCouponNeedNumber = storyCouponNeedNumber;
    }
}
