package com.ifenghui.storybookapi.app.presale.response;


import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class AppCallBackeResponse extends ApiResponse {


    Integer  CallBackStatus;

    public Integer getCallBackStatus() {
        return CallBackStatus;
    }

    public void setCallBackStatus(Integer callBackStatus) {
        CallBackStatus = callBackStatus;
    }
}
