package com.ifenghui.storybookapi.app.transaction.service.lesson.impl;

import com.ifenghui.storybookapi.app.story.entity.LessonItem;
import com.ifenghui.storybookapi.app.story.service.LessonItemService;
import com.ifenghui.storybookapi.app.transaction.dao.lesson.LessonItemReadRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.LessonItemReadRecord;
import com.ifenghui.storybookapi.app.transaction.service.lesson.LessonItemReadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class LessonItemReadRecordServiceImpl implements LessonItemReadRecordService {


    @Autowired
    LessonItemService lessonItemService;

    @Autowired
    LessonItemReadRecordDao lessonItemReadRecordDao;

    /**
     * 判断课程是否已读
     *
     * @param userId
     * @param lessonItemId
     * @return
     */
    @Override
    public Integer getLessonItemReadRecordByUserIdAndLessonItemId(Integer userId, Integer lessonItemId) {

        Integer isRead = 0;
        LessonItemReadRecord lessonItemReadRecord = lessonItemReadRecordDao.findByUserIdAndLessonItemId(userId, lessonItemId);
        if (lessonItemReadRecord != null) {
            isRead = 1;
        }
        return isRead;
    }

    /**
     * 创建阅读记录
     *
     * @param userId
     * @param lessonItemId
     * @return
     */
    @Override
    public LessonItemReadRecord createLessonItemRecord(Integer userId, Integer lessonItemId) {

        //判断是否已存在记录 存在则返回
        LessonItemReadRecord itemReadRecord = lessonItemReadRecordDao.findByUserIdAndLessonItemId(userId, lessonItemId);
        if (itemReadRecord != null) {
            return itemReadRecord;
        }
        //获得lessonItem
        LessonItem lessonItem = lessonItemService.getLessonItemById(lessonItemId);

        LessonItemReadRecord lessonItemReadRecord = new LessonItemReadRecord();
        lessonItemReadRecord.setUserId(userId);
        lessonItemReadRecord.setLessonId(lessonItem.getLessonId());
        lessonItemReadRecord.setLessonItemId(lessonItemId);
        lessonItemReadRecord.setLessonNum(lessonItem.getLessonNum());
        lessonItemReadRecord.setCreateTime(new Date());
        return lessonItemReadRecordDao.save(lessonItemReadRecord);

    }
}
