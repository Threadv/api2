package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.story.dao.LessonStudyVideoDao;
import com.ifenghui.storybookapi.app.story.entity.LessonStudyVideo;
import com.ifenghui.storybookapi.app.story.service.LessonStudyVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LessonStudyVideoServiceImpl implements LessonStudyVideoService {

    @Autowired
    LessonStudyVideoDao lessonStudyVideoDao;

    @Override
    public LessonStudyVideo getLessonStudyVideoById(Integer studyVideoId) {
        return lessonStudyVideoDao.findOne(studyVideoId);
    }
}
