package com.ifenghui.storybookapi.app.user.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.vip.UserSvip;

/**
 * Created by wslhk on 2016/12/19.
 */
public class GetUserSvipResponse extends ApiResponse {
    UserSvip userSvip;

    public UserSvip getUserSvip() {
        return userSvip;
    }

    public void setUserSvip(UserSvip userSvip) {
        this.userSvip = userSvip;
    }
}
