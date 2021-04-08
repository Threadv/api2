package com.ifenghui.storybookapi.app.social.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.social.entity.ViewRecord;

import java.util.List;

public class GetViewRecordsByUserIdResponse extends ApiPageResponse {


    List<ViewRecord> viewrecords;

    public List<ViewRecord> getViewrecords() {
        return viewrecords;
    }

    public void setViewrecords(List<ViewRecord> viewrecords) {
        this.viewrecords = viewrecords;
    }
}

