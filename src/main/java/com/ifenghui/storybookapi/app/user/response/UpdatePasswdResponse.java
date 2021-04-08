package com.ifenghui.storybookapi.app.user.response;

/**
 * Created by jia on 2017/1/9.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
public class UpdatePasswdResponse   extends ApiResponse {
    UserToken userToken;

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }
}
