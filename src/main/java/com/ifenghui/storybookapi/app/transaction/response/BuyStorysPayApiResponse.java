package com.ifenghui.storybookapi.app.transaction.response;

/**
 * Created by jia on 2017/1/9.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;

public class BuyStorysPayApiResponse extends ApiResponse{
    PayRechargeOrder payRechargeOrder;

    public PayRechargeOrder getPayRechargeOrder() {
        return payRechargeOrder;
    }

    public void setPayRechargeOrder(PayRechargeOrder payRechargeOrder) {
        this.payRechargeOrder = payRechargeOrder;
    }
}
