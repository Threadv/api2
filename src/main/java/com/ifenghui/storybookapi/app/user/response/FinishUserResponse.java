package com.ifenghui.storybookapi.app.user.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.user.entity.UserToken;

/**
 * Created by wslhk on 2016/12/19.
 */
public class FinishUserResponse extends ApiResponse {
    UserToken userToken;

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }
}
