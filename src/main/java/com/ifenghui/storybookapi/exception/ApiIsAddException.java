package com.ifenghui.storybookapi.exception;

/**
 * 已添加
 * Created by wml on 2017/2/20.
 */
public class ApiIsAddException extends ApiException {

    public ApiIsAddException(String apimsg){
        super(ExceptionStyle.ALREADY_EXISTS_EXCEPTION,apimsg);

    }

}
