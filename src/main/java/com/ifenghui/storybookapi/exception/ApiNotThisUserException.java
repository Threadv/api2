package com.ifenghui.storybookapi.exception;

/**
 * 第一次分享获取优惠券
 * Created by wml on 2017/5/23
 */
public class ApiNotThisUserException extends ApiException {


    public ApiNotThisUserException(String apimsg){
        super(ExceptionStyle.NOT_CURRENT_USER,apimsg);

    }


}
