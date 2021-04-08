package com.ifenghui.storybookapi.app.studyplan.service;

import com.ifenghui.storybookapi.app.studyplan.entity.ParentsGuide;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskRelate;
import com.ifenghui.storybookapi.app.studyplan.response.WeekStudyReportResponse;
import com.ifenghui.storybookapi.style.LabelTargetStyle;

import java.util.List;

public interface WeekPlanTaskRelateService {
    /**
     * 获得周任务的小任务关联
     * @param weekPlanId
     * @param userId
     * @return
     */
    List<WeekPlanTaskRelate> getWeekPlanTaskRelateListByWeekPlanId(Integer weekPlanId, Integer userId);

    /**
     *  设置周学习报告数据
     * @param response
     * @param weekPlanId
     * @param userId
     */
    void setDataToWeekStudyReportResponse(WeekStudyReportResponse response, Integer weekPlanId, Integer userId);

    /**
     * 查找周计划小任务列表根据 故事id 和 类型
     * @param storyId
     * @param targetStyle
     * @return
     */
    List<WeekPlanTaskRelate> getRecordByStoryIdAndTargetType(Integer storyId, LabelTargetStyle targetStyle);

    WeekPlanTaskRelate findOne(Integer id);

    Integer countByStoryId(Integer storyId);

    /**
     * 获取关联信息
     * @param id
     * @return
     */
    WeekPlanTaskRelate getWeekPlanTaskRelateById(Integer id);

    /**
     * 获取家长导读
     * @param weekPlanId
     * @param dayNum
     * @return
     */
//    ParentsGuide getParentsGuideByWeekPlanIdAndDayNum(Integer weekPlanId, Integer dayNum);
}
