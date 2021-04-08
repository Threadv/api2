package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.response.FormatViewRecordResponse;

import java.util.List;

/**
 * Created by wml on 2016/12/30.
 */
public class SynchroViewRecordJsonDataResponse extends ApiResponse {

    List<FormatViewRecordResponse> viewRecords;


    public List<FormatViewRecordResponse> getViewRecords() {
        return viewRecords;
    }

    public void setViewRecords(List<FormatViewRecordResponse> viewRecords) {
        this.viewRecords = viewRecords;
    }
}
