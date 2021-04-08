package com.ifenghui.storybookapi.adminapi.manager.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.adminapi.manager.entity.Manager;

import java.util.List;

public class ManagersResponse extends ApiPageResponse {
    List<Manager> managers;

    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }
}
