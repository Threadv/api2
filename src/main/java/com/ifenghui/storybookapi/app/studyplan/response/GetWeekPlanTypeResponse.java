package com.ifenghui.storybookapi.app.studyplan.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanMagazine;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskRelate;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import java.util.List;

public class GetWeekPlanTypeResponse extends ApiResponse {

    Integer weekPlanType;

    WeekPlanStyle weekPlanStyle;

    public Integer getWeekPlanType() {
        return weekPlanType;
    }

    public void setWeekPlanType(Integer weekPlanType) {
        this.weekPlanType = weekPlanType;
    }

    public WeekPlanStyle getWeekPlanStyle() {
        return weekPlanStyle;
    }

    public void setWeekPlanStyle(WeekPlanStyle weekPlanStyle) {
        this.weekPlanStyle = weekPlanStyle;
    }
}
