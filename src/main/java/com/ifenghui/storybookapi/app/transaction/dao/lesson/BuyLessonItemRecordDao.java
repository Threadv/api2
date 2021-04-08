package com.ifenghui.storybookapi.app.transaction.dao.lesson;

import com.ifenghui.storybookapi.app.transaction.entity.lesson.BuyLessonItemRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BuyLessonItemRecordDao extends JpaRepository<BuyLessonItemRecord, Integer> {

    @Cacheable(cacheNames = "countBuyLessonItemRecordsByUserIdAndLessonItemId_v1",key = "'countBuyLessonItemRecordsByUserIdAndLessonItemId_v1_'+#p0+'_'+#p1")
    Long countBuyLessonItemRecordsByUserIdAndLessonItemId(@Param("userId") Integer userId, @Param("lessonItemId") Integer lessonItemId);

    @Cacheable(cacheNames = "getBuyLessonItemRecordsByUserIdAndLessonItemId_v1",key = "'getBuyLessonItemRecordsByUserIdAndLessonItemId_v1_'+#p0+'_'+#p1")
    @Query("select lesson from BuyLessonItemRecord lesson where lesson.userId = :userId and lesson.lessonItemId = :lessonItemId")
    BuyLessonItemRecord getBuyLessonItemRecordsByUserIdAndLessonItemId(@Param("userId") Integer userId, @Param("lessonItemId") Integer lessonItemId);



    @Query(value = "select count(record) from BuyLessonItemRecord as record where record.userId=:userId and record.lessonId=:lessonId and record.isFree=0")
    Integer getBuyLessonItemRecordCountByLessonIdAndUserId(
            @Param("lessonId") Integer lessonId,
            @Param("userId") Integer userId
    );

    @Query(value = "select max(record.lessonNum) from BuyLessonItemRecord as record where record.userId=:userId and record.lessonId=:lessonId and record.isFree=0")
    Integer getMaxLessonNumByLessonIdAndUserId(
            @Param("lessonId") Integer lessonId,
            @Param("userId") Integer userId
    );

    @Query(value = "select record from BuyLessonItemRecord as record where record.userId=:userId and record.lessonId=:lessonId and record.isRead=:isRead and record.isFree=0")
    Page<BuyLessonItemRecord> getBuyLessonItemRecordsByLessonIdAndUserId(
            @Param("lessonId") Integer lessonId,
            @Param("userId") Integer userId,
            @Param("isRead") Integer isRead,
            Pageable pageable
    );

    @Query("select count(record) from BuyLessonItemRecord as record where record.userId=:userId and record.lessonId=:lessonId and record.isRead=:isRead and record.isFree=0")
    Integer getHasReadLessonCount(
            @Param("lessonId") Integer lessonId,
            @Param("userId") Integer userId,
            @Param("isRead") Integer isRead
    );

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "getBuyLessonItemRecordsByUserIdAndLessonItemId_v1",key = "'getBuyLessonItemRecordsByUserIdAndLessonItemId_v1_'+#p0.userId+'_'+#p0.lessonItemId"),
            @CacheEvict(cacheNames = "countBuyLessonItemRecordsByUserIdAndLessonItemId_v1",key = "'countBuyLessonItemRecordsByUserIdAndLessonItemId_v1_'+#p0.userId+'_'+#p0.lessonItemId")

    })

    BuyLessonItemRecord save(BuyLessonItemRecord buyLessonItemRecord);
}
