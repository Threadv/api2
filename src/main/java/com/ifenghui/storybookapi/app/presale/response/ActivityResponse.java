package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.Activity;

public class ActivityResponse extends ApiPageResponse {

    Activity preSaleActivity;

    public Activity getPreSaleActivity() {
        return preSaleActivity;
    }

    public void setPreSaleActivity(Activity preSaleActivity) {
        this.preSaleActivity = preSaleActivity;
    }
}
