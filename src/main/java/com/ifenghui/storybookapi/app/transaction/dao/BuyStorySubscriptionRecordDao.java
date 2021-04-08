package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStorySubscriptionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuyStorySubscriptionRecordDao extends JpaRepository<BuyStorySubscriptionRecord, Long> {

    Page<BuyStorySubscriptionRecord> getBuyStorySubscriptionRecordsByUserId(Long userId, Pageable pageable);


    @Query("select a from BuyStorySubscriptionRecord a, Story b where a.userId=:userId and (b.categoryId=1 or b.categoryId=2 or b.categoryId=5 or b.categoryId=6) and a.storyId=b.id and b.storyId > 0")
    Page<BuyStorySubscriptionRecord> getMusicBuyStoryRecordsByUserId(
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query("select a from BuyStorySubscriptionRecord a,Story b " +
            "where a.userId=:userId and b.categoryId=:groupId " +
            "and a.storyId=b.id")
    Page<BuyStorySubscriptionRecord> getUserBuyRecordByCatId(
            @Param("userId") Long userId,
            @Param("groupId") Integer groupId,
            Pageable pageable
    );

}
