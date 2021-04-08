package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import com.ifenghui.storybookapi.app.social.entity.UserReadStudyVideo;
import com.ifenghui.storybookapi.app.social.service.UserReadRecordService;
import com.ifenghui.storybookapi.app.social.service.UserReadStudyVideoService;
import com.ifenghui.storybookapi.app.story.dao.LessonItemDao;
import com.ifenghui.storybookapi.app.story.dao.LessonItemRelateDao;
import com.ifenghui.storybookapi.app.story.entity.LessonItem;
import com.ifenghui.storybookapi.app.story.entity.LessonItemRelate;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.LessonItemRelateService;
import com.ifenghui.storybookapi.app.story.service.LessonStudyVideoService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.dao.lesson.BuyLessonItemRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.BuyLessonItemRecord;
import com.ifenghui.storybookapi.app.transaction.service.lesson.BuyLessonItemRecordService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonItemRelateServiceImpl implements LessonItemRelateService {

    @Autowired
    LessonItemRelateDao lessonItemRelateDao;

    @Autowired
    StoryService storyService;

    @Autowired
    BuyLessonItemRecordService buyLessonItemRecordService;

    @Autowired
    LessonItemDao lessonItemDao;

    @Autowired
    UserReadRecordService userReadRecordService;

    @Autowired
    UserReadStudyVideoService userReadStudyVideoService;

    @Autowired
    LessonStudyVideoService lessonStudyVideoService;

    @Autowired
    BuyLessonItemRecordDao buyLessonItemRecordDao;

    @Override
    public List<LessonItemRelate> getLessonItemRelateList(Integer itemId, Long userId) {
        LessonItem lessonItem = lessonItemDao.findOne(itemId);
        if(lessonItem == null){
            throw new ApiNotFoundException("没找到对应的课程！");
        }

        LessonItemRelate lessonItemRelate = new LessonItemRelate();
        lessonItemRelate.setItemId(itemId);
        lessonItemRelate.setStatus(1);
        List<LessonItemRelate> lessonItemRelateList = lessonItemRelateDao.findAll(Example.of(lessonItemRelate));

        if(lessonItem.getIsFree().equals(1)){
            this.checkAndAddBuyLessonItemRecord(userId.intValue(), lessonItem, lessonItemRelateList);
        }

        for(LessonItemRelate item : lessonItemRelateList){
            if(item.getType().equals(3)){
                Page<UserReadStudyVideo> userReadStudyVideoPage = userReadStudyVideoService.getUserReadStudyVideoByUserIdAndVideoId(userId.intValue(),item.getStoryId());
                if(userReadStudyVideoPage != null && userReadStudyVideoPage.getContent().size() > 0){
                    item.setIsRead(1);
                } else {
                    item.setIsRead(0);
                }
                item.setLessonStudyVideo(lessonStudyVideoService.getLessonStudyVideoById(item.getStoryId()));
            } else {
                Story story = storyService.getStoryDetailById(item.getStoryId().longValue(), userId);
                storyService.setStoryAppFile(story);

                Long readRecordCount=userReadRecordService.countReadRecordsByUserIdAndStoryId(userId,item.getStoryId().longValue());
//                Page<UserReadRecord> userReadRecordPage = userReadRecordService.getReadRecordsByUserIdAndStoryId(userId,item.getStoryId().longValue(), 0,1);
                if (readRecordCount != null && readRecordCount > 0){
                    item.setIsRead(1);
                } else {
                    item.setIsRead(0);
                }

                if(lessonItem.getIsFree().equals(1)){
                    story.setIsPurchased(1);
                } else {
                    story.setIsPurchased(buyLessonItemRecordService.isBuyLessonItem(userId.intValue(),itemId));
                }
                item.setStory(story);
            }

        }
        return lessonItemRelateList;
    }

    @Override
    public boolean checkIsBuy(int storyId, int userId) {
        Long countrelate=lessonItemRelateDao.countByStoryId(storyId);
        if(countrelate==null||countrelate==0){
            return false;
        }
        LessonItemRelate lessonItemRelate = lessonItemRelateDao.findOneByStoryId(storyId);
        if(lessonItemRelate==null){
            return false;
        }
        int itemId=lessonItemRelate.getItemId();
        LessonItem lessonItem=lessonItemDao.findOne(itemId);
        if(lessonItem==null){
            return false;
        }
        int lessonId=lessonItem.getLessonId();

        int flag=buyLessonItemRecordService.isBuyLessonItem(userId,lessonId);
        if(flag==1){
            return true;
        }
        return false;
    }



    @Override
    public List<LessonItemRelate> getLessonItemListByStoryId(LessonItemRelate lessonItemRelate){
        List<LessonItemRelate> lessonItemRelateList = null;
        if(lessonItemRelate != null){
            LessonItemRelate findRelate = new LessonItemRelate();
            findRelate.setItemId(lessonItemRelate.getItemId());
             lessonItemRelateList = lessonItemRelateDao.findAll(Example.of(findRelate));
        }
        return lessonItemRelateList;
    }

    @Override
    public void checkAndAddBuyLessonItemRecord(Integer userId, LessonItem lessonItem, List<LessonItemRelate> lessonItemRelateList){
        if(!userId.equals(0)){
            Integer isRead = buyLessonItemRecordService.getIsReadAllStoryByLessonItemRelateList(userId.longValue(), lessonItemRelateList);
            BuyLessonItemRecord buyLessonItemRecord = buyLessonItemRecordService.addBuyLessonItemRecord(userId, lessonItem.getLessonId(), lessonItem.getId(),0);
            buyLessonItemRecord.setIsRead(isRead);

            buyLessonItemRecordService.update(buyLessonItemRecord);
        }
    }

    @Override
    public LessonItemRelate getLessonItemRelateByStoryIdAndIsVideo(Integer storyId, boolean isVideo) {
        LessonItemRelate lessonItemRelate = null;
        if(isVideo){
            lessonItemRelate = lessonItemRelateDao.findOneByVideoId(storyId);
        } else {
            lessonItemRelate = lessonItemRelateDao.findOneByStoryId(storyId);
        }
        return lessonItemRelate;
    }
}
