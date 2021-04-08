package com.ifenghui.storybookapi.app.transaction.dao.lesson;

import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PayLessonOrderDao extends JpaRepository<PayLessonOrder, Integer> {

    @Query("select sum(record.num) from PayLessonOrder as record where record.userId=:userId and record.status =:status and record.lessonId=:lessonId")
    Integer getPayLessonOrderLessonNumSum(
            @Param("userId") Integer userId,
            @Param("status") Integer status,
            @Param("lessonId") Integer lessonId
    );

    @Query("select record from PayLessonOrder as record where record.userId=:userId and record.priceId=6")
    List<PayLessonOrder> getPriceIdSixLessonOrder(
            @Param("userId") Integer userId
    );

    @Query("select record from PayLessonOrder as record where record.userId=:userId and record.priceId=6 and record.status=1")
    List<PayLessonOrder> getSuccessPriceIdSixLessonOrder(
            @Param("userId") Integer userId
    );

}
