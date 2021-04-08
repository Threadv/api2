package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;

public class PayResponse extends ApiResponse {

    PreSalePay pay;

    public PreSalePay getPay() {
        return pay;
    }

    public void setPay(PreSalePay pay) {
        this.pay = pay;
    }
}
