package com.ifenghui.storybookapi.app.transaction.response;

/**
 * Created by jia on 2017/1/9.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;

public class GetBuySerialStoryOrderResponse extends ApiResponse {
    PaySerialStoryOrder paySerialStoryOrder;

    public PaySerialStoryOrder getPaySerialStoryOrder() {
        return paySerialStoryOrder;
    }

    public void setPaySerialStoryOrder(PaySerialStoryOrder paySerialStoryOrder) {
        this.paySerialStoryOrder = paySerialStoryOrder;
    }
}
