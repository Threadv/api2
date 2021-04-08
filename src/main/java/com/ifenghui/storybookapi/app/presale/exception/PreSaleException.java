package com.ifenghui.storybookapi.app.presale.exception;

/**
 * Created by wslhk on 2016/12/23.
 */
public class PreSaleException extends RuntimeException{
    protected String apimsg;
    public int apicode;
    public PreSaleException(){

    }


    public PreSaleException(PreSaleExceptionStyle preSaleExceptionStyle, String apimsg){
        super(apimsg);
        this.apicode= preSaleExceptionStyle.code;

        this.apimsg=apimsg;
        if(apimsg==null||"".equals(apimsg)){
            this.apimsg= preSaleExceptionStyle.msg;
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
