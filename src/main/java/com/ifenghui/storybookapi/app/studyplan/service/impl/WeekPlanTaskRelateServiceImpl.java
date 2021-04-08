package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanTaskRelateDao;
import com.ifenghui.storybookapi.app.studyplan.entity.ParentsGuide;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskFinish;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskRelate;
import com.ifenghui.storybookapi.app.studyplan.response.WeekStudyReportResponse;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanTaskFinishService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanTaskRelateService;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeekPlanTaskRelateServiceImpl implements WeekPlanTaskRelateService {

    @Autowired
    WeekPlanTaskRelateDao weekPlanTaskRelateDao;

    @Autowired
    WeekPlanTaskFinishService weekPlanTaskFinishService;

    @Autowired
    StoryService storyService;

    @Autowired
    private Environment env;

    @Override
    public List<WeekPlanTaskRelate> getWeekPlanTaskRelateListByWeekPlanId(Integer weekPlanId, Integer userId) {
        List<WeekPlanTaskRelate> weekPlanTaskRelateList = weekPlanTaskRelateDao.getWeekPlanTaskRelatesByWeekPlanId(weekPlanId);
        if(weekPlanTaskRelateList != null && weekPlanTaskRelateList.size() > 0) {
            for(WeekPlanTaskRelate item : weekPlanTaskRelateList) {
                this.setDataToWeekPlanTaskRelate(item, userId);
            }
        }
        return weekPlanTaskRelateList;
    }

    private void setDataToWeekPlanTaskRelate(WeekPlanTaskRelate weekPlanTaskRelate, Integer userId) {
        Long hasRecord = weekPlanTaskFinishService.getCountByTaskIdAndWeekPlanId(weekPlanTaskRelate.getId(), weekPlanTaskRelate.getWeekPlanId(),userId);
        if(hasRecord > 0) {
            weekPlanTaskRelate.setIsFinish(1);
        } else {
            weekPlanTaskRelate.setIsFinish(0);
        }

        weekPlanTaskRelate.setParentsGuideUrl(env.getProperty("parents.guide.url") + weekPlanTaskRelate.getId());

    }

    @Override
    public void setDataToWeekStudyReportResponse(WeekStudyReportResponse response, Integer weekPlanId, Integer userId) {
        List<WeekPlanTaskRelate> weekPlanTaskRelateList = this.getWeekPlanTaskRelateListByWeekPlanId(weekPlanId, userId);
        Integer wordCount = 0;
        response.setIsFinishReview(0);
        for(WeekPlanTaskRelate item : weekPlanTaskRelateList) {
            if(item.getIsFinish().equals(1) && item.getTargetType().equals(LabelTargetStyle.Story) && !item.getDayNum().equals(0)) {
                Story story = storyService.getStory(item.getStoryId().longValue());
                wordCount += story.getReadWordCount();
            }
            if(item.getDayNum().equals(0) && item.getIsFinish().equals(1)) {
                response.setIsFinishReview(1);
            }
        }
        response.setReadWordCount(wordCount);
    }

    @Override
    public List<WeekPlanTaskRelate> getRecordByStoryIdAndTargetType(Integer storyId, LabelTargetStyle targetStyle) {
        return weekPlanTaskRelateDao.getWeekPlanTaskRelatesByStoryIdAndTargetType(storyId, targetStyle.getId());
    }

    @Override
    public WeekPlanTaskRelate findOne(Integer id) {
        return weekPlanTaskRelateDao.findOne(id);
    }

    @Override
    public Integer countByStoryId(Integer storyId) {
        return weekPlanTaskRelateDao.countByStoryId(storyId);
    }

    @Override
    public WeekPlanTaskRelate getWeekPlanTaskRelateById(Integer id) {
        return weekPlanTaskRelateDao.findById(id);
    }

//    @Override
//    public ParentsGuide getParentsGuideByWeekPlanIdAndDayNum(Integer weekPlanId, Integer dayNum) {
//        WeekPlanTaskRelate weekPlanTaskRelate = weekPlanTaskRelateDao.findByWeekPlanIdAndDayNum(weekPlanId,dayNum);
//
//        ParentsGuide parentsGuide = new ParentsGuide();
//        parentsGuide.setWeekPlanTaskRelateId(weekPlanTaskRelate.getId());
//        parentsGuide.setUrl(env.getProperty("parents.guide.url") + weekPlanTaskRelate.getId());
//        return parentsGuide;
//    }
}
