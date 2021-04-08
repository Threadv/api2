package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.entity.ReadPlan;

public class GetReadPlanResponse extends ApiResponse {

    ReadPlan readPlan;

    Integer isJoinReadPlan;

    public ReadPlan getReadPlan() {
        return readPlan;
    }

    public void setReadPlan(ReadPlan readPlan) {
        this.readPlan = readPlan;
    }

    public Integer getIsJoinReadPlan() {
        return isJoinReadPlan;
    }

    public void setIsJoinReadPlan(Integer isJoinReadPlan) {
        this.isJoinReadPlan = isJoinReadPlan;
    }
}
