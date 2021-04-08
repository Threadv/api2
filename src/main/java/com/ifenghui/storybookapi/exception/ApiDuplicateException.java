package com.ifenghui.storybookapi.exception;

/**
 * 数据重复的异常
 * Created by wslhk on 2016/12/21.
 */
public class ApiDuplicateException extends ApiException {

    public ApiDuplicateException(String apimsg){
        super(ExceptionStyle.COUPON_DUPLICATE_EXCEPTION,apimsg);
    }


}
