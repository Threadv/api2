package com.ifenghui.storybookapi.app.presale.exception;

/**
 * 没有找到数据的异常
 * Created by wslhk on 2016/12/21.
 */
public class PreSaleNotFoundException extends PreSaleException {


    public PreSaleNotFoundException(String apimsg){

            super(PreSaleExceptionStyle.NOT_FOUND_EXCEPTION,apimsg);

    }
    public PreSaleNotFoundException(String apimsg, int code){
        this.apimsg=apimsg;
        this.apicode=code;
    }

}
