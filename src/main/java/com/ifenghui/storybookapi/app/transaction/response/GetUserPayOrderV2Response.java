package com.ifenghui.storybookapi.app.transaction.response;

/**
 * mix表代替view
 */

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.VPayOrder;

import java.util.List;

public class GetUserPayOrderV2Response extends ApiPageResponse {
    List<OrderMix> payOrders;

    public List<OrderMix> getPayOrders() {
        return payOrders;
    }

    public void setPayOrders(List<OrderMix> payOrders) {
        this.payOrders = payOrders;
    }
}
