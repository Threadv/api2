package com.ifenghui.storybookapi.app.studyplan.service;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabelStory;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.WeekPlanLabelStyle;

import java.util.List;

public interface WeekPlanLabelStoryService {
    /**
     * 根据 标签类型 任务类型 和 值 查找标签和故事 视频 关联
     * @param targetStyle
     * @param targetValue
     * @param weekPlanLabelStyle
     * @return
     */
    List<WeekPlanLabelStory> findListByTargetTypeAndTargetValueAndLabelType(LabelTargetStyle targetStyle, Integer targetValue, WeekPlanLabelStyle weekPlanLabelStyle);

    /**
     * 根据 目标类型和值 获取 标签和故事 视频 关联
     * @param targetStyle
     * @param targetValue
     * @return
     */
    List<WeekPlanLabelStory> findListByTargetTypeAndTargetValue(LabelTargetStyle targetStyle, Integer targetValue);
}
