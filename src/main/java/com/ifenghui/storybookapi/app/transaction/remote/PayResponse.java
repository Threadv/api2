package com.ifenghui.storybookapi.app.transaction.remote;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;

public class PayResponse extends BaseResponse {

    PreSalePay pay;

    public PreSalePay getPay() {
        return pay;
    }

    public void setPay(PreSalePay pay) {
        this.pay = pay;
    }
}
