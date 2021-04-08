package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponGetRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional
public interface CouponGetRecordDao extends JpaRepository<CouponGetRecord, Long> ,JpaSpecificationExecutor {

    CouponGetRecord getByUserIdAndPhone(Long userId,String phone);
    CouponGetRecord getByPhone(String phone);

    @Query("select record from CouponGetRecord as record where record.userId=:userId")
    Page<CouponGetRecord> getCouponGetRecordsByUserId(@Param("userId") Long userId, Pageable pageable);
}