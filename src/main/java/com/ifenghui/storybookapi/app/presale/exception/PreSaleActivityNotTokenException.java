package com.ifenghui.storybookapi.app.presale.exception;

/**
 * 用户登录异常
 * Created by wslhk on 2016/12/21.
 */
public class PreSaleActivityNotTokenException extends PreSaleException {

    public PreSaleActivityNotTokenException(){
        super(PreSaleExceptionStyle.NOT_FOUND_USER,"用户信息验证无效，请重新登录");
//        this.apimsg="用户信息验证无效，请重新登录";
//        this.apicode=203;
    }
    public PreSaleActivityNotTokenException(String apimsg){
        super(PreSaleExceptionStyle.NOT_FOUND_USER,apimsg);
//        this.apimsg=apimsg;
//        this.apicode=203;
    }

}
