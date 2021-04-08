package com.ifenghui.storybookapi.adminapi.controlleradmin.user.resp;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.UserAbilityPlanRelate;

import java.util.List;

public class WeekPlanJoinsResponse extends ApiPageResponse {
    List<WeekPlanJoin> weekPlanJoins;

    public List<WeekPlanJoin> getWeekPlanJoins() {
        return weekPlanJoins;
    }

    public void setWeekPlanJoins(List<WeekPlanJoin> weekPlanJoins) {
        this.weekPlanJoins = weekPlanJoins;
    }
}
