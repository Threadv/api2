package com.ifenghui.storybookapi.exception;

import com.ifenghui.storybookapi.config.ExceptionCodeConfig;
import com.ifenghui.storybookapi.app.user.entity.UserToken;

public class ApiUserTokenBeyondLimitException extends ApiException {

    UserToken userToken;

    public ApiUserTokenBeyondLimitException(UserToken userToken){
        super(ExceptionStyle.USER_TOKEN_OUT_OF_BOUNDS_EXCEPTION,ExceptionCodeConfig.USER_TOKEN_BEYOND_LIMIT_MSG);
        this.userToken = userToken;
    }

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }

    public ApiUserTokenBeyondLimitException(UserToken userToken, String apiMsg,ExceptionStyle exceptionStyle){
        super(exceptionStyle,apiMsg);
//        this.apimsg = apiMsg;
//        this.apicode = apiCode;
        this.userToken = userToken;
    }
}
