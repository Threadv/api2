package com.ifenghui.storybookapi.app.studyplan.service;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanUserRecord;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface WeekPlanUserRecordService {


    /**
     * 删除记录
     * @param userId
     * @param weekPlanId
     */
    void  deleteRecordsByUserIdAndWeekPlanId(Integer userId,Integer weekPlanId);

    /**
     * 查询完成周数
     * @param planStyle
     * @param userId
     * @return
     */
    Integer countFinishWeekNum(WeekPlanStyle planStyle,Integer userId);
    /**
     * 添加周计划用户记录方法（内部调用）
     * @param weekPlanId
     * @param userId
     * @param magTaskId
     * @return
     */
    WeekPlanUserRecord addWeekPlanUserRecord(
            Integer weekPlanId,
            Integer userId,
            Integer magTaskId
    );

    /**
     * 创建周计划用户记录方法（外部调用）
     * @param weekPlanId
     * @param userId
     * @return
     */
    WeekPlanUserRecord createWeekPlanUserRecord(Integer weekPlanId, Integer userId);

    /**
     * 获取用户与杂志任务关联
     * @param userId
     * @param magTaskId
     * @return
     */
    Integer getCountByUserIdAndMagTaskId(Integer userId, Integer magTaskId);

    Integer getCountByUserIdAndWeekPlanId(Integer userId, Integer weekPlanId);

    /**
     * 工具方法通过时间获取需要绑定的杂志任务id
     * @param date
     * @return
     */
    Integer getMagTaskDate(Date date);

    /**
     * 获得当前周一
     * @param date
     * @return
     */
    Date getThisWeekMonday(Date date);
    /**
     * 获得用户对应周计划用户记录（通过周计划id）
     * @param weekPlanId
     * @param userId
     * @return
     */
    WeekPlanUserRecord isHasWeekPlanUserRecord(Integer weekPlanId, Integer userId);

    /**
     * 获取所有包含此杂志任务的周计划用户记录
     * @param magTaskId
     * @param userId
     * @return
     */
    List<WeekPlanUserRecord> findListByMagTaskIdAndUserId(Integer magTaskId, Integer userId);

    /**
     * 未使用
     * @param weekPlanId
     * @param userId
     * @return
     */
    List<WeekPlanUserRecord> findListByPlanIdAndUserId(Integer weekPlanId, Integer userId);

    /**
     * 设置周计划用户记录完成状态
     * @param magTaskId
     * @param userId
     */
    void setWeekPlanUserRecordFinishStatusByMagTaskId(Integer magTaskId, Integer userId);

    /**
     * 设置完成周计划小任务后弹星星
     * @param weekPlanId
     * @param userId
     */
    void setFinishShowStar(Integer weekPlanId, Integer userId);

    /**
     * 设置周计划用户记录完成数量
     * @param weekPlanId
     * @param userId
     * @param finishNum
     */
    void setWeekPlanUserRecordFinishNum(Integer weekPlanId, Integer userId, Integer finishNum);

    /**
     * 定时任务时给用户推送周计划用户记录（相当于推送周任务）（入口）
     */
    void dayTaskSendWeekPlanUserRecord();

    /**
     * 修复推送数据入口
     * @throws ParseException
     */
    void recoverDayTaskSend() throws ParseException;

    /**
     * 通过周计划参加记录查找用户进行推送
     * @param weekPlanJoinList
     */
    void sendWeekPlanUserRecordByList(List<WeekPlanJoin> weekPlanJoinList);

//    /**
//     * 通过分页推送
//     * @param pageNo
//     * @param pageSize
//     */
//    void sendWeekPlanUserRecordByPage(int pageNo, int pageSize);

    /**
     * 通过特定时间推送记录
     * @param pageNo
     * @param pageSize
     * @param date
     */
    void sendWeekPlanUserRecordByDate(int pageNo, int pageSize, Date date);

    /**
     * 根据不同周计划类型推送用户记录
     * @param userId
     * @param weekPlanStyle
     */
    void sendWeekPlanUserRecordByUserIdAndPlanType(Integer userId, WeekPlanStyle weekPlanStyle);

    void sendWeekPlanUserRecordByUserIdAndPlanTypeToSomeone(Integer userId, WeekPlanStyle planStyle);

    /**
     * 用戶購買完成后，推送第二周記錄
     * 需要用戶已經完成後買后觸發
     * @param userId
     */
    void sendSecondWeek(Integer userId);
}
