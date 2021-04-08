package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.entity.LessonViewRecord;

import java.util.List;

public interface LessonViewRecordService {

    LessonViewRecord addLessonViewRecord(Integer lessonId, Integer userId, Integer itemId, Integer targetValue, Integer targetType, Integer lessonNum);

    LessonViewRecord addLessonViewRecordByTargetValueAndIsVideo(Integer userId, Integer targetValue, boolean isVideo);

    LessonViewRecord getLastLessonViewRecord(Integer lessonId, Integer userId);

    List<LessonViewRecord> getLessonViewRecordByUserId(Integer userId);
}
