package com.ifenghui.storybookapi.api.response.exception;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.VPayOrder;
import com.ifenghui.storybookapi.app.transaction.response.StandardOrder;
import com.ifenghui.storybookapi.exception.ApiStoryOrderRepeatException;

/**
 * Created by wml on 2016/12/26.
 */
public class ExceptionStoryOrderRepeatResponse extends ApiResponse {
    public ExceptionStoryOrderRepeatResponse(ApiStoryOrderRepeatException apiStoryOrderRepeatException){
        this.setPayOrder(apiStoryOrderRepeatException.getvPayOrder());
    }
    public ExceptionStoryOrderRepeatResponse(){

    }
    VPayOrder payOrder;

    StandardOrder standardOrder;

    public VPayOrder getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(VPayOrder payOrder) {
        this.payOrder = payOrder;
    }

    public StandardOrder getStandardOrder() {
        return standardOrder;
    }

    public void setStandardOrder(StandardOrder standardOrder) {
        this.standardOrder = standardOrder;
    }
}
