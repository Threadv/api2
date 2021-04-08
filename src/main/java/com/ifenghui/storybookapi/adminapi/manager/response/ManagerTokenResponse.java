package com.ifenghui.storybookapi.adminapi.manager.response;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerToken;
import com.ifenghui.storybookapi.style.AdminResponseType;

public class ManagerTokenResponse extends BaseResponse {

    public ManagerTokenResponse(){
        super();

    }

    public ManagerTokenResponse(AdminResponseType responseType){
        super(responseType);
    }

    ManagerToken managerToken;

    public ManagerToken getManagerToken() {
        return managerToken;
    }

    public void setManagerToken(ManagerToken managerToken) {
        this.managerToken = managerToken;
    }
}
