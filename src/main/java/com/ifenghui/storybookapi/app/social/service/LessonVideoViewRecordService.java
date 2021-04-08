package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.entity.LessonVideoViewRecord;

public interface LessonVideoViewRecordService {

    /**
     * 添加课程视频（亲子互动）浏览记录
     * @param itemId
     * @param videoId
     * @param userId
     * @return
     */
    public LessonVideoViewRecord addLessonVideoViewRecord(Integer itemId, Integer videoId, Integer userId);

    /**
     * 添加课程视频（亲子互动）浏览记录和课程浏览记录
     * @param videoId
     * @param userId
     * @return
     */
    public LessonVideoViewRecord addLessonVideoViewRecordAndLessonViewRecord(Integer itemId, Integer videoId, Integer userId);
}
