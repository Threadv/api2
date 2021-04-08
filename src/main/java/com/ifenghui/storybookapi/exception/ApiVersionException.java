package com.ifenghui.storybookapi.exception;

/**
 * 没有找到数据的异常
 * Created by wslhk on 2016/12/21.
 */
public class ApiVersionException extends ApiException {


    public ApiVersionException(String apimsg){
        super(ExceptionStyle.VERSION_EXCEPTION,apimsg);
    }

}
