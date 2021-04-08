package com.ifenghui.storybookapi.app.transaction.response;

/**
 * Created by jia on 2017/1/9.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;

public class GetSubscriptionOrderResponse extends ApiResponse {
    PaySubscriptionOrder paySubscriptionOrder;

    public PaySubscriptionOrder getPaySubscriptionOrder() {
        return paySubscriptionOrder;
    }

    public void setPaySubscriptionOrder(PaySubscriptionOrder paySubscriptionOrder) {
        this.paySubscriptionOrder = paySubscriptionOrder;
    }
}
