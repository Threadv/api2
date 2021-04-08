package com.ifenghui.storybookapi.api.response.base;

import java.io.Serializable;

/**
 * Created by wslhk on 2016/12/20.
 */
public abstract class ApiResponse {
    ApiStatus status;
    public ApiResponse(){
        status=new ApiStatus();
        status.setCode(1);
        status.setMsg("成功");
    }



    public ApiStatus getStatus() {
        return status;
    }

    public void setStatus(ApiStatus status) {
        this.status = status;
    }
}
