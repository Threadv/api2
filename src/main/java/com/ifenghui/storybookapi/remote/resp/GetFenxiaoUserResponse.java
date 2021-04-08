package com.ifenghui.storybookapi.remote.resp;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;

public class GetFenxiaoUserResponse extends BaseResponse {

    FenxiaoUser user;

    public FenxiaoUser getUser() {
        return user;
    }

    public void setUser(FenxiaoUser user) {
        this.user = user;
    }
}
