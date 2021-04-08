package com.ifenghui.storybookapi.exception;

/**
 * 余额不足
 * Created by wml on 2017/2/20.
 */
public class ApiBeyondLimitException extends ApiException {


    public ApiBeyondLimitException(String apimsg){
        super(ExceptionStyle.INDEX_OUT_OF_BOUNDS_EXCEPTION,apimsg);

    }

}
