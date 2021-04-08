package com.ifenghui.storybookapi.exception;

import io.swagger.annotations.ApiResponse;

/**
 * Created by wslhk on 2016/12/23.
 */
public class ApiException extends RuntimeException{
    protected String apimsg;
    public int apicode;
    public ApiException(){

    }


    public ApiException(ExceptionStyle exceptionStyle,String apimsg){
        super(apimsg);
        this.apicode=exceptionStyle.code;

        this.apimsg=apimsg;
        if(apimsg==null||"".equals(apimsg)){
            this.apimsg=exceptionStyle.msg;
        }
    }

    public String getApimsg() {
        return apimsg;
    }

    public void setApimsg(String apimsg) {
        this.apimsg = apimsg;
    }

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }


}
