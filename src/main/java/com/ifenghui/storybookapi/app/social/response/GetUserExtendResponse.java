package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;

public class GetUserExtendResponse extends ApiResponse {

    UserExtend userExtend;

    public UserExtend getUserExtend() {
        return userExtend;
    }

    public void setUserExtend(UserExtend userExtend) {
        this.userExtend = userExtend;
    }
}
