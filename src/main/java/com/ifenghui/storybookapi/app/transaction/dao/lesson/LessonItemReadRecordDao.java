package com.ifenghui.storybookapi.app.transaction.dao.lesson;

import com.ifenghui.storybookapi.app.transaction.entity.lesson.LessonItemReadRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonItemReadRecordDao extends JpaRepository<LessonItemReadRecord, Integer> {

    @Query("select item from LessonItemReadRecord  as item where  item.userId =:userId and item.lessonItemId =:lessonItemId")
    LessonItemReadRecord findByUserIdAndLessonItemId(@Param("userId") Integer userId, @Param("lessonItemId") Integer lessonItemId);
}
