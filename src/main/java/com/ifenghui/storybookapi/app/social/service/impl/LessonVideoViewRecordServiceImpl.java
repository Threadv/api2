package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.social.dao.LessonVideoViewRecordDao;
import com.ifenghui.storybookapi.app.social.entity.LessonVideoViewRecord;
import com.ifenghui.storybookapi.app.social.service.LessonVideoViewRecordService;
import com.ifenghui.storybookapi.app.social.service.LessonViewRecordService;
import com.ifenghui.storybookapi.app.story.entity.LessonItem;
import com.ifenghui.storybookapi.app.story.service.LessonItemRelateService;
import com.ifenghui.storybookapi.app.story.service.LessonItemService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;
import java.util.Date;

@Component
public class LessonVideoViewRecordServiceImpl implements LessonVideoViewRecordService {

    @Autowired
    LessonVideoViewRecordDao lessonVideoViewRecordDao;

    @Autowired
    LessonViewRecordService lessonViewRecordService;

    @Autowired
    LessonItemService lessonItemService;

    @Override
    public LessonVideoViewRecord addLessonVideoViewRecord(Integer itemId, Integer videoId, Integer userId) {
        LessonVideoViewRecord lessonVideoViewRecord = new LessonVideoViewRecord();
        lessonVideoViewRecord.setCreateTime(new Date());
        lessonVideoViewRecord.setItemId(itemId);
        lessonVideoViewRecord.setUserId(userId);
        lessonVideoViewRecord.setVideoId(videoId);
        return lessonVideoViewRecordDao.save(lessonVideoViewRecord);
    }

    @Override
    public LessonVideoViewRecord addLessonVideoViewRecordAndLessonViewRecord(Integer itemId, Integer videoId, Integer userId) {
        LessonVideoViewRecord lessonVideoViewRecord = lessonVideoViewRecordDao.getLessonVideoViewRecordByUserIdAndAndItemIdAndAndVideoId(itemId, userId, videoId);
        if(lessonVideoViewRecord != null){
            lessonVideoViewRecord.setCreateTime(new Date());
            lessonVideoViewRecordDao.save(lessonVideoViewRecord);
        } else {
           lessonVideoViewRecord = this.addLessonVideoViewRecord(itemId, videoId, userId);
        }

        lessonViewRecordService.addLessonViewRecordByTargetValueAndIsVideo(userId, videoId, true);
        return lessonVideoViewRecord;
    }
}
