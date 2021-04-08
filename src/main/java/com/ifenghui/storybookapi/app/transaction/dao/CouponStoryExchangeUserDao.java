package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CouponStoryExchangeUserDao extends JpaRepository<CouponStoryExchangeUser,Integer> ,JpaSpecificationExecutor {

    /**
     * 获取已过期的故事优惠券
     * @param nowTime
     * @return
     */
    @Query("select c from CouponStoryExchangeUser c where c.status =0 and c.endTime<=:nowTime")
    List<CouponStoryExchangeUser> getUserUnValidStoryCouponList(@Param("nowTime") Date nowTime);

    @Query("select count(c) from CouponStoryExchangeUser c where c.status=0 and c.isExpire=0 and c.userId=:userId")
    Integer getUserHasStoryCouponNumber(
        @Param("userId") Integer userId
    );

    @Query("select c from CouponStoryExchangeUser as c where c.couponId=:couponId and c.userId=:userId")
    List<CouponStoryExchangeUser> getCouponStoryExchangeUsersByCouponIdAndUserId(
        @Param("couponId") Integer couponId,
        @Param("userId") Integer userId
    );
}
