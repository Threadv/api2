package com.ifenghui.storybookapi.app.social.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.entity.ViewRecord;

public class GetViewRecordResponse extends ApiResponse{
    ViewRecord viewrecord;


    public ViewRecord getViewrecord() {
        return viewrecord;
    }

    public void setViewrecord(ViewRecord viewrecord) {
        this.viewrecord = viewrecord;
    }


}
