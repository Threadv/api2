package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.VCouponMix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;


/**
 * Created by wml on 2017/6/27.
 */
@Transactional
public interface VCouponMixDao extends JpaRepository<VCouponMix, Long> ,JpaSpecificationExecutor {

//    @Query("from CouponDeferredUser c where c.userId =:userId  and status=0 and c.endTime>=:nowTime")
//    Page<CouponDeferredUser> getUserValidityDeferredCoupons(@Param("userId") Long userId, @Param("nowTime") Date nowTime, Pageable pageable);//未过期未使用的
//
//    @Query("from CouponDeferredUser c where c.userId =:userId  and c.endTime>=:nowTime")
//    Page<CouponDeferredUser> getUserDeferredCoupons(@Param("userId") Long userId, @Param("nowTime") Date nowTime, Pageable pageable);//未过期的

    @Query("from VCouponMix c where c.userId =:userId  and c.endTime<:nowTime")
    Page<VCouponMix> getExpiredMixCoupons(@Param("userId") Long userId, @Param("nowTime") Date nowTime, Pageable pageable);//已过期的优惠券和赠阅券

//    @Query("select count(c) from CouponDeferredUser c where c.userId =:userId and c.status = 0 and c.endTime>=:nowTime")
//    Integer getUserValidityDeferredCouponsCount(@Param("userId") Long userId, @Param("nowTime") Date nowTime);//未过期的并可用的
//
//    CouponDeferredUser getByUserIdAndCouponId(Long userId, Long couponId);
}