package com.ifenghui.storybookapi.app.user.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class CheckUserTokenResponse extends ApiResponse {

    Integer isValid;

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
