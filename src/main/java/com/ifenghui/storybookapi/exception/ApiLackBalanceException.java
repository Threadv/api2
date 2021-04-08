package com.ifenghui.storybookapi.exception;

/**
 * 余额不足
 * Created by wml on 2017/2/20.
 */
public class ApiLackBalanceException extends ApiException {


    public ApiLackBalanceException(String apimsg){
        super(ExceptionStyle.NOT_ENOUGH_MONEY_EXCEPTION,apimsg);

    }

}
