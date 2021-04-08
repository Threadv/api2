package com.ifenghui.storybookapi.app.presale.response;


import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;

public class PayNotifyResponse extends ApiPageResponse {

    PayRechargeOrder payRechargeOrder;

    public PayRechargeOrder getPayRechargeOrder() {
        return payRechargeOrder;
    }

    public void setPayRechargeOrder(PayRechargeOrder payRechargeOrder) {
        this.payRechargeOrder = payRechargeOrder;
    }
}
