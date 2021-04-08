package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class SortByIsFinishAndOrderBy implements Comparator<WeekPlanLabel> {

    @Override
    public int compare(WeekPlanLabel s1, WeekPlanLabel s2) {
        if(s1.getIsFinish() > s2.getIsFinish()) {
            return -1;
        } else if(s1.getIsFinish().equals(s2.getIsFinish())) {
            if(s1.getOrderBy() < s2.getOrderBy()) {
                return -1;
            } else if(s1.getOrderBy().equals(s2.getOrderBy())) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

}
