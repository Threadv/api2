package com.ifenghui.storybookapi.app.wallet.dao;

import com.ifenghui.storybookapi.app.wallet.entity.UserAccountRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by jia on 2016/12/22.
 */

@Transactional
public interface UserAccountRecordDao extends JpaRepository<UserAccountRecord, Long> {
   Page<UserAccountRecord> getUserAccountRecordByUserId(Long orderId, Pageable pageable);

   UserAccountRecord findOneByOutTradeNo(String outTradeNo);

   @Query("select u from UserAccountRecord as u where u.userId=:userId and u.type=:addType and u.walletType=:walletType")
   Page<UserAccountRecord> getUserAccountRecordsByUserIdAndTypeAndWalletType(
           @Param("userId") Long userId,
           @Param("addType") Integer addType,
           @Param("walletType") Integer walletType,
           Pageable pageable
   );

}
