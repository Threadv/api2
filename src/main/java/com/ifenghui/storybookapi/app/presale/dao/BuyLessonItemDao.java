package com.ifenghui.storybookapi.app.presale.dao;


import com.ifenghui.storybookapi.app.transaction.entity.lesson.BuyLessonItemRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BuyLessonItemDao extends JpaRepository<BuyLessonItemRecord, Integer> {

    /**
     * 查询体验课阅读状态
     * @param userId
     * @param lessonItemId
     * @param isRead
     * @return
     */
    @Query("select record from BuyLessonItemRecord  as record where record.userId=:userId and record.lessonItemId =:lessonItemId and record.isRead =:isRead")
    BuyLessonItemRecord findReadStatus(@Param("userId") Integer userId, @Param("lessonItemId") Integer lessonItemId, @Param("isRead") Integer isRead);
}
