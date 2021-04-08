package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class BuyOrderByBalanceResponse extends ApiResponse {
    StandardOrder standardOrder;

    public StandardOrder getStandardOrder() {
        return standardOrder;
    }

    public void setStandardOrder(StandardOrder standardOrder) {
        this.standardOrder = standardOrder;
    }
}
