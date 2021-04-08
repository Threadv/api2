package com.ifenghui.storybookapi.app.studyplan.service;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanReadRecord;
import com.ifenghui.storybookapi.app.studyplan.response.GetStatisticWeekPlanLabelResponse;
import com.ifenghui.storybookapi.app.studyplan.response.StatisticWeekPlanLabel;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.ReadRecordTypeStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import java.util.List;

public interface WeekPlanReadRecordService {


    /**
     * 修改week_plan_read_record数据
     * @param count
     */
    void updateWeekPlanDate(Integer count);

    /**
     * 冗余数据 user_read_record -> weekplanreadrecord
     * @param count
     */
    void updateDate(Integer count,Integer id1);
    /**
     * 添加用户阅读记录
     * @param targetValue
     * @param userId
     * @param wordCount
     * @param weekPlanStyle
     * @param labelTargetStyle
     * @param recordTypeStyle
     * @return
     */
    WeekPlanReadRecord addWeekPlanReadRecord(Integer targetValue, Integer userId, Integer wordCount, WeekPlanStyle weekPlanStyle, LabelTargetStyle labelTargetStyle, ReadRecordTypeStyle recordTypeStyle, Integer isStory);

    /**
     * 通过不同类型和对应值 查找用户的阅读记录
     * @param targetValue
     * @param userId
     * @param weekPlanStyle
     * @param labelTargetStyle
     * @return
     */
    WeekPlanReadRecord findOneByTargetValueAndUserIdAndPlanTypeAndTargetType(Integer targetValue, Integer userId, WeekPlanStyle weekPlanStyle, LabelTargetStyle labelTargetStyle);

    /**
     * 创建用户阅读记录
     * @param targetValue
     * @param userId
     * @param wordCount
     * @param weekPlanStyle
     * @param labelTargetStyle
     * @param recordTypeStyle
     */
    void createWeekPlanReadRecord(Integer targetValue, Integer userId, Integer wordCount, WeekPlanStyle weekPlanStyle, LabelTargetStyle labelTargetStyle, ReadRecordTypeStyle recordTypeStyle, Integer isStory);

    /**
     * 根据 标签类型 和 对应值 和 周计划类型 获取用户阅读记录（未使用）
     * @param weekPlanStyle
     * @param labelTargetStyle
     * @param userId
     * @return
     */
    List<WeekPlanReadRecord> getListByPlanTypeAndTargetTypeAndTargetValue(WeekPlanStyle weekPlanStyle, LabelTargetStyle labelTargetStyle, Integer userId);

    /**
     * 根据 周计划类型 获取用户阅读记录
     * @param weekPlanStyle
     * @param userId
     * @return
     */
    List<WeekPlanReadRecord> getListByPlanTypeAndUserId(WeekPlanStyle weekPlanStyle, Integer userId);

    /**
     * 通过阅读记录格式化成统计对象
     * @param weekPlanReadRecord
     * @return
     */
    StatisticWeekPlanLabel getStatisticByWeekPlanReadRecord(WeekPlanReadRecord weekPlanReadRecord);

    /**
     * 获取统计结果
     * @param userId
     * @param weekPlanStyle
     * @return
     */
    GetStatisticWeekPlanLabelResponse getStatisticWeekPlanLabel(Integer userId, WeekPlanStyle weekPlanStyle);

    /**
     * 根据计划类型 阅读类型 标签类型 获取用户阅读条目数
     * @param planType
     * @param targetType
     * @param userId
     * @param readType
     * @return
     */
    Integer getCountByPlanTypeAndTargetTypeAndUserIdAndReadType(WeekPlanStyle planType, LabelTargetStyle targetType, Integer userId, ReadRecordTypeStyle readType, Integer isStory);

    /**
     * 根据计划类型 阅读类型 标签类型 获取用户阅读文字量总数
     * @param planType
     * @param targetType
     * @param userId
     * @param readType
     * @return
     */
    Integer getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(WeekPlanStyle planType, LabelTargetStyle targetType, Integer userId, ReadRecordTypeStyle readType, Integer isStory);

    /**
     * 第一条阅读记录
     * @param userId
     * @param planType
     * @return
     */
    WeekPlanReadRecord getFirstWeekPlanReadRecord(Integer userId, WeekPlanStyle planType);

}
