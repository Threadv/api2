package com.ifenghui.storybookapi.adminapi.manager.response;


import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerLog;

import java.util.List;

public class ManagerLogsResponse extends ApiPageResponse{
    List<ManagerLog> managerLogs;

    public List<ManagerLog> getManagerLogs() {
        return managerLogs;
    }

    public void setManagerLogs(List<ManagerLog> managerLogs) {
        this.managerLogs = managerLogs;
    }
}
