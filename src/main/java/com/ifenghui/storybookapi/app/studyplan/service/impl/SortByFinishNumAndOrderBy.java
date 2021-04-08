package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class SortByFinishNumAndOrderBy implements Comparator<WeekPlanLabel> {

    @Override
    public int compare(WeekPlanLabel s1, WeekPlanLabel s2) {
        if(s1.getFinishNum() > s2.getFinishNum()) {
            return -1;
        } else if(s1.getFinishNum().equals(s2.getFinishNum())) {
            if(s1.getOrderBy() > s2.getOrderBy()) {
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
