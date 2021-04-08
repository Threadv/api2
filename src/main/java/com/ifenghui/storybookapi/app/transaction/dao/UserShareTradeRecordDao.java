package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.UserShareTradeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserShareTradeRecordDao extends JpaRepository<UserShareTradeRecord, Integer> {

    @Query("select sum(u.rewardAmount) from UserShareTradeRecord as u where u.userParentId=:userParentId")
    public Integer getSumOfRewardAmountFromUserShareTradeRecord(
            @Param("userParentId") Integer userParentId
    );

    @Query("select sum(u.orderAmount) from UserShareTradeRecord as u where u.userParentId=:userParentId")
    public Integer getSumOfOrderAmountFromUserShareTradeRecord(
            @Param("userParentId") Integer userParentId
    );

    @Query("select count(u) from UserShareTradeRecord as u where u.userParentId=:userParentId")
    public Integer getCountNumFromUserShareTradeRecord(
            @Param("userParentId") Integer userParentId
    );


}
