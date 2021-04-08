package com.ifenghui.storybookapi.app.studyplan.service;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabelStory;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskRelate;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.WeekPlanLabelStyle;

import java.util.HashMap;
import java.util.List;

public interface WeekPlanLabelService {

    WeekPlanLabel findOne(int id);

    /**
     *  根据关联列表 获得标签列表并且设置是否完成
     * @param weekPlanLabelStoryList
     * @param isFinish
     * @return
     */
    List<WeekPlanLabel> findListFromWeekPlanLabelStoryList(List<WeekPlanLabelStory> weekPlanLabelStoryList, Integer isFinish);

    /**
     * 通过任务关联 和 标签类型 获得特定的类型标签列表
     * @param weekPlanTaskRelateList
     * @param weekPlanLabelStyle
     * @return
     */
    List<WeekPlanLabel> findListByTaskRelateAndType(List<WeekPlanTaskRelate> weekPlanTaskRelateList, WeekPlanLabelStyle weekPlanLabelStyle);

    /**
     * 保留唯一的标签列表
     * @param weekPlanLabelList
     * @return
     */
    List<WeekPlanLabel> dealUniqueListFromWeekPlanLabelList(List<WeekPlanLabel> weekPlanLabelList);

    /**
     *  根据用户，周计划id，标签类型 获得标签列表
     * @param weekPlanId
     * @param userId
     * @param weekPlanLabelStyle
     * @return
     */
    List<WeekPlanLabel> findListByWeekPlanIdAndUserId(Integer weekPlanId, Integer userId, WeekPlanLabelStyle weekPlanLabelStyle);

    /**
     * 去除未完成的标签
     * @param weekPlanLabelList
     * @return
     */
    List<WeekPlanLabel> removeIsNotFinish(List<WeekPlanLabel> weekPlanLabelList);

    /**
     * 根据标签类型获取所有标签
     * @param weekPlanLabelStyle
     * @return
     */
    List<WeekPlanLabel> findListByLabelType(WeekPlanLabelStyle weekPlanLabelStyle);

    /**
     * 根据标签类型 获得所有标签组成hashMap
     * @param weekPlanLabelStyle
     * @return
     */
    HashMap<Integer, WeekPlanLabel> getHashMapLabelListByType(WeekPlanLabelStyle weekPlanLabelStyle);
}
