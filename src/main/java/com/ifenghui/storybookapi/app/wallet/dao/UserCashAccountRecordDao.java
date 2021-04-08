package com.ifenghui.storybookapi.app.wallet.dao;

import com.ifenghui.storybookapi.app.wallet.entity.UserCashAccountRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCashAccountRecordDao extends JpaRepository<UserCashAccountRecord, Integer> {

    UserCashAccountRecord findUserCashAccountRecordByOutTradeNo(String outTradeNo);

    @Query("select u from UserCashAccountRecord as u where u.userId=:userId and u.type=:addType")
    Page<UserCashAccountRecord> getUserCashAccountRecordsByUserIdAndType(
            @Param("userId") Long userId,
            @Param("addType") Integer addType,
            Pageable pageable
    );

    @Query("select u from UserCashAccountRecord as u where u.userId=:userId")
    Page<UserCashAccountRecord> getUserCashAccountRecordsByUserId(
            @Param("userId") Long userId,
            Pageable pageable
    );


}
