package com.ifenghui.storybookapi.app.social.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.entity.UserReadPlanRecord;

import java.util.List;

public class GetUserReadPlanRecordListRespone extends ApiResponse {

    List<UserReadPlanRecord> userReadPlanRecordList;

    public List<UserReadPlanRecord> getUserReadPlanRecordList() {
        return userReadPlanRecordList;
    }

    public void setUserReadPlanRecordList(List<UserReadPlanRecord> userReadPlanRecordList) {
        this.userReadPlanRecordList = userReadPlanRecordList;
    }

    @JsonProperty("isFinish")
    public Integer getIsFinish(){
        if(this.userReadPlanRecordList != null && this.userReadPlanRecordList.size() >= 21){
           return 1;
        } else {
            return 0;
        }
    }

    @JsonProperty("finishCount")
    public Integer getFinishRecordCount(){
        if(this.userReadPlanRecordList != null && this.userReadPlanRecordList.size() >= 1){
            return this.userReadPlanRecordList.size();
        } else {
            return 0;
        }
    }

}
