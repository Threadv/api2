package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponOrderRelate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponOrderRelateDao extends JpaRepository<CouponOrderRelate, Integer> {

    @Query("select record from CouponOrderRelate as record where record.userId=:userId and record.orderId=:orderId and record.orderType=:orderType")
    List<CouponOrderRelate> getCouponOrderRelatesByUserIdAndAndOrderIdAndOrderType(
            @Param("userId") Integer userId,
            @Param("orderId") Integer orderId,
            @Param("orderType") Integer orderType
    );

}
