package com.ifenghui.storybookapi.exception;

/**
 * 输入格式错误
 */
public class ApiInputFormatException extends ApiException {

    public ApiInputFormatException(){
        this.apimsg="input format error";
        this.apicode=23;
    }
    public ApiInputFormatException(String apimsg){
        super(ExceptionStyle.PARAMS_EXCEPTION,apimsg);
    }

}
