package com.ifenghui.storybookapi.app.user.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;

public class AlipayAuthGrantResponse extends BaseResponse {

    String strInfo;

    public String getStrInfo() {
        return strInfo;
    }

    public void setStrInfo(String strInfo) {
        this.strInfo = strInfo;
    }
}
