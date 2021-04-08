package com.ifenghui.storybookapi.active1902.dao;

import com.ifenghui.storybookapi.active1902.entity.UserAwards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Date: 2019/2/19 14:10
 * @Description:
 */
public interface UserAwardsDao extends JpaRepository<UserAwards,Integer> {

    UserAwards findUserAwardsByUserIdAndScheduleId(Integer userId, Integer scheduleId);
    /**
     * 查询用户获得的奖励
     * @param userId
     * @return
     */
    List<UserAwards> findUserAwardsByUserId(Integer userId);
    /**
     * 通过type查询是否领取奖励
     * @param scheduleId
     * @param userId
     * @param type
     * @return
     */
    Integer countUserAwardsByScheduleIdAndUserIdAndType(Integer scheduleId, Integer userId, Integer type);
    Integer countUserAwardsByUserIdAndType(Integer userId, Integer type);
    Integer countUserAwardsByUserId(Integer userId);
}
