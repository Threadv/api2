package com.ifenghui.storybookapi.app.social.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;

import java.util.List;

public class GetUserReadStoryRecordsResponse extends BaseResponse {


    List<UserReadRecord> readRecords;

    public List<UserReadRecord> getReadRecords() {
        return readRecords;
    }

    public void setReadRecords(List<UserReadRecord> readRecords) {
        this.readRecords = readRecords;
    }
}

