package com.ifenghui.storybookapi.app.user.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.entity.UserToken;

/**
 * Created by wslhk on 2016/12/19.
 */
public class GetUserResponse extends ApiResponse {
    UserToken userToken;

    User user;

    UserExtend userExtend;

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserExtend getUserExtend() {
        return userExtend;
    }

    public void setUserExtend(UserExtend userExtend) {
        this.userExtend = userExtend;
    }
}
