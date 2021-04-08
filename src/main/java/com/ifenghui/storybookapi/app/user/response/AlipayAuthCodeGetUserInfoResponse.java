package com.ifenghui.storybookapi.app.user.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class AlipayAuthCodeGetUserInfoResponse extends ApiResponse {

    String aliUserId;
    String aliUserInfo;

    public String getAliUserId() {
        return aliUserId;
    }

    public void setAliUserId(String aliUserId) {
        this.aliUserId = aliUserId;
    }

    public String getAliUserInfo() {
        return aliUserInfo;
    }

    public void setAliUserInfo(String aliUserInfo) {
        this.aliUserInfo = aliUserInfo;
    }
}
