package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.LessonVideoViewRecord;
import com.ifenghui.storybookapi.app.social.entity.LessonViewRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonViewRecordDao extends JpaRepository<LessonViewRecord, Integer> {

    @Query("select record from LessonViewRecord as record where record.userId=:userId and record.lessonId=:lessonId")
    Page<LessonViewRecord> getLessonViewRecordsByLessonIdAndUserId(
            @Param("lessonId") Integer lessonId,
            @Param("userId") Integer userId,
            Pageable pageable
    );
}
