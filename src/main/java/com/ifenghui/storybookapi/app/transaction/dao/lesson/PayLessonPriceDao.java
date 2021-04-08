package com.ifenghui.storybookapi.app.transaction.dao.lesson;

import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PayLessonPriceDao extends JpaRepository<PayLessonPrice, Integer> {

    @Query("select item from PayLessonPrice as item where item.status=:status")
    Page<PayLessonPrice> getAllByStatus(
            @Param("status") Integer status,
            Pageable pageable
    );

}
