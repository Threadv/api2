package com.ifenghui.storybookapi.app.studyplan.dao;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanUserRecord;
import jdk.nashorn.internal.runtime.FindProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * 用户推送记录
 */
public interface WeekPlanUserRecordDao extends JpaRepository<WeekPlanUserRecord, Integer> {

    @CacheEvict(cacheNames = "weekPlanUserRecord_v1",key = "'weekPlanUserRecord_v1'+#p0.userId+'_'+#p0.weekPlanId")
    @Override
    WeekPlanUserRecord save(WeekPlanUserRecord weekPlanUserRecord);

    @Modifying
    @Transactional
    @Query("delete  from WeekPlanUserRecord w where w.userId=:userId and w.weekPlanId =:weekPlanId")
    void deleteByUserIdAndPlanId(@Param("userId")Integer userId ,   @Param("weekPlanId") Integer weekPlanId);

    @Query("select count(w) from WeekPlanUserRecord w where w.userId=:userId and w.weekPlanId <=52 and w.isFinish =1")
    Integer countTwoFourFinishWeekNum(@Param("userId")Integer userId);

    @Query("select count(w) from WeekPlanUserRecord w where w.userId=:userId and w.weekPlanId >=53 and w.isFinish =1")
    Integer countFourSixFinishWeekNum(@Param("userId")Integer userId);

    @Cacheable(cacheNames = "weekPlanUserRecord_v1",key = "'weekPlanUserRecord_v1'+#p0+'_'+#p1")
    WeekPlanUserRecord getDistinctByUserIdAndWeekPlanId(Integer userId, Integer weekPlanId);

    @Query("select w from WeekPlanUserRecord as w where w.userId=:userId and w.weekPlanId <= 52")
    List<WeekPlanUserRecord> getListTwoFourByUserId(
        @Param("userId") Integer userId,
        Pageable pageable
    );

    @Query("select w from WeekPlanUserRecord as w where w.userId=:userId and w.weekPlanId >= 53")
    List<WeekPlanUserRecord> getListFourSixByUserId(
            @Param("userId") Integer userId,
            Pageable pageable
    );

    @Query("select count(w) from WeekPlanUserRecord as w where w.userId=:userId and w.magTaskId=:magTaskId")
    Integer getCountByUserIdAndMagTaskId(
            @Param("userId") Integer userId,
            @Param("magTaskId") Integer magTaskId
    );

    @Query("select count(w) from WeekPlanUserRecord as w where w.userId=:userId and w.weekPlanId=:weekPlanId")
    Integer getCountByUserIdAndWeekPlanId(
            @Param("userId") Integer userId,
            @Param("weekPlanId") Integer weekPlanId
    );

    List<WeekPlanUserRecord> getWeekPlanUserRecordsByUserIdAndMagTaskId(Integer userId, Integer magTaskId);

    List<WeekPlanUserRecord> getWeekPlanUserRecordsByUserIdAndWeekPlanId(Integer userId, Integer weekPlanId);
}
