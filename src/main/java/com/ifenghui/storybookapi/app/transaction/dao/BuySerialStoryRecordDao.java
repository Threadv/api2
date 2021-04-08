package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.serial.BuySerialStoryRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BuySerialStoryRecordDao extends JpaRepository<BuySerialStoryRecord, Integer>,JpaSpecificationExecutor {

    /**
     * 查询购买记录里可以显示的合集
     * 42:西游记第一季
     * 60:西游记第二季
     * 2019-08-29修改，增加id白名单：1/2/42/46/47/48/49/50/60
     * @param userId
     * @param pageable
     * @return
     */
    @Query(value = "select a from BuySerialStoryRecord as a where a.userId=:userId " +
            "and (" +
            "a.serialStoryId=1 " +
            "or a.serialStoryId=2 " +
            "or a.serialStoryId=13 " +
            "or a.serialStoryId=14 " +
            "or a.serialStoryId =42 " +
            "or a.serialStoryId =46 " +
            "or a.serialStoryId =47 " +
            "or a.serialStoryId =48 " +
            "or a.serialStoryId =49 " +
            "or a.serialStoryId =50 " +
            "or a.serialStoryId =60)")
    Page<BuySerialStoryRecord> getBuySerialStoryRecordsByUserId(
        @Param("userId") Integer userId,
        Pageable pageable
    );

    @Cacheable(cacheNames ="countBuySerialStoryRecordByUserIdAndSerialStoryId",key = "'countBuySerialStoryRecordByUserIdAndSerialStoryId'+#p0+'_'+#p1")
    @Query("select count(record) from BuySerialStoryRecord as record where record.userId =:userId and record.serialStoryId =:serialStoryId")
    Long countBuySerialStoryRecordByUserIdAndSerialStoryId(
            @Param("userId") Integer userId,
            @Param("serialStoryId") Integer serialStoryId
    );

    @Query("select record from BuySerialStoryRecord as record where record.userId =:userId and record.serialStoryId =:serialStoryId")
    BuySerialStoryRecord getBuySerialStoryRecordByUserIdAndSerialStoryId(
            @Param("userId") Integer userId,
            @Param("serialStoryId") Integer serialStoryId
    );

    @Query("select item from BuySerialStoryRecord as item where item.serialStoryId=:serialStoryId and item.userId=:userId")
    List<BuySerialStoryRecord> getBuySerialStoryRecordsBySerialStoryIdAndUserId(
            @Param("serialStoryId") Integer serialStoryId,
            @Param("userId") Integer userId
    );


    @Caching(evict = @CacheEvict(cacheNames ="countBuySerialStoryRecordByUserIdAndSerialStoryId",key = "'countBuySerialStoryRecordByUserIdAndSerialStoryId'+#p0.userId+'_'+#p0.serialStoryId"))
    @Override
    BuySerialStoryRecord save(BuySerialStoryRecord buySerialStoryRecord);
}
