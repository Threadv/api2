package com.ifenghui.storybookapi.app.transaction.response;

/**
 * Created by jia on 2017/1/9.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;

public class GetBuyStorysOrderResponse extends ApiResponse {
    PayStoryOrder payStoryOrder;

    public PayStoryOrder getPayStoryOrder() {
        return payStoryOrder;
    }

    public void setPayStoryOrder(PayStoryOrder payStoryOrder) {
        this.payStoryOrder = payStoryOrder;
    }
}
