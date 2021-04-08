package com.ifenghui.storybookapi.app.user.response;

/**
 * Created by jia on 2017/1/9.
 */

import com.ifenghui.storybookapi.app.app.response.BaseResponse;

public class GetIosIsCheckResponse extends BaseResponse {

    Integer isCheck;

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }
}
