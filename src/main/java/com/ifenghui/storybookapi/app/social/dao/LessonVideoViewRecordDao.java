package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.LessonVideoViewRecord;
import com.ifenghui.storybookapi.app.social.entity.LessonViewRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonVideoViewRecordDao extends JpaRepository<LessonVideoViewRecord, Integer> {

    @Query("select a from LessonVideoViewRecord as a where a.itemId=:itemId and a.userId=:userId and a.videoId=:videoId")
    LessonVideoViewRecord getLessonVideoViewRecordByUserIdAndAndItemIdAndAndVideoId(
            @Param("itemId") Integer itemId,
            @Param("userId") Integer userId,
            @Param("videoId") Integer videoId
    );

}
