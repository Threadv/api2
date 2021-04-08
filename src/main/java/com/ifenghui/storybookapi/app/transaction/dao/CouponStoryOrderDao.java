package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.CouponLessonOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponStoryOrderDao extends JpaRepository<CouponStoryOrder, Integer> {

    @Query("select record from CouponStoryOrder as record where record.userId=:userId and record.orderId=:orderId")
    List<CouponStoryOrder> getCouponStoryOrdersByUserIdAndAndOrderId(
            @Param("userId") Integer userId,
            @Param("orderId") Integer orderId
    );

    List<CouponStoryOrder> getCouponStoryOrdersByOrderId(Integer orderId);

}
