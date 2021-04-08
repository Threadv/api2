package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.story.entity.Story;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional
public interface BuyStoryRecordDao extends JpaRepository<BuyStoryRecord, Long> {

    @Query("select count(a) from BuyStoryRecord as a where a.userId=:userId and a.storyId=:storyId")
    Integer getCountRecrodsByUserIdAndStoryId(
            @Param("userId") Long userId,
            @Param("storyId") Long storyId
    );

//    List<BuyStoryRecord> getRecordsByUserIdAndStoryId(Long userId,Long storyId);

    @Cacheable(cacheNames = "countByUserIdAndStoryId",key = "'countByUserIdAndStoryId'+#p0+'_'+#p1")
    Long countByUserIdAndStoryId(Long userId,Long storyId);



    @CacheEvict(cacheNames = "countByUserIdAndStoryId",key = "'countByUserIdAndStoryId'+#p0.userId+'_'+#p0.storyId")
    @Override
    BuyStoryRecord save(BuyStoryRecord buyStoryRecord);
    /**
     * 修改为 hql 查询兼容老版本
     * @param userId
     * @param type
     * @param pageable
     * @return
     */
    @Query("select a from BuyStoryRecord a where a.userId=:userId and a.type=:buyType")
    Page<BuyStoryRecord> getBuyStoryRecordsByUserIdAndType(
            @Param("userId") Long userId,
            @Param("buyType") Integer type,
            Pageable pageable);

    @Query("select a from BuyStoryRecord a, Story b where a.userId=:userId and a.type=:buyType and (b.categoryId=1 or b.categoryId=2 or b.categoryId=5 or b.categoryId=6) and a.story.id=b.id and b.storyId > 0")
    Page<BuyStoryRecord> getMusicBuyStoryRecordsByUserIdAndType(
            @Param("userId") Long userId,
            @Param("buyType") Integer type,
            Pageable pageable);

    @Query("select a from BuyStoryRecord a, Story b where a.userId=:userId and (a.type=1 or a.type=2 or a.type=3 or a.type = 4 or a.type=6) and a.story.id=b.id")
    Page<BuyStoryRecord> getBuyStoryRecordsByUserIdAndPage(Long userId,Pageable pageable);

    @Query("select a from BuyStoryRecord a, Story b where a.userId=:userId and (a.type=1 or a.type=2 or a.type=3 or a.type = 4 or a.type=6) and (b.categoryId=1 or b.categoryId=2 or b.categoryId=5 or b.categoryId=6) and a.story.id=b.id")
    Page<BuyStoryRecord> getBuyStoryRecordsByUserId(Long userId,Pageable pageable);

    /**
     * 兼容老版本修改hql
     * @param userId
     * @param pageable
     * @return
     */
    @Query("select a from BuyStoryRecord a, Story b where a.userId=:userId and (a.type =1 or a.type = 3 or a.type = 4 or a.type = 6) and (b.categoryId=1 or b.categoryId=2 or b.categoryId=5 or b.categoryId=6) and a.story.id=b.id")
    Page<BuyStoryRecord> getBuyStoryRecords(@Param("userId") Long userId,Pageable pageable);

    List<BuyStoryRecord> getBuyStoryRecordsByStoryAndType(Story story, Integer type);

    List<BuyStoryRecord> getBuyStoryRecordsByUserIdAndType(Long userId, Integer type);

    List<BuyStoryRecord> getBuyStoryRecordsByUserIdAndStoryId(Long userId,Long storyId);

    @Query("select a from BuyStoryRecord a,Story b " +
            "where a.userId=:userId and a.type =:type and b.categoryId=:groupId " +
            "and a.story.id=b.id")
    Page<BuyStoryRecord> getUserBuyRecordByTypeAndCatId(@Param("userId") Long userId, @Param("type") Integer type,@Param("groupId") Integer groupId, Pageable pageable);

    @Query("select a from BuyStoryRecord a,Story b " +
            "where a.userId=:userId and (a.type =1 or a.type = 3 or a.type = 4 or a.type = 6) and b.categoryId=:groupId " +
            "and a.story.id=b.id")
    Page<BuyStoryRecord> getUserBuyStoryRecords(@Param("userId") Long userId,@Param("groupId") Integer groupId, Pageable pageable);

    @Query("select a from BuyStoryRecord a,Story b " +
            "where a.userId=:userId and (a.type =1 or a.type = 3 or a.type = 4 or a.type = 6) and b.categoryNewId=:groupId " +
            "and a.story.id=b.id")
    Page<BuyStoryRecord> getUserBuyStoryRecordsByNewCategory(@Param("userId") Long userId,@Param("groupId") Integer groupId, Pageable pageable);

    @Query("select a from BuyStoryRecord a,Story b " +
            "where a.userId=:userId and (a.type =1 or a.type = 3 or a.type = 4 or a.type = 6) and (b.categoryNewId=1 or b.categoryNewId=2 or b.categoryNewId=3 or b.categoryNewId=4)" +
            "and a.story.id=b.id")
    Page<BuyStoryRecord> getAllUserBuyStoryRecordsByNewCategory(@Param("userId") Long userId, Pageable pageable);


}