package com.ifenghui.storybookapi.app.wallet.dao;

import com.ifenghui.storybookapi.app.wallet.entity.UserStarRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserStarRecordDao extends JpaRepository<UserStarRecord, Long> {

    @Query("select sum(starRecord.amount) from UserStarRecord as starRecord where starRecord.userId=:userId and starRecord.type=:addType and starRecord.createTime>=:beginTime and starRecord.createTime<=:endTime")
    Integer getUserStarNumberSum(
            @Param("addType") Integer type,
            @Param("userId") Integer userId,
            @Param("beginTime") java.util.Date beginTime,
            @Param("endTime") java.util.Date endTime
    );

    @Cacheable(cacheNames = "UserStarRecord_count_v1_",key = "'UserStarRecord_count_v1_'+#p0+'_'+#p1+'_'+#p2")
    @Query(value = "select count(s) from UserStarRecord s where s.userId=:userId and s.type=:addType and s.buyType=:buyType")
    Long getUserStarCountByBuyType(
            @Param("addType") Integer type,
            @Param("userId") Integer userId,
            @Param("buyType") Integer buyType
    );

    @Query("select record from UserStarRecord as record where record.userId=:userId")
    Page<UserStarRecord> getUserStarRecordsByUserId(
            @Param("userId") Integer userId,
            Pageable pageable
    );

    @Query("select record from UserStarRecord as record where record.userId=:userId and record.buyType =:buyType and record.type=:addType")
    List<UserStarRecord> getUserStarRecordsByUserIdAndBuyTypeAndType(
            @Param("userId") Integer userId,
            @Param("buyType") Integer buyType,
            @Param("addType") Integer addType
    );

    @CacheEvict(cacheNames = "UserStarRecord_count_v1_",key = "'UserStarRecord_count_v1_'+#p0.userId+'_'+#p0.type+'_'+#p0.buyType")
    @Override
    UserStarRecord save(UserStarRecord userStarRecord);

}
