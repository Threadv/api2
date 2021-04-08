package com.ifenghui.storybookapi.app.studyplan.response;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;

import java.util.List;

public class WeekPlanLabelParent {

    String name;
    List<WeekPlanLabel> weekPlanLabelList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeekPlanLabel> getWeekPlanLabelList() {
        return weekPlanLabelList;
    }

    public void setWeekPlanLabelList(List<WeekPlanLabel> weekPlanLabelList) {
        this.weekPlanLabelList = weekPlanLabelList;
    }
}
