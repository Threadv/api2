package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional
public interface CouponUserDao extends JpaRepository<CouponUser, Long> ,JpaSpecificationExecutor {

    @Query("from CouponUser c where c.userId =:userId  and c.endTime<:nowTime")
    Page<CouponUser> getExpiredUserCoupons(@Param("userId") Long userId, @Param("nowTime") Date nowTime, Pageable pageable);//已过期的
    @Query("from CouponUser c where c.userId =:userId  and c.endTime>=:nowTime")
    Page<CouponUser> getUserCoupons(@Param("userId") Long userId, @Param("nowTime") Date nowTime, Pageable pageable);//未过期的

    @Query("from CouponUser c where c.userId =:userId  and c.isView=0")
    Page<CouponUser> getUserUnReadCoupons(@Param("userId") Long userId, Pageable pageable);//未读

    @Query("from CouponUser c where c.userId =:userId  and status=0 and c.endTime>=:nowTime")
    Page<CouponUser> getUserValidityCoupons(@Param("userId") Long userId, @Param("nowTime") Date nowTime, Pageable pageable);//未过期的


    @Query("select count(c) from CouponUser c where c.userId =:userId and c.status = 0 and c.endTime>=:nowTime")
    Integer getUserValidityCouponsCount(@Param("userId") Long userId, @Param("nowTime") Date nowTime);//未过期的并可用的

    @Query("select count(c) from CouponUser c where c.userId =:userId  and c.endTime>=:nowTime")
    Integer getUserCouponsCount(@Param("userId") Long userId, @Param("nowTime") Date nowTime);//未过期的

    @Query("from CouponUser c where c.userId =:userId  and status=0 and c.endTime>=:nowTime")
    List<CouponUser> getUserValidityCoupons(@Param("userId") Long userId, @Param("nowTime") Date nowTime);//未过期的


    CouponUser getByUserIdAndCouponId(Long userId,Long couponId);

    @Query("select c from CouponUser as c where c.userId=:userId and c.couponId=:couponId")
    List<CouponUser> getCouponUsersByUserIdAndCouponId(
        @Param("userId") Long userId,
        @Param("couponId") Long couponId
    );
}