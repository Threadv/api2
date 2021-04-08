package com.ifenghui.storybookapi.active1902.service;

import com.ifenghui.storybookapi.active1902.entity.Schedule;
import com.ifenghui.storybookapi.active1902.entity.UserAwards;
import com.ifenghui.storybookapi.active1902.style.AwardsStyle;

import java.util.List;

/**
 * @Date: 2019/2/19 14:52
 * @Description:
 */
public interface UserAwardsService {

    /**
     * 添加领取记录
     * @param userId
     * @param scheduleId
     * @param type
     * @param storyId
     * @param serialStoryId
     * @return
     */
    UserAwards addRecord(Integer userId, Integer scheduleId, Integer type, Integer storyId, Integer serialStoryId);
    /**
     * 查询用户未获得获得奖励的排期
     * @param userId
     * @return
     */
    List<Schedule> findUnGetAwardsByUserId(Integer userId);
    /**
     * 查询本期奖励
     * @param userId
     * @param scheduleId
     * @return
     */
    UserAwards getAwardsByUserIdAndScheduleId(Integer userId, Integer scheduleId);

    /**
     * 是否有其他未领取的奖励
     * @param userId
     * @param schedules
     * @return
     */
    boolean isGetAllAwards(Integer userId, List<Schedule> schedules);
    /**
     * 查询用户获得奖励
     * @param userId
     * @return
     */
    List<UserAwards> findAwardsByUserId(Integer userId);
    /**
     * 查询是否领取过奖励
     * @param userId
     * @param awardsStyle
     * @return
     */
    Integer countAwardsByType(Integer scheduleId, Integer userId, AwardsStyle awardsStyle);
    /**
     * 查询是否领取过奖励
     * @param userId
     * @param awardsStyle
     * @return
     */
    Integer countAwardsByTypeAndUserId(Integer userId, AwardsStyle awardsStyle);

    /**
     * 统计获得几次奖励
     * @param userId
     * @return
     */
    Integer countAwardsByUserId(Integer userId);
}
