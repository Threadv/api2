package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

/**
 * Created by wml on 2016/12/30.
 */
public class SynchroPartsRecordJsonDataResponse extends ApiResponse {

    List<FormatPartsRecordResponse> parts;

    public List<FormatPartsRecordResponse> getParts() {
        return parts;
    }

    public void setParts(List<FormatPartsRecordResponse> parts) {
        this.parts = parts;
    }
}
