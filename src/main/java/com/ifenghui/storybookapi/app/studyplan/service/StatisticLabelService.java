package com.ifenghui.storybookapi.app.studyplan.service;

import com.ifenghui.storybookapi.app.social.service.TotalReadStatistic;
import com.ifenghui.storybookapi.app.studyplan.response.GetStatisticWeekPlanLabelResponse;
import com.ifenghui.storybookapi.app.studyplan.response.StaticLabel;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import java.text.ParseException;
import java.util.Date;

public interface StatisticLabelService {
    /**
     * 获取阅读文字总量
     * @param userId
     * @param planStyle
     * @return
     */
    StaticLabel getStaticLabelOne(Integer userId, WeekPlanStyle planStyle, Integer storyCount, Integer storyWordCount, Integer magCount, Integer magWordCount);

    /**
     * 阅读时长
     * @param userId
     * @return
     * @throws ParseException
     */
    StaticLabel getStaticLabelTwo(Integer userId, TotalReadStatistic totalReadStatistic) throws ParseException;


    /**
     * 获得时长
     * @param number
     * @return
     */
    String getName(Integer number);

    /**
     * 阅读类型统计
     * @param response
     * @return
     */
    StaticLabel getStaticLabelThree(GetStatisticWeekPlanLabelResponse response);

    /**
     * 认知学习
     * @param response
     * @return
     */
    StaticLabel getStaticLabelFour(GetStatisticWeekPlanLabelResponse response);

    /**
     * 识字学习
     * @param response
     * @return
     */
    StaticLabel getStaticLabelFive(GetStatisticWeekPlanLabelResponse response);

    /**
     * 五大领域
     * @param response
     * @return
     */
    StaticLabel getStaticLabelSix(GetStatisticWeekPlanLabelResponse response);

    /**
     * 计算时差
     * @param date1
     * @param date2
     * @return
     */
    int differentDaysByMillisecond(Date date1, Date date2);
}
