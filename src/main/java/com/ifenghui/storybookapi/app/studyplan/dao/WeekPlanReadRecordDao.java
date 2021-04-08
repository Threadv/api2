package com.ifenghui.storybookapi.app.studyplan.dao;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanReadRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface WeekPlanReadRecordDao extends JpaRepository<WeekPlanReadRecord, Integer> {


    /**
     * 查询weekplanreadrecord id -id
     * @param id1
     * @param id2
     * @return
     */
    @Query(value = "select * from story_week_plan_read_record  u where u.id >= ?1 and u.id < ?2 and u.read_type=1",nativeQuery = true)
    List<WeekPlanReadRecord> getAllByReadType(@Param("id1") Integer id1, @Param("id2") Integer id2);


    /**
     *
     * @param userId
     * @param planType
     * @param storyId
     * @return
     */
    WeekPlanReadRecord getWeekPlanReadRecordByUserIdAndPlanTypeAndTargetValue(Integer userId,Integer planType,Integer storyId);

    @Query("select w from WeekPlanReadRecord as w where w.userId=:userId and w.targetType=:targetType and w.targetValue=:targetValue and w.planType=:planType")
    public WeekPlanReadRecord getDistinctByUserIdAndTargetTypeAndTargetValueAndPlanType(
            @Param("userId") Integer userId,
            @Param("targetType") Integer targetType,
            @Param("targetValue") Integer targetValue,
            @Param("planType") Integer planType
    );


    @Query("select w from WeekPlanReadRecord as w where w.userId=:userId and w.planType=:planType")
    Page<WeekPlanReadRecord> getByUserIdAndPlanType(
            @Param("userId") Integer userId,
            @Param("planType") Integer planType,
            Pageable pageable
    );

    @Query("select w from WeekPlanReadRecord as w where w.userId=:userId and w.targetType=:targetType and w.planType=:planType")
    public List<WeekPlanReadRecord> getListByUserIdAndPlanTypeAndTargetType(
            @Param("userId") Integer userId,
            @Param("targetType") Integer targetType,
            @Param("planType") Integer planType
    );

    @Cacheable(cacheNames = "getWeekPlanReadRecordListByUserIdAndPlanType",key = "'getWeekPlanReadRecordListByUserIdAndPlanType'+#p0+'_'+#p1")
    @Query("select w from WeekPlanReadRecord as w where w.userId=:userId and w.planType=:planType")
    public List<WeekPlanReadRecord> getListByUserIdAndPlanType(
            @Param("userId") Integer userId,
            @Param("planType") Integer planType
    );

//    @Query("select count(w) from WeekPlanReadRecord as w where w.userId=:userId and w.planType=:planType and w.targetType=:targetType and w.readType=:readType")
//    public Integer getCountByPlanTypeAndTargetTypeAndUserIdAndReadType(
//            @Param("planType") Integer planType,
//            @Param("targetType") Integer targetType,
//            @Param("userId") Integer userId,
//            @Param("readType") Integer readType
//    );

    @Cacheable(cacheNames = "weekPlanReadRecordDao_sum",key = "'weekPlanReadRecordDao_sum'+#p0+'_'+#p1+'_'+#p2+'_'+#p3")
    @Query("select sum(w.wordCount) from WeekPlanReadRecord as w where w.userId=:userId and w.planType=:planType and w.targetType=:targetType and w.readType=:readType and w.isStory=:isStory")
    public Integer getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(
            @Param("planType") Integer planType,
            @Param("targetType") Integer targetType,
            @Param("userId") Integer userId,
            @Param("readType") Integer readType,
            @Param("isStory") Integer isStory
    );

    //因为sum空数据会返回空，无法使用缓存，所以预先用count查下，如果count为0则字数也是0
    @Cacheable(cacheNames = "weekPlanReadRecordDao_count",key = "'weekPlanReadRecordDao_count'+#p0+'_'+#p1+'_'+#p2+'_'+#p3")
    @Query("select count(w) from WeekPlanReadRecord as w where w.userId=:userId and w.planType=:planType and w.targetType=:targetType and w.readType=:readType and w.isStory=:isStory")
    public Integer countWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(
            @Param("planType") Integer planType,
            @Param("targetType") Integer targetType,
            @Param("userId") Integer userId,
            @Param("readType") Integer readType,
            @Param("isStory") Integer isStory
    );

//    @CacheEvict(cacheNames = "weekPlanReadRecordDao_sum",key = "'weekPlanReadRecordDao_sum'+#p0.planType+'_'+#p0.targetType+'_'+#p0.userId+'_'+#p0.readType+''")
    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "weekPlanReadRecordDao_count",key = "'weekPlanReadRecordDao_count'+#p0.planType+'_'+#p0.targetType+'_'+#p0.userId+'_'+#p0.readType"),
            @CacheEvict(cacheNames = "weekPlanReadRecordDao_sum",key = "'weekPlanReadRecordDao_sum'+#p0.planType+'_'+#p0.targetType+'_'+#p0.userId+'_'+#p0.readType"),
            @CacheEvict(cacheNames = "getWeekPlanReadRecordListByUserIdAndPlanType",key = "'getWeekPlanReadRecordListByUserIdAndPlanType'+#result.userId+'_'+#result.planType")
    })
//    @CacheEvict(cacheNames = "weekPlanReadRecordDao_sum",key = "'weekPlanReadRecordDao_sum'+#p0.planType+'_'+#p0.targetType+'_'+#p0.userId+'_'+#p0.readType+'_'+#p0.isStory+''")
//    @CacheEvict(cacheNames = "weekPlanReadRecordDao_sum",key = "'weekPlanReadRecordDao_sum'+#p0.planType+'_'+#p0.targetType+'_'+#p0.userId+'_'+#p0.readType+'_'+#p0.isStory+''")
    WeekPlanReadRecord save(WeekPlanReadRecord weekPlanReadRecord);
}
