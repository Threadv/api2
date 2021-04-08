package com.ifenghui.storybookapi.exception;

/**
 * 用户登录异常
 * Created by wslhk on 2016/12/21.
 */
public class ApiNotTokenException extends ApiException {

    public ApiNotTokenException(){
        super(ExceptionStyle.NOT_FOUND_USER,"用户信息验证无效，请重新登录");
//        this.apimsg="用户信息验证无效，请重新登录";
//        this.apicode=203;
    }
    public ApiNotTokenException(String apimsg){
        super(ExceptionStyle.NOT_FOUND_USER,apimsg);
//        this.apimsg=apimsg;
//        this.apicode=203;
    }

}
