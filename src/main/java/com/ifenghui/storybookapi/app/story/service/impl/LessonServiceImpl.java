package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.story.dao.LessonDao;
import com.ifenghui.storybookapi.app.story.entity.Lesson;
import com.ifenghui.storybookapi.app.story.service.LessonItemService;
import com.ifenghui.storybookapi.app.story.service.LessonService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.BuyLessonItemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonDao lessonDao;

    @Autowired
    BuyLessonItemRecordService buyLessonItemRecordService;

    @Autowired
    LessonItemService lessonItemService;

    @Override
    public Lesson getLessonById(Integer lessonId) {
        return lessonDao.findOne(lessonId);
    }
    @Override
    public Lesson getLessonAndHasUpdateNumById(Integer lessonId){
        Lesson lesson = this.getLessonById(lessonId);
        Integer hasUpdateNum = lessonItemService.getLessonHasUpdateNumByLessonId(lessonId);
        lesson.setHasUpdateNum(hasUpdateNum);
        return lesson;
    }

    @Override
    public List<Lesson> getLessonList() {
//        Lesson lesson = new Lesson();
//        lesson.setStatus(1);
//        List<Lesson> lessonList = lessonDao.findAll(Example.of(lesson));
        List<Lesson> lessonList = lessonDao.findAllByStatus(1);
        return lessonList;
    }

    @Override
    public List<Lesson> getLessonListAndBuyNum(Long userId){
        List<Lesson> lessonList = this.getLessonList();
        for(Lesson item : lessonList){
            item.setHasBuyNum(buyLessonItemRecordService.getBuyLessonItemCount(userId.intValue() , item.getId()));
        }
        return lessonList;
    }
}
