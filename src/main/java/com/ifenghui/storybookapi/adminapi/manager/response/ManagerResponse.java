package com.ifenghui.storybookapi.adminapi.manager.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.adminapi.manager.entity.Manager;


public class ManagerResponse extends BaseResponse {
    Manager manager;

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
