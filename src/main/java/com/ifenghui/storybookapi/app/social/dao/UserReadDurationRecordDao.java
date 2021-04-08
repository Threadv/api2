package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.UserReadDurationRecord;
import com.ifenghui.storybookapi.style.RangeTimeStyle;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserReadDurationRecordDao extends JpaRepository<UserReadDurationRecord, Integer> {

//    @Query("select sum(duration.duration) from UserReadDurationRecord as duration where duration.createTime>=:beginTime and duration.createTime <=:endTime and duration.userId=:userId and duration.type=:storyType")
//    Integer getDurationCountNumber(
//            @Param("userId") Integer userId,
//            @Param("storyType") Integer type,
//            @Param("beginTime") java.util.Date beginTime,
//            @Param("endTime") java.util.Date endTime
//    );

    /**
     * 下面两个查询你功能是一样的区别是在注解中，增加了不同的缓存名称，分别用于当前天，和所有天的查询
     * @param userId
     * @param type
     * @param beginTime
     * @param endTime
     * @return
     */

    @Cacheable(cacheNames = "sumDurationCountNumberCurrentDay",key = "'sumDurationCountNumberCurrentDay'+#p0+'_'+#p1+'_'+T(com.ifenghui.storybookapi.style.RangeTimeStyle).CURRENT_DAY.endDate")
    @Query("select sum(duration.duration) from UserReadDurationRecord as duration where duration.createTime>=:beginTime and duration.createTime <=:endTime and duration.userId=:userId and duration.type=:storyType")
    Integer sumDurationCountNumberCurrentDay(
            @Param("userId") Integer userId,
            @Param("storyType") Integer type,
            @Param("beginTime") java.util.Date beginTime,
            @Param("endTime") java.util.Date endTime
    );

    @Cacheable(cacheNames = "countDurationCountNumberCurrentDay",key = "'countDurationCountNumberCurrentDay'+#p0+'_'+#p1+'_'+T(com.ifenghui.storybookapi.style.RangeTimeStyle).CURRENT_DAY.endDate")
    @Query("select count(duration) from UserReadDurationRecord as duration where duration.createTime>=:beginTime and duration.createTime <=:endTime and duration.userId=:userId and duration.type=:storyType")
    Integer countDurationCountNumberCurrentDay(
            @Param("userId") Integer userId,
            @Param("storyType") Integer type,
            @Param("beginTime") java.util.Date beginTime,
            @Param("endTime") java.util.Date endTime
    );

    @Cacheable(cacheNames = "sumDurationCountNumberAllDay",key = "'sumDurationCountNumberAllDay'+#p0+'_'+#p1")
    @Query("select sum(duration.duration) from UserReadDurationRecord as duration where duration.createTime>=:beginTime and duration.createTime <=:endTime and duration.userId=:userId and duration.type=:storyType")
    Integer sumDurationCountNumberAllDay(
            @Param("userId") Integer userId,
            @Param("storyType") Integer type,
            @Param("beginTime") java.util.Date beginTime,
            @Param("endTime") java.util.Date endTime
    );

    @Cacheable(cacheNames = "countDurationCountNumberAllDay",key = "'countDurationCountNumberAllDay'+#p0+'_'+#p1")
    @Query("select count(duration) from UserReadDurationRecord as duration where duration.createTime>=:beginTime and duration.createTime <=:endTime and duration.userId=:userId and duration.type=:storyType")
    Integer countDurationCountNumberAllDay(
            @Param("userId") Integer userId,
            @Param("storyType") Integer type,
            @Param("beginTime") java.util.Date beginTime,
            @Param("endTime") java.util.Date endTime
    );

    @Caching(evict = {
            @CacheEvict(cacheNames = "sumDurationCountNumberCurrentDay",key = "'sumDurationCountNumberCurrentDay'+#p0.userId+'_'+#p0.type+'_'+T(com.ifenghui.storybookapi.style.RangeTimeStyle).CURRENT_DAY.endDate"),
            @CacheEvict(cacheNames = "countDurationCountNumberCurrentDay",key = "'countDurationCountNumberCurrentDay'+#p0.userId+'_'+#p0.type+'_'+T(com.ifenghui.storybookapi.style.RangeTimeStyle).CURRENT_DAY.endDate"),
            @CacheEvict(cacheNames = "sumDurationCountNumberAllDay",key = "'sumDurationCountNumberAllDay'+#p0.userId+'_'+#p0.type"),
            @CacheEvict(cacheNames = "countDurationCountNumberAllDay",key = "'countDurationCountNumberAllDay'+#p0.userId+'_'+#p0.type")
    })
    @Override
    UserReadDurationRecord save(UserReadDurationRecord userReadDurationRecord);

//    @Query("select sum(duration.duration) from UserReadDurationRecord as duration" +
//            " where duration.createTime>=:rangeTimeStyle.getBeginDate()" +
//            " and duration.createTime <=:rangeTimeStyle.getEndDate()" +
//            " and duration.userId=:userId and duration.type=:storyType")
//    Integer getDurationCountNumber(
//            @Param("userId") Integer userId,
//            @Param("storyType") Integer type,
//            @Param("rangeTimeStyle")RangeTimeStyle rangeTimeStyle
//            );

}
