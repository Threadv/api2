package com.ifenghui.storybookapi.exception;

/**
 * 没有找到数据的异常
 * Created by wslhk on 2016/12/21.
 */
public class ApiNotFoundException extends ApiException {


    public ApiNotFoundException(String apimsg){

            super(ExceptionStyle.NOT_FOUND_EXCEPTION,apimsg);

    }
    public ApiNotFoundException(String apimsg,int code){
        this.apimsg=apimsg;
        this.apicode=code;
    }

}
