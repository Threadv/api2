package com.ifenghui.storybookapi.app.studyplan.service;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskFinish;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskRelate;
import com.ifenghui.storybookapi.style.LabelTargetStyle;

import java.util.List;

public interface WeekPlanTaskFinishService {

    /**
     * 获得周小任务完成记录根据小任务id和周任务id
     * @param planTaskId
     * @param weekPlanId
     * @param userId
     * @return
     */
    WeekPlanTaskFinish getWeekPlanTaskFinishByTaskIdAndWeekPlanId(Integer planTaskId, Integer weekPlanId, Integer userId);

    /**
     * 获得周小任务完成记录根据小任务id和周任务id 数量
     * @param planTaskId
     * @param weekPlanId
     * @param userId
     * @return
     */
    Long getCountByTaskIdAndWeekPlanId(Integer planTaskId, Integer weekPlanId, Integer userId);
    /**
     * 获取某个周任务用户获得分数值
     * @param weekPlanId
     * @param userId
     * @return
     */
    Integer getScoreCountByWeekPlanIdAndUserId(Integer weekPlanId, Integer userId);

    /**
     * 添加小任务完成记录
     * @param planTaskId
     * @param weekPlanId
     * @param userId
     * @param score
     * @return
     */
    WeekPlanTaskFinish addWeekPlanTaskFinish (Integer planTaskId, Integer weekPlanId, Integer userId, Integer score);

    /**
     * 创建小任务完成记录
     * @param planTaskId
     * @param weekPlanId
     * @param userId
     * @param score
     */
    void createWeekPlanTaskFinish(Integer planTaskId, Integer weekPlanId, Integer userId, Integer score);

    /**
     * 检测是否需要修改周任务完成状态
     * @param weekPlanTaskRelateList
     * @param userId
     */
    void checkIsNeedChangeWeekPlanFinishStatus(List<WeekPlanTaskRelate> weekPlanTaskRelateList, Integer userId);

    /**
     * 设置周任务完成入口
     * @param targetValue
     * @param labelTargetStyle
     * @param userId
     */
    void setWeekPlanTaskFinishEnter(Integer targetValue, LabelTargetStyle labelTargetStyle, Integer userId);
}
