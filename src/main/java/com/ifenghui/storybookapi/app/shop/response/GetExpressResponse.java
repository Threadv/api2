package com.ifenghui.storybookapi.app.shop.response;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;

import com.ifenghui.storybookapi.app.transaction.response.LogisticsNew;

/**
 * Created by wml on 2016/12/30.
 */
public class GetExpressResponse extends BaseResponse {

    LogisticsNew logistics;

    String  logo;

    public LogisticsNew getLogistics() {
        return logistics;
    }

    public void setLogistics(LogisticsNew logistics) {
        this.logistics = logistics;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
