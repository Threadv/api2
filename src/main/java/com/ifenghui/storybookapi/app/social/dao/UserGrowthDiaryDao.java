package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiary;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserGrowthDiaryDao extends JpaRepository<UserGrowthDiary, Integer> {

    @Query("select diary from UserGrowthDiary as diary where diary.id =:id and diary.userId =:userId")
    UserGrowthDiary getUserGrowthDiaryByIdAndUserId(
            @Param("id") Integer id,
            @Param("userId") Integer userId
    );

    @Query("select diary from UserGrowthDiary as diary where diary.recordDate <=:recordDate and diary.userId=:userId and diary.status=1")
    Page<UserGrowthDiary> getUserGrowthDiaryPageByUserIdBefore(
            @Param("recordDate") Date recordDate,
            @Param("userId") Integer userId,
            Pageable pageable
    );

    @Query("select diary from UserGrowthDiary as diary where diary.userId =:userId and diary.status =:status and diary.monthDate =:monthDate")
    Page<UserGrowthDiary> getUserGrowthDiariesByUserIdAndStatusAndMonthDate(
            @Param("userId") Integer userId,
            @Param("status") Integer status,
            @Param("monthDate") Integer monthDate,
            Pageable pageable
    );

    @Query("select diary from UserGrowthDiary as diary where diary.userId =:userId and diary.status =:status")
    Page<UserGrowthDiary> getUserGrowthDiariesByUserIdAndStatus(
            @Param("userId") Integer userId,
            @Param("status") Integer status,
            Pageable pageable
    );

    @Query("select diary from UserGrowthDiary as diary where diary.createTime >=:beginTime and diary.createTime <=:endTime and diary.userId=:userId")
    Page<UserGrowthDiary> getUserGrowthDiaryListByTime(
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime,
            @Param("userId") Integer userId,
            Pageable pageable
    );

    @Query("select diary from UserGrowthDiary as diary where diary.recordDate >:recordDate and diary.userId=:userId and diary.status=1")
    Page<UserGrowthDiary> getUserGrowthDiaryPageByUserIdAfter(
            @Param("recordDate") Date recordDate,
            @Param("userId") Integer userId,
            Pageable pageable
    );

    @Query(value = "select distinct(month_date) from story_user_growth_diary where user_id=:userId and status=1", nativeQuery = true)
    List<Integer> getMonthDataList(
            @Param("userId") Integer userId
    );

    @Override
    @Cacheable(
        cacheNames = "userGrowthFindOne",
        key = "'userGrowthDiaryId_'+#p0"
    )
    UserGrowthDiary findOne(Integer id);

    @Override
    @Caching(put = {
        @CachePut(cacheNames = "userGrowthFindOne", key = "'userGrowthDiaryId_'+#p0.id")
    })
    UserGrowthDiary save(UserGrowthDiary userGrowthDiary);



}
