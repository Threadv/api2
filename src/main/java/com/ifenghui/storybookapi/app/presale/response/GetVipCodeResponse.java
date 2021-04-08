package com.ifenghui.storybookapi.app.presale.response;


import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class GetVipCodeResponse extends ApiResponse {

    String vipCode;

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }
}
