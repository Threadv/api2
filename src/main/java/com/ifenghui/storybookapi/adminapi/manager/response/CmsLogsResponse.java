package com.ifenghui.storybookapi.adminapi.manager.response;


import com.ifenghui.storybookapi.adminapi.manager.entity.CmsLog;
import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerLog;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;

import java.util.List;

public class CmsLogsResponse extends ApiPageResponse{
    List<CmsLog> cmsLogs;

    public List<CmsLog> getCmsLogs() {
        return cmsLogs;
    }

    public void setCmsLogs(List<CmsLog> cmsLogs) {
        this.cmsLogs = cmsLogs;
    }
}
