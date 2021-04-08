package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleUser;

public class PreSaleGetUserResponse extends ApiResponse {

    PreSaleUser preSaleUser;

    public PreSaleUser getPreSaleUser() {
        return preSaleUser;
    }

    public void setPreSaleUser(PreSaleUser preSaleUser) {
        this.preSaleUser = preSaleUser;
    }
}
