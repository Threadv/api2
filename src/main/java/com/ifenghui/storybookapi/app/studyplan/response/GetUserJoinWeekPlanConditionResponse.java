package com.ifenghui.storybookapi.app.studyplan.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

public class GetUserJoinWeekPlanConditionResponse extends ApiResponse {

    WeekPlanStyle weekPlanStyle;

    Integer planChangeCount;

    public WeekPlanStyle getWeekPlanStyle() {
        return weekPlanStyle;
    }

    public void setWeekPlanStyle(WeekPlanStyle weekPlanStyle) {
        this.weekPlanStyle = weekPlanStyle;
    }

    public Integer getPlanChangeCount() {
        return planChangeCount;
    }

    public void setPlanChangeCount(Integer planChangeCount) {
        this.planChangeCount = planChangeCount;
    }
}
