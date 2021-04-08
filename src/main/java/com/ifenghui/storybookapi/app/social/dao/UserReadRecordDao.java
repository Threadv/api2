package com.ifenghui.storybookapi.app.social.dao;



import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
/**
 * Created by jia on 2016/12/23.
 */
@Transactional
public interface UserReadRecordDao extends JpaRepository<UserReadRecord, Long> {


    @Query("select r from UserReadRecord  r where r.userId =:userId and r.type=:type ")
    Page<UserReadRecord> findReadRecordStoryByUserIdAndType(  @Param("userId") Long userId,@Param("type") Integer type,Pageable pageable);



    /**
     * 用于查询是否有记录
     * @param userId
     * @param storyId
     * @return
     */
    Long countByUserIdAndStoryId(Long userId,Long storyId);

    @Query("select r from UserReadRecord as  r where r.userId =:userId ")
    Page<UserReadRecord> getUserReadRecordByUserId(Long userId, Pageable pageable);

    @Query("select r from UserReadRecord as  r where r.userId =:userId and r.storyId=:storyId ")
    Page<UserReadRecord> getUserReadRecordByUserIdAndStoryId(@Param("userId")Long userId, @Param("storyId")Long storyId, Pageable pageable);


//
//    @Query("select count(readRecord) from UserReadRecord as readRecord where readRecord.createTime>=:beginTime and readRecord.createTime <=:endTime and readRecord.userId=:userId and readRecord.type=:storyType")
//    Long getReadRecordCountNumber(
//            @Param("userId") Long userId,
//            @Param("storyType") Integer type,
//            @Param("beginTime") java.util.Date beginTime,
//            @Param("endTime") java.util.Date endTime
//    );

    @Cacheable(cacheNames = "getReadRecordCountNumberCurrentDay",key = "'getReadRecordCountNumberCurrentDay'+#p0+'_'+#p1+'_'+T(com.ifenghui.storybookapi.style.RangeTimeStyle).CURRENT_DAY.endDate")
    @Query("select count(readRecord) from UserReadRecord as readRecord where readRecord.createTime>=:beginTime and readRecord.createTime <=:endTime and readRecord.userId=:userId and readRecord.type=:storyType")
    Long getReadRecordCountNumberCurrentDay(
            @Param("userId") Long userId,
            @Param("storyType") Integer type,
            @Param("beginTime") java.util.Date beginTime,
            @Param("endTime") java.util.Date endTime
    );
    @Cacheable(cacheNames = "getReadRecordCountNumberAllDay",key = "'getReadRecordCountNumberAllDay'+#p0+'_'+#p1")
    @Query("select count(readRecord) from UserReadRecord as readRecord where readRecord.createTime>=:beginTime and readRecord.createTime <=:endTime and readRecord.userId=:userId and readRecord.type=:storyType")
    Long getReadRecordCountNumberAllDay(
            @Param("userId") Long userId,
            @Param("storyType") Integer type,
            @Param("beginTime") java.util.Date beginTime,
            @Param("endTime") java.util.Date endTime
    );

    @Query(value = "select story_id from story_user_read_record  where user_id=:userId and type=:storyType group by story_id order by count(*) desc limit 1",nativeQuery = true)
    Long getFavoriteUserReadRecord(
        @Param("userId") Long userId,
        @Param("storyType") Integer type
    );


    @Query(value = "select * from story_user_read_record  u where u.id >= ?1 and u.id < ?2",nativeQuery = true)
    List<UserReadRecord> getAll(@Param("id1") Integer id1, @Param("id2") Integer id2);
    @Caching(evict = {
            @CacheEvict(cacheNames = "getReadRecordCountNumberCurrentDay",key = "'getReadRecordCountNumberCurrentDay'+#p0.userId+'_'+#p0.type+'_'+T(com.ifenghui.storybookapi.style.RangeTimeStyle).CURRENT_DAY.endDate"),
            @CacheEvict(cacheNames = "getReadRecordCountNumberAllDay",key = "'getReadRecordCountNumberAllDay'+#p0.userId+'_'+#p0.type")

    })
    @Override
    UserReadRecord save(UserReadRecord userReadRecord);


    @Query("from UserReadRecord  as record where record.userId =:userId and (record.type=7 or record.type=8)")
    List<UserReadRecord> findYiZhiYouXiByUserId(@Param("userId") Integer userId);
}
