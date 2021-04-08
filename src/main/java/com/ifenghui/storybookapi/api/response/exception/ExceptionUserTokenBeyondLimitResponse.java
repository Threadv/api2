package com.ifenghui.storybookapi.api.response.exception;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import com.ifenghui.storybookapi.exception.ApiUserTokenBeyondLimitException;

public class ExceptionUserTokenBeyondLimitResponse extends ApiResponse {

    UserToken userToken;

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }

    public ExceptionUserTokenBeyondLimitResponse(ApiUserTokenBeyondLimitException apiUserTokenBeyondLimitException){
        this.userToken = apiUserTokenBeyondLimitException.getUserToken();
    }
}
