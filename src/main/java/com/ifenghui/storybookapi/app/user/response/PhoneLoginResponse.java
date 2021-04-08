package com.ifenghui.storybookapi.app.user.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.app.user.entity.UserToken;

public class PhoneLoginResponse extends ApiResponse {
    public PhoneLoginResponse(){
        this.getStatus().setMsg(MyEnv.getMessage("login.success"));
    }
    UserToken userToken;

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }
}
