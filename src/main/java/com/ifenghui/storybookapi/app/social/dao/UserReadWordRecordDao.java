package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.UserReadWordRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserReadWordRecordDao extends JpaRepository<UserReadWordRecord, Integer> {



    @Query("select  u from  UserReadWordRecord  as u where u.storyId =:storyId and  u.userId =:userId")
    UserReadWordRecord getOneByStoryIdAndUserId(@Param("storyId") Integer storyId,@Param("userId")Integer userId);

    @Query("select sum(record.wordCount) from UserReadWordRecord as record where record.userId=:userId")
    Integer getUserReadWordRecordWordCount(
            @Param("userId") Integer userId
    );

    @Query("select sum(record.vocabularyCount) from UserReadWordRecord as record where record.userId=:userId")
    Integer getUserReadWordRecordVocabularyCount(
            @Param("userId") Integer userId
    );
//    @Cacheable(cacheNames ="getUserReadWordCountNumber",key = "'getUserReadWordCountNumber'+#p0+'_'+#p1+'_'+#p2")
//@Query("select sum(record.wordCount) from UserReadWordRecord as record where record.readTime>=:beginTime and record.readTime <=:endTime and record.userId=:userId")
//Integer getUserReadWordCountNumber(
//        @Param("userId") Integer userId,
//        @Param("beginTime") java.util.Date beginTime,
//        @Param("endTime") java.util.Date endTime
//);
@Cacheable(cacheNames ="getUserReadWordCountNumberAllDay",key = "'getUserReadWordCountNumberAllDay'+#p0")
    @Query("select sum(record.wordCount) from UserReadWordRecord as record where record.readTime>=:beginTime and record.readTime <=:endTime and record.userId=:userId")
    Integer getUserReadWordCountNumberAllDay(
            @Param("userId") Integer userId,
            @Param("beginTime") java.util.Date beginTime,
            @Param("endTime") java.util.Date endTime
    );
    @Cacheable(cacheNames ="getUserReadWordCountNumberCurrentDay",key = "'getUserReadWordCountNumberCurrentDay'+#p0+'_'+T(com.ifenghui.storybookapi.style.RangeTimeStyle).CURRENT_DAY.endDate")
    @Query("select sum(record.wordCount) from UserReadWordRecord as record where record.readTime>=:beginTime and record.readTime <=:endTime and record.userId=:userId")
    Integer getUserReadWordCountNumberCurrentDay(
            @Param("userId") Integer userId,
            @Param("beginTime") java.util.Date beginTime,
            @Param("endTime") java.util.Date endTime
    );

//    @Cacheable(cacheNames ="getUserReadVocabularyCountNumber",key = "'getUserReadVocabularyCountNumber'+#p0+'_'+#p1+'_'+#p2")
//    @Query("select sum(record.vocabularyCount) from UserReadWordRecord as record where record.readTime>=:beginTime and record.readTime <=:endTime and record.userId=:userId")
//    Integer getUserReadVocabularyCountNumber(
//            @Param("userId") Integer userId,
//            @Param("beginTime") java.util.Date beginTime,
//            @Param("endTime") java.util.Date endTime
//    );
@Cacheable(cacheNames ="getUserReadVocabularyCountNumberCurrentDay",key = "'getUserReadVocabularyCountNumberCurrentDay'+#p0+'_'+T(com.ifenghui.storybookapi.style.RangeTimeStyle).CURRENT_DAY.endDate")
    @Query("select sum(record.vocabularyCount) from UserReadWordRecord as record where record.readTime>=:beginTime and record.readTime <=:endTime and record.userId=:userId")
    Integer getUserReadVocabularyCountNumberCurrentDay(
            @Param("userId") Integer userId,
            @Param("beginTime") java.util.Date beginTime,
            @Param("endTime") java.util.Date endTime
    );
    @Cacheable(cacheNames ="getUserReadVocabularyCountNumberAllDay",key = "'getUserReadVocabularyCountNumberAllDay'+#p0")
    @Query("select sum(record.vocabularyCount) from UserReadWordRecord as record where record.readTime>=:beginTime and record.readTime <=:endTime and record.userId=:userId")
    Integer getUserReadVocabularyCountNumberAllDay(
            @Param("userId") Integer userId,
            @Param("beginTime") java.util.Date beginTime,
            @Param("endTime") java.util.Date endTime
    );

    @Caching(evict = {
//            @CacheEvict(cacheNames ="getUserReadWordCountNumber",key = "'getUserReadWordCountNumber'+#p0.userId+'_'+#p0.+'_'+#p2"),
            @CacheEvict(cacheNames ="getUserReadVocabularyCountNumberCurrentDay",key = "'getUserReadVocabularyCountNumberCurrentDay'+#p0.userId+'_'+T(com.ifenghui.storybookapi.style.RangeTimeStyle).CURRENT_DAY.endDate"),
            @CacheEvict(cacheNames ="getUserReadVocabularyCountNumberAllDay",key = "'getUserReadVocabularyCountNumberAllDay'+#p0.userId"),
            @CacheEvict(cacheNames ="getUserReadWordCountNumberAllDay",key = "'getUserReadWordCountNumberAllDay'+#p0"),
            @CacheEvict(cacheNames ="getUserReadWordCountNumberCurrentDay",key = "'getUserReadWordCountNumberCurrentDay'+#p0+'_'+T(com.ifenghui.storybookapi.style.RangeTimeStyle).CURRENT_DAY.endDate")
    })
    @Override
    UserReadWordRecord save(UserReadWordRecord userReadWordRecord);
}
