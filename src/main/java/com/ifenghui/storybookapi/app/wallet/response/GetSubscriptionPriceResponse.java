package com.ifenghui.storybookapi.app.wallet.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionPrice;

import java.util.List;

/**
 * Created by wml on 2017/2/16.
 */
public class GetSubscriptionPriceResponse extends ApiPageResponse {

    List<PaySubscriptionPrice> paySubscriptionPrices;
    Integer couponCount;
    Integer couponDeferredCount;

    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    public Integer getCouponDeferredCount() {
        return couponDeferredCount;
    }

    public void setCouponDeferredCount(Integer couponDeferredCount) {
        this.couponDeferredCount = couponDeferredCount;
    }

    public List<PaySubscriptionPrice> getPaySubscriptionPrices() {
        return paySubscriptionPrices;
    }

    public void setPaySubscriptionPrices(List<PaySubscriptionPrice> paySubscriptionPrices) {
        this.paySubscriptionPrices = paySubscriptionPrices;
    }
}
