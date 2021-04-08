package com.ifenghui.storybookapi.app.transaction.response;

/**
 * Created by jia on 2016/12/26.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.VPayOrder;

import java.util.List;

public class GetUserPayOrderResponse extends ApiPageResponse {
    List<VPayOrder> payOrders;

    public List<VPayOrder> getPayOrders() {
        return payOrders;
    }

    public void setPayOrders(List<VPayOrder> payOrders) {
        this.payOrders = payOrders;
    }
}
