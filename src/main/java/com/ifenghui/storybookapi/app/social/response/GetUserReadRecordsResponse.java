package com.ifenghui.storybookapi.app.social.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;

import java.util.List;

public class GetUserReadRecordsResponse extends ApiPageResponse {


    List<UserReadRecord> readRecords;

    public List<UserReadRecord> getReadRecords() {
        return readRecords;
    }

    public void setReadRecords(List<UserReadRecord> readRecords) {
        this.readRecords = readRecords;
    }
}

