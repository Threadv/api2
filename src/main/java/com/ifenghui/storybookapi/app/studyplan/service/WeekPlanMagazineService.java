package com.ifenghui.storybookapi.app.studyplan.service;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanMagazine;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import java.util.List;

public interface WeekPlanMagazineService {
    /**
     * 根据日期（就是杂志任务唯一确认标识），周计划类型 获取杂志任务列表
     * @param date
     * @param userId
     * @param weekPlanStyle
     * @return
     */
    List<WeekPlanMagazine> getWeekPlanMagazineByDate(Integer date, Integer userId, WeekPlanStyle weekPlanStyle);
    WeekPlanMagazine findOne(Integer id);
}
