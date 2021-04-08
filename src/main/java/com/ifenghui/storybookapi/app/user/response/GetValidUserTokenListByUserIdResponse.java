package com.ifenghui.storybookapi.app.user.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.user.entity.UserToken;

import java.util.List;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GetValidUserTokenListByUserIdResponse extends ApiResponse {

    List<UserToken> userTokenList;

    public List<UserToken> getUserTokenList() {
        return userTokenList;
    }

    public void setUserTokenList(List<UserToken> userTokenList) {
        this.userTokenList = userTokenList;
    }
}
