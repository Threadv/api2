package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.entity.Lesson;

import java.util.List;

public interface LessonService {

    /**
     * 课程详情
     * @param lessonId
     * @return
     */
    Lesson getLessonById(Integer lessonId);

    Lesson getLessonAndHasUpdateNumById(Integer lessonId);

    /**
     * 精品课程列表
     * @return
     */
    List<Lesson> getLessonList();

    public List<Lesson> getLessonListAndBuyNum(Long userId);

}
