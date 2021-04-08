package com.ifenghui.storybookapi.exception;

/**
 * 没有找到数据的异常
 * Created by wml on 2017/3/6.
 */
public class ApiActiveCodeFailException extends ApiException {


    public ApiActiveCodeFailException(String apimsg){
        super(ExceptionStyle.VIP_CODE_EXCEPTION,apimsg);

    }

}
