package com.ifenghui.storybookapi.app.studyplan.dao;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;


public interface WeekPlanJoinDao extends JpaRepository<WeekPlanJoin, Integer> {


    @Cacheable(cacheNames = "getDistinctByPlanTypeAndUserId",key = "'getDistinctByPlanTypeAndUserId'+#p0+'_'+#p1")
    @Query("select w from WeekPlanJoin as w where w.userId=:userId and w.planType=:planType")
    WeekPlanJoin getDistinctByPlanTypeAndUserId(
            @Param("userId") Integer userId,
            @Param("planType") Integer planType
    );

    @Cacheable(cacheNames = "countDistinctByPlanTypeAndUserId",key = "'countDistinctByPlanTypeAndUserId'+#p0+'_'+#p1")
    @Query("select count(w) from WeekPlanJoin as w where w.userId=:userId and w.planType=:planType")
    Long countDistinctByPlanTypeAndUserId(
            @Param("userId") Integer userId,
            @Param("planType") Integer planType
    );

    @Query("select w from WeekPlanJoin as w")
    Page<WeekPlanJoin> getAllByPage(
        Pageable pageable
    );

    @Query("select w from WeekPlanJoin as w where w.createTime <=:createTime")
    Page<WeekPlanJoin> getAllByDate(
            @Param("createTime") Date createTime,
            Pageable pageable
    );

    @Override

    @Caching(evict = {
            @CacheEvict(cacheNames = "getDistinctByPlanTypeAndUserId",key = "'getDistinctByPlanTypeAndUserId'+#p0.userId+'_'+#p0.planType"),
            @CacheEvict(cacheNames = "countDistinctByPlanTypeAndUserId",key = "'countDistinctByPlanTypeAndUserId'+#p0.userId+'_'+#p0.planType")
    })
    WeekPlanJoin save(WeekPlanJoin weekPlanJoin);


    @Query("from WeekPlanJoin  w where w.buyNum=0")
    Page<WeekPlanJoin> getAllByBuyNum( Pageable pageable);


    Page<WeekPlanJoin> findAllByBuyNumGreaterThan(
            Integer buyNum,Pageable pageable
    );
}
