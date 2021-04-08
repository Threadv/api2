package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanTaskFinishDao;
import com.ifenghui.storybookapi.app.studyplan.entity.*;
import com.ifenghui.storybookapi.app.studyplan.service.*;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.ReadRecordTypeStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class WeekPlanTaskFinishServiceImpl implements WeekPlanTaskFinishService {

    @Autowired
    WeekPlanTaskFinishDao weekPlanTaskFinishDao;

    @Autowired
    WeekPlanJoinService weekPlanJoinService;

    @Autowired
    WeekPlanTaskRelateService weekPlanTaskRelateService;

    @Autowired
    WeekPlanReadRecordService weekPlanReadRecordService;

    @Autowired
    WeekPlanUserRecordService weekPlanUserRecordService;

    @Autowired
    WeekPlanIntroService weekPlanIntroService;

    @Autowired
    StoryService storyService;

    @Override
    public WeekPlanTaskFinish addWeekPlanTaskFinish (Integer planTaskId, Integer weekPlanId, Integer userId, Integer score) {
        WeekPlanTaskFinish weekPlanTaskFinish = new WeekPlanTaskFinish();
        weekPlanTaskFinish.setCreateTime(new Date());
        weekPlanTaskFinish.setWeekPlanId(weekPlanId);
        weekPlanTaskFinish.setPlanTaskId(planTaskId);
        weekPlanTaskFinish.setUserId(userId);
        weekPlanTaskFinish.setScore(score);
        return weekPlanTaskFinishDao.save(weekPlanTaskFinish);
    }

    @Override
    public WeekPlanTaskFinish getWeekPlanTaskFinishByTaskIdAndWeekPlanId(Integer planTaskId, Integer weekPlanId, Integer userId) {
        return weekPlanTaskFinishDao.getDistinctByUserIdAndWeekPlanIdAndPlanTaskId(userId, weekPlanId, planTaskId);
    }

    @Override
    public Long getCountByTaskIdAndWeekPlanId(Integer planTaskId, Integer weekPlanId, Integer userId) {
        Long count = weekPlanTaskFinishDao.getCountByUserIdAndWeekPlanIdAndPlanTaskId(userId, weekPlanId, planTaskId);
        if(count == null) {
            return 0L;
        }
        return count;
    }

    @Override
    public Integer getScoreCountByWeekPlanIdAndUserId(Integer weekPlanId, Integer userId) {
        Integer count = weekPlanTaskFinishDao.getScoreCountByWeekPlanIdAndUserId(weekPlanId, userId);
        if(count == null) {
            return 0;
        } else {
            return count;
        }
    }

    @Override
    public void createWeekPlanTaskFinish(Integer planTaskId, Integer weekPlanId, Integer userId, Integer score) {

        Integer hasJoinRecord = weekPlanUserRecordService.getCountByUserIdAndWeekPlanId(userId, weekPlanId);

        if(hasJoinRecord == 0) {
            return ;
        }

        Long hasRecord = this.getCountByTaskIdAndWeekPlanId(planTaskId, weekPlanId, userId);
        if(hasRecord > 0) {
            return ;
        }
        WeekPlanTaskRelate weekPlanTaskRelate = weekPlanTaskRelateService.findOne(planTaskId);
        if(weekPlanTaskRelate == null) {
            return ;
        }
        WeekPlanIntro weekPlanIntro = weekPlanIntroService.findOne(weekPlanTaskRelate.getWeekPlanId());
        Integer wordCount = 0;
        ReadRecordTypeStyle readRecordTypeStyle = ReadRecordTypeStyle.OTHER;
        Integer isStory = 0;
        if(weekPlanTaskRelate.getTargetType().equals(LabelTargetStyle.Story)) {
            Story story = storyService.getStory(weekPlanTaskRelate.getStoryId());
            if(story == null) {
                return ;
            }
            if(story.getType().equals(1) || story.getType().equals(4)) {
                isStory = 1;
            }
            wordCount = story.getReadWordCount();
            readRecordTypeStyle = ReadRecordTypeStyle.STORY;
        }

        this.addWeekPlanTaskFinish(planTaskId, weekPlanId, userId, score);
        List<WeekPlanTaskRelate> weekPlanTaskRelateList = weekPlanTaskRelateService.getWeekPlanTaskRelateListByWeekPlanId(weekPlanId, userId);
        this.checkIsNeedChangeWeekPlanFinishStatus(weekPlanTaskRelateList, userId);
        weekPlanReadRecordService.createWeekPlanReadRecord(weekPlanTaskRelate.getStoryId(), userId, wordCount, weekPlanIntro.getWeekPlanStyle(), weekPlanTaskRelate.getTargetType(), readRecordTypeStyle, isStory);

    }

    @Override
    public void checkIsNeedChangeWeekPlanFinishStatus(List<WeekPlanTaskRelate> weekPlanTaskRelateList, Integer userId) {
        Integer finishNum = 0;
        if(weekPlanTaskRelateList == null || weekPlanTaskRelateList.size() == 0) {
            return ;
        }
        Integer weekPlanId = weekPlanTaskRelateList.get(0).getWeekPlanId();
        for(WeekPlanTaskRelate item : weekPlanTaskRelateList) {
            if(item.getIsFinish().equals(1)){
                finishNum += 1;
            }
        }
        weekPlanUserRecordService.setWeekPlanUserRecordFinishNum(weekPlanId, userId,finishNum);
    }

    /**
     * 添加完成记录视频和故事入口
     */
    @Override
    public void setWeekPlanTaskFinishEnter(Integer targetValue, LabelTargetStyle labelTargetStyle, Integer userId) {
        List<WeekPlanTaskRelate> weekPlanTaskRelateList = weekPlanTaskRelateService.getRecordByStoryIdAndTargetType(targetValue, labelTargetStyle);
        if(weekPlanTaskRelateList != null && weekPlanTaskRelateList.size() > 0 ) {
            for(WeekPlanTaskRelate item : weekPlanTaskRelateList) {
                this.createWeekPlanTaskFinish(item.getId(), item.getWeekPlanId(), userId, item.getScore());
            }
        }
        /**
         * 如果什么都没填上 至少填一个全部的
         */
        WeekPlanReadRecord weekPlanReadRecordAll = weekPlanReadRecordService.findOneByTargetValueAndUserIdAndPlanTypeAndTargetType(targetValue, userId, WeekPlanStyle.ALL_PLAN, labelTargetStyle);
        if(weekPlanReadRecordAll == null) {
            Integer wordCount = 0;
            Integer isStory = 0;
            ReadRecordTypeStyle recordTypeStyle = ReadRecordTypeStyle.OTHER;
            if(labelTargetStyle.equals(LabelTargetStyle.Story)) {
                Story story = storyService.getStory(targetValue);
                if(story == null) {
                    return ;
                }
                if(story.getType().equals(1) || story.getType().equals(4)) {
                    isStory = 1;
                }
                wordCount = story.getReadWordCount();
                recordTypeStyle = ReadRecordTypeStyle.STORY;
            }
            weekPlanReadRecordService.addWeekPlanReadRecord(targetValue, userId, wordCount, WeekPlanStyle.ALL_PLAN, labelTargetStyle, recordTypeStyle, isStory);
        }
    }

}
