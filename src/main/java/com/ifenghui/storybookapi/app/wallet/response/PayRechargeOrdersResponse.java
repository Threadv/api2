package com.ifenghui.storybookapi.app.wallet.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;

import java.util.List;

/**
 * Created by w
 */
public class PayRechargeOrdersResponse extends ApiPageResponse {

    List<PayRechargeOrder> payRechargeOrders;

    public List<PayRechargeOrder> getPayRechargeOrders() {
        return payRechargeOrders;
    }

    public void setPayRechargeOrders(List<PayRechargeOrder> payRechargeOrders) {
        this.payRechargeOrders = payRechargeOrders;
    }

}
