package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanLabelStoryDao;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabelStory;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanLabelService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanLabelStoryService;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.WeekPlanLabelStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeekPlanLabelStoryServiceImpl implements WeekPlanLabelStoryService {

    @Autowired
    WeekPlanLabelService weekPlanLabelService;

    @Autowired
    WeekPlanLabelStoryDao weekPlanLabelStoryDao;

    @Override
    public List<WeekPlanLabelStory> findListByTargetTypeAndTargetValueAndLabelType(LabelTargetStyle targetStyle, Integer targetValue, WeekPlanLabelStyle weekPlanLabelStyle) {
        return weekPlanLabelStoryDao.getWeekPlanLabelStoriesByTargetTypeAndTargetValueAndLabelType(targetStyle.getId(), targetValue, weekPlanLabelStyle.getId());
    }

    @Override
    public List<WeekPlanLabelStory> findListByTargetTypeAndTargetValue(LabelTargetStyle targetStyle, Integer targetValue) {
        return weekPlanLabelStoryDao.getWeekPlanLabelStoriesByTargetTypeAndTargetValue(targetStyle.getId(), targetValue);
    }

}
