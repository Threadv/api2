package com.ifenghui.storybookapi.exception;

/**
 * 优惠券过期
 * Created by wml on 2017/5/19
 */
public class ApiCouponPastDueException extends ApiException {

    public ApiCouponPastDueException(String apimsg){
        super(ExceptionStyle.COUPON_EXCEPTION,apimsg);
    }
}
