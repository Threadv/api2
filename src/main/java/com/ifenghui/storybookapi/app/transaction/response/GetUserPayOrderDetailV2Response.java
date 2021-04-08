package com.ifenghui.storybookapi.app.transaction.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;

public class GetUserPayOrderDetailV2Response extends ApiResponse  {

    @JsonProperty("payOrder")
    OrderMix orderMix;

    public OrderMix getOrderMix() {
        return orderMix;
    }

    public void setOrderMix(OrderMix orderMix) {
        this.orderMix = orderMix;
    }
}
