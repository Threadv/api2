package com.ifenghui.storybookapi.app.wallet.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.wallet.entity.UserStarRecord;
import java.util.List;

public class GetStarRecordsResponse extends ApiPageResponse {

   Integer starCount;

   List<UserStarRecord> userStarRecordList;

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public List<UserStarRecord> getUserStarRecordList() {
        return userStarRecordList;
    }

    public void setUserStarRecordList(List<UserStarRecord> userStarRecordList) {
        this.userStarRecordList = userStarRecordList;
    }
}
