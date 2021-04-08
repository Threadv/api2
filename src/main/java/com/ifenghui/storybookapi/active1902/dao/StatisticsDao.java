package com.ifenghui.storybookapi.active1902.dao;

import com.ifenghui.storybookapi.active1902.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Date: 2019/2/19 14:10
 * @Description:
 */
@Transactional
public interface StatisticsDao extends JpaRepository<Statistics,Integer> {


    @Query("select s from Statistics  s where s.userId=:userId and s.type=:type and s.createTime >=:date")
    Statistics findStatisticsByUserIdAndTypeaAndCreateTime(@Param("userId") Integer userId, @Param("type") Integer type, @Param("date") Date date);

    @Modifying
    @Query(value = "UPDATE activity1902_schedule sc,(SELECT COUNT(DISTINCT user_id) AS c FROM `activity1902_statistics` WHERE schedule_id=:schId and type=1) cc  SET sc.add_num=cc.c \n" +
            "WHERE sc.id=:schId",nativeQuery = true)
    public void updateType1(@Param("schId") int schId);

    @Modifying
    @Query(value = "UPDATE activity1902_schedule sc,(SELECT COUNT(DISTINCT user_id) AS c FROM `activity1902_statistics` WHERE schedule_id=:schId and type=4) cc  SET sc.finish_num=cc.c \n" +
            "WHERE sc.id=:schId",nativeQuery = true)
    public void updateType4(@Param("schId") int schId);
}
