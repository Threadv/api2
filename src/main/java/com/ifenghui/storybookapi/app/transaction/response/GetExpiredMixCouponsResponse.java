package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.VCouponMix;

import java.util.List;

/**
 * Created by wml on 2017/05/318
 */
public class GetExpiredMixCouponsResponse extends ApiPageResponse {


    List<VCouponMix> expiredMixCoupons;

    public List<VCouponMix> getExpiredMixCoupons() {
        return expiredMixCoupons;
    }

    public void setExpiredMixCoupons(List<VCouponMix> expiredMixCoupons) {
        this.expiredMixCoupons = expiredMixCoupons;
    }
}
