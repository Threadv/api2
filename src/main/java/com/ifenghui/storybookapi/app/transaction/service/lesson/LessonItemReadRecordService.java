package com.ifenghui.storybookapi.app.transaction.service.lesson;

import com.ifenghui.storybookapi.app.transaction.entity.lesson.LessonItemReadRecord;

public interface LessonItemReadRecordService {


    /**
     * 判断课程是否已读
     * @param userId
     * @param lessonItemId
     * @return
     */
    Integer getLessonItemReadRecordByUserIdAndLessonItemId(Integer userId,Integer lessonItemId);

    /**
     * 创建阅读记录
     * @param userId
     * @param lessonItemId
     * @return
     */
    LessonItemReadRecord createLessonItemRecord(Integer userId, Integer lessonItemId);
}
