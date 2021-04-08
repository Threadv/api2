package com.ifenghui.storybookapi.app.transaction.dao.lesson;

import com.ifenghui.storybookapi.app.transaction.entity.lesson.CouponLessonOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponLessonOrderDao extends JpaRepository<CouponLessonOrder, Integer> {

    @Query("select record from CouponLessonOrder as record where record.userId=:userId and record.orderId=:orderId")
    List<CouponLessonOrder> getCouponLessonOrdersByUserIdAndAndOrderId(
            @Param("userId") Integer userId,
            @Param("orderId") Integer orderId
    );

}
