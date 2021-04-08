package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by wml on 2017/2/16.
 */
@Transactional
public interface SubscriptionRecordDao extends JpaRepository<SubscriptionRecord, Long> {


//        Page<SubscriptionRecord> getSubscriptionRecordsByUserId(Long userId, Pageable pageable);
        @Query("from SubscriptionRecord s where s.status=1 and s.user=:user")
        Page<SubscriptionRecord> getSubscriptionRecordsByUserId(@Param("user")User user,Pageable pageable);

        @Query("from SubscriptionRecord s where s.status=1 and s.startTime<:nowDate and s.endTime>:nowDate")
        List<SubscriptionRecord> getSubscriptionRecordsByTime(@Param("nowDate")Date nowDate);
//        List<SubscriptionRecord> getSubscriptionRecordsLessThanEndTimeAndGreaterThanStartTime(Date time);

        @Query("from SubscriptionRecord s where s.status=1 and s.user=:user and s.startTime>=:stime")
        List<SubscriptionRecord> getSubscriptionCurrentRecords(@Param("user")User user,@Param("stime")Date stime,Sort sort);

        @Query("from SubscriptionRecord s where s.status=1 and s.user=:user and s.endTime<:etime")
        List<SubscriptionRecord> getSubscriptionHistoryRecords(@Param("user")User user,@Param("etime")Date etime,Sort sort);

        @Query("select s from SubscriptionRecord as s where s.userId=:userId")
        List<SubscriptionRecord> getSubscriptionRecordsByUserId(
                @Param("userId") Long userId
        );

        @Cacheable(cacheNames = "subgetUserIdsByEndTime",key = "'subgetUserIdsByEndTime'+#p0")
        @Query("select count(a.userId) from SubscriptionRecord as a where a.userId=:userId and a.endTime >='2018-09-13'")
        Integer getUserIdsByEndTime(
                @Param("userId") Long userId
        );

        @Query("select distinct(a.userId) from SubscriptionRecord as a where a.endTime >= '2018-09-13'")
        List<Long> getUserIdsByUserId();

        @Query("select a from SubscriptionRecord as a where a.userId=:userId and a.endTime >='2018-09-13'")
        List<SubscriptionRecord> getSubscriptionRecordsByUserIdAndEndTime(
                @Param("userId") Long userId,
                Pageable pageable
        );

}