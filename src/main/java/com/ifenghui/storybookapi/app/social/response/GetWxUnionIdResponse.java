package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;

public class GetWxUnionIdResponse extends BaseResponse {

    String unionId;
    String nick;

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
