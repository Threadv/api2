package com.ifenghui.storybookapi.app.transaction.response;

/**
 * Created by jia on 2016/12/26.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.VPayOrder;

public class GetUserPayOrderDetailResponse extends ApiResponse {
    VPayOrder payOrder;

    public VPayOrder getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(VPayOrder payOrder) {
        this.payOrder = payOrder;
    }
}
