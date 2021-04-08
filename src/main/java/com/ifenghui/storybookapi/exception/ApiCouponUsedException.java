package com.ifenghui.storybookapi.exception;

/**
 * 没
 * Created by wml on 2017/5/18
 */
public class ApiCouponUsedException extends ApiException {

    public ApiCouponUsedException(String apimsg){
        super(ExceptionStyle.COUPON_EXCEPTION,apimsg);

    }

}
