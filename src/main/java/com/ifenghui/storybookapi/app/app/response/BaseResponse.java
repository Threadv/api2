package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.style.AdminResponseType;

public class BaseResponse extends ApiResponse {
    public BaseResponse(){

    }
    public BaseResponse(AdminResponseType responseType){
        this.getStatus().setCode(responseType.getCode());
    }


}
