package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.social.dao.LessonViewRecordDao;
import com.ifenghui.storybookapi.app.social.entity.LessonVideoViewRecord;
import com.ifenghui.storybookapi.app.social.entity.LessonViewRecord;
import com.ifenghui.storybookapi.app.social.service.LessonViewRecordService;
import com.ifenghui.storybookapi.app.story.entity.LessonItem;
import com.ifenghui.storybookapi.app.story.entity.LessonItemRelate;
import com.ifenghui.storybookapi.app.story.service.LessonItemRelateService;
import com.ifenghui.storybookapi.app.story.service.LessonItemService;
import com.ifenghui.storybookapi.app.story.service.LessonService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class LessonViewRecordServiceImpl implements LessonViewRecordService {

    @Autowired
    LessonViewRecordDao lessonViewRecordDao;

    @Autowired
    LessonItemRelateService lessonItemRelateService;

    @Autowired
    LessonItemService lessonItemService;

    @Autowired
    LessonService lessonService;

    @Override
    public LessonViewRecord addLessonViewRecord(Integer lessonId, Integer userId, Integer itemId, Integer targetValue, Integer targetType, Integer lessonNum) {
        LessonViewRecord lessonViewRecord = new LessonViewRecord();
        lessonViewRecord.setCreateTime(new Date());
        lessonViewRecord.setItemId(itemId);
        lessonViewRecord.setLessonId(lessonId);
        lessonViewRecord.setTargetType(targetType);
        lessonViewRecord.setUserId(userId);
        lessonViewRecord.setTargetValue(targetValue);
        lessonViewRecord.setLessonNum(lessonNum);
        return lessonViewRecordDao.save(lessonViewRecord);
    }

    @Override
    public LessonViewRecord addLessonViewRecordByTargetValueAndIsVideo(Integer userId, Integer targetValue, boolean isVideo) {
        LessonItemRelate lessonItemRelate = lessonItemRelateService.getLessonItemRelateByStoryIdAndIsVideo(targetValue, isVideo);
        if(lessonItemRelate == null){
            throw new ApiNotFoundException("未找到这个课程章节！");
        }
        LessonItem lessonItem = lessonItemService.getLessonItemById(lessonItemRelate.getItemId());
        if(lessonItem == null){
            throw new ApiNotFoundException("未找到这个课程章节！");
        }
        return this.addLessonViewRecord(lessonItem.getLessonId(), userId, lessonItem.getId(), targetValue, lessonItemRelate.getType(), lessonItem.getLessonNum());
    }

    @Override
    public LessonViewRecord getLastLessonViewRecord(Integer lessonId, Integer userId) {
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"createTime"));
        Page<LessonViewRecord> lessonViewRecordPage = lessonViewRecordDao.getLessonViewRecordsByLessonIdAndUserId(lessonId, userId, pageable);
        if(lessonViewRecordPage.getContent().size() > 0){
            return lessonViewRecordPage.getContent().get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<LessonViewRecord> getLessonViewRecordByUserId(Integer userId) {
        LessonViewRecord enlightenLessonViewRecord = this.getLastLessonViewRecord(1, userId);
        LessonViewRecord growthLessonViewRecord = this.getLastLessonViewRecord(2, userId);
        if(enlightenLessonViewRecord == null && growthLessonViewRecord == null){
            return null;
        }
        List<LessonViewRecord> lessonViewRecordList = new ArrayList<>();

        if(enlightenLessonViewRecord != null){
            enlightenLessonViewRecord.setLesson(lessonService.getLessonAndHasUpdateNumById(enlightenLessonViewRecord.getLessonId()));
            lessonViewRecordList.add(enlightenLessonViewRecord);
        }

        if(growthLessonViewRecord != null){
            growthLessonViewRecord.setLesson(lessonService.getLessonAndHasUpdateNumById(growthLessonViewRecord.getLessonId()));
            if(enlightenLessonViewRecord != null){
                if(growthLessonViewRecord.getCreateTime().getTime() >= enlightenLessonViewRecord.getCreateTime().getTime()){
                    lessonViewRecordList.add(0, growthLessonViewRecord);
                } else {
                    lessonViewRecordList.add(1, growthLessonViewRecord);
                }
            } else {
                lessonViewRecordList.add(growthLessonViewRecord);
            }
        }
        return lessonViewRecordList;
    }
}
