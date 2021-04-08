package com.ifenghui.storybookapi.exception;

/**
 * 已添加
 * Created by wml on 2017/2/20.
 */
public class ApiNoPermissionDelException extends ApiException {

    public ApiNoPermissionDelException(String apimsg){
        super(ExceptionStyle.NOT_PERMISSION_EXCEPTION,apimsg);

    }

}
