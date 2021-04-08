package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.Story;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional
public interface StoryDao extends JpaRepository<Story, Long> ,JpaSpecificationExecutor {

    Page<Story> findAllStoryByCategoryId(Integer categoryId, Pageable pageable);

    @Query("from Story s where s.categoryId =:categoryId and s.isShow=:isShow and s.isDel=0 and s.isNow=0 and s.status=1 and s.scheduleType=0")
    Page<Story> findAllStoryByCategoryIdAndIsShow(@Param("categoryId") Integer categoryId,@Param("isShow")Integer isShow,Pageable pageable);

    @Query("from Story s where s.serialStoryId =:serialStoryId and s.status=1  and s.isDel=0 and s.scheduleType=0")
    Page<Story> getStorysBySerialStoryId(@Param("serialStoryId")Long serialStoryId,Pageable pageable);

    @Query("from Story s where s.serialStoryId =:serialStoryId and s.status=1 and s.isDel=0 and s.scheduleType=0 order by s.orderBy desc")
    List<Story> getStoryListBySerialStoryId(@Param("serialStoryId") Long serialStoryId);

    @Query("select s from Story s where s.secondSerialStoryId =:serialStoryId and s.status=1 and s.isDel=0 and s.scheduleType=0 order by s.orderBy desc")
    List<Story> getStoryListBySecondSerialStoryId(@Param("serialStoryId") Long serialStoryId);

    @Query("from Story s where s.magazineId =:magazineId  and s.isDel=0 and s.scheduleType=0")
    Page<Story> getStorysByMagazineId(@Param("magazineId")Long magazineId,Pageable pageable);

    @Cacheable(cacheNames = "storyship_story_v9",key = "'storyship_story_v9'+#p0")
    @Override
    Story findOne(Long id);


    @Query("from Story s where s.type != 2  and s.status=1 and s.isDel=0 and s.scheduleType=0 and s.serialStoryId=0 and  (s.name LIKE CONCAT('%',:content,'%') or s.pyName LIKE CONCAT('%',:content,'%')) ")
    Page<Story> getStorysBySearch(@Param("content") String content,Pageable pageable);

    @Query("from Story s where s.type != 2  and s.status=1 and s.isNow !=1 and s.isDel=0  and s.scheduleType=0 and s.serialStoryId=0 and (s.name LIKE CONCAT('%',:content,'%') or s.pyName LIKE CONCAT('%',:content,'%'))  ")
    Page<Story> getNotNowStorysBySearch(@Param("content") String content,Pageable pageable);

    @Query("from Story s where s.id !=:storyId and s.type =:type and s.isDel=0 and s.scheduleType=0")
    Page<Story> getMoreStorysByType(@Param("storyId") Long storyId,@Param("type") Integer type, Pageable pageable);

    @Query("from Story s where s.id !=:storyId and s.status =1 and s.isNow=1 and s.isDel=0 and s.scheduleType=0")
    Page<Story> getNowStorys(@Param("storyId") Long storyId, Pageable pageable);

    @Query("from Story s where  s.status =1 and s.isNow=:isNow and s.isDel=0 and s.scheduleType=0")
    Page<Story> getStorysByIsNow(@Param("isNow") Integer isNow, Pageable pageable);

    @Query("select count(s) from Story s where s.id !=:storyId and s.status =1 and s.type!=2 and s.isDel=0 and s.scheduleType=0")
    Integer getStorysCount(@Param("storyId") Long storyId);

    @Query("from Story s where   s.status =1 and s.isDel=0 and s.scheduleType=0 and s.id !=:storyId and s.type!=2  and s.type!=9")
    Page<Story> getOtherStorys(@Param("storyId") Long storyId, Pageable pageable);

    @Query("from Story s,Magazine mag " +
            "where s.magazineId=mag.id and s.status =1 and s.isDel=0 " +
            "and mag.publishTime>=:beginDate and mag.publishTime<=:endDate")
    Page<Story> getStorysByPublishDateRange(@Param("beginDate") java.sql.Date beginDate,@Param("endDate") java.sql.Date endDate, Pageable pageable);

    @Query(value = "select s.* from story_story s LEFT JOIN story_buy_story_record r ON " +
            "r.user_id=:userId AND r.story_id = s.id "+
            "where s.is_now=0 and s.status =1 and s.is_del=0 and s.is_free=0 and s.type!=2 and s.schedule_type=0 and r.story_id is null "+
            "ORDER BY RAND() LIMIT 1",nativeQuery = true)
    Story getStoryByRank(@Param("userId") Long userId);//随机一个故事（未购买，已发布，未删除，非当期,收费，非音频,非任务类型）

    @Query(value = "SELECT ss.* FROM story_story ss,story_group_relevance sgr WHERE ss.id = sgr.story_id AND sgr.group_id = 16 AND sgr.is_del = 0 ORDER BY sgr.order_by DESC",nativeQuery = true)
    List<Story> getRadioList();

//    @Query(value = "select s from Story s LEFT JOIN StoryPayRecord r ON " +
//            "r.userId=:userId AND r.storyId = s.id "+
//            "where s.isNow=0 and s.status =1 and s.isDel=0 and s.isFree=0 and s.type!=2 and r.storyId is null ",nativeQuery = true)
//    List<Story> getStoryByRank(@Param("userId") Long userId);//随机一个故事（未购买，已发布，未删除，非当期,收费，非音频）
}