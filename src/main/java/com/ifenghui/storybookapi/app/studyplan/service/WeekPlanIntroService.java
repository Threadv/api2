package com.ifenghui.storybookapi.app.studyplan.service;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanIntro;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WeekPlanIntroService {
    /**
     * 获取比weekNum之前的周计划介绍
     * @param weekNum
     * @param weekPlanStyle
     * @return
     */
    List<WeekPlanIntro> getWeekPlanIntroListByWeekNum(Integer weekNum, WeekPlanStyle weekPlanStyle);

    /**
     * 根据 某周 和 周计划类型 获得周计划介绍
     * @param weekNum
     * @param weekPlanStyle
     * @return
     */
    WeekPlanIntro getWeekPlanIntroByWeekNum(Integer weekNum, WeekPlanStyle weekPlanStyle);

    WeekPlanIntro findOne(Integer id);

    /**
     * 获取某个用户的特定类型周计划介绍列表
     * @param pageNo
     * @param pageSize
     * @param weekPlanStyle
     * @param userId
     * @return
     */
    Page<WeekPlanIntro> getWeekPlanIntroPage(Integer pageNo, Integer pageSize, WeekPlanStyle weekPlanStyle, Integer userId);
}
