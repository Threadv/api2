package com.ifenghui.storybookapi.app.wallet.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.user.entity.UserInfo;

/**
 * Created by wml on 2017/08/08
 */
public class GetUserInfoResponse extends ApiResponse {

    UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
