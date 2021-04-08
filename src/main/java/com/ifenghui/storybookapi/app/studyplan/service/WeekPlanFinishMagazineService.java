package com.ifenghui.storybookapi.app.studyplan.service;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanFinishMagazine;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanMagazine;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import java.util.List;

public interface WeekPlanFinishMagazineService {

    /**
     * 根据杂志任务id 和 用户id 和 周计划类型 获取杂志完成记录
     * @param magPlanId
     * @param userId
     * @param weekPlanStyle
     * @return
     */
    WeekPlanFinishMagazine findOneByMagPlanIdAndUserIdAndPlanType(Integer magPlanId, Integer userId, WeekPlanStyle weekPlanStyle);

    /**
     * 根据杂志任务id 和 用户id 和 周计划类型 获取杂志完成记录数量
     * @param magPlanId
     * @param userId
     * @param weekPlanStyle
     * @return
     */
    Long countByMagPlanIdAndUserIdAndPlanType(Integer magPlanId, Integer userId, WeekPlanStyle weekPlanStyle);

    /**
     * 添加杂志完成记录
     * @param magPlanId
     * @param userId
     * @param wordNum
     * @param weekPlanStyle
     * @return
     */
    WeekPlanFinishMagazine addWeekPlanFinishMagazine(Integer magPlanId, Integer userId, Integer wordNum, WeekPlanStyle weekPlanStyle);

    /**
     * 创建杂志完成记录
     * @param magPlanId
     * @param userId
     * @param weekPlanStyle
     * @return
     */
    WeekPlanFinishMagazine createWeekPlanFinishMagazine(Integer magPlanId, Integer userId, WeekPlanStyle weekPlanStyle);

    /**
     * 检测是否需要修改杂志任务完成情况
     * @param weekPlanMagazineList
     * @param userId
     */
    void checkIsNeedChangeWeekPlanFinishStatus(List<WeekPlanMagazine> weekPlanMagazineList, Integer userId);
}
