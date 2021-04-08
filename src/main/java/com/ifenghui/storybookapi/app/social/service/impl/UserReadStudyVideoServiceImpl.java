package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.social.dao.UserReadStudyVideoDao;
import com.ifenghui.storybookapi.app.social.entity.UserReadStudyVideo;
import com.ifenghui.storybookapi.app.social.service.UserReadStudyVideoService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.BuyLessonItemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserReadStudyVideoServiceImpl implements UserReadStudyVideoService {

    @Autowired
    UserReadStudyVideoDao userReadStudyVideoDao;

    @Autowired
    BuyLessonItemRecordService buyLessonItemRecordService;

    @Override
    public UserReadStudyVideo addUserReadStudyVideo(Integer itemId, Integer videoId, Integer userId) {
        UserReadStudyVideo userReadStudyVideo = new UserReadStudyVideo();
        userReadStudyVideo.setItemId(itemId);
        userReadStudyVideo.setVideoId(videoId);
        userReadStudyVideo.setUserId(userId);
        userReadStudyVideo.setCreateTime(new Date());
        return userReadStudyVideoDao.save(userReadStudyVideo);
    }

    @Override
    public boolean isNeedAddNewRecord(Integer videoId, Integer userId){
        Page<UserReadStudyVideo> userReadStudyVideoPage = this.getUserReadStudyVideoByUserIdAndVideoId(userId,videoId);
        if(userReadStudyVideoPage.getContent().size() > 0){
           UserReadStudyVideo userReadStudyVideo =  userReadStudyVideoPage.getContent().get(0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String recordString = simpleDateFormat.format(userReadStudyVideo.getCreateTime());
            String nowString = simpleDateFormat.format(new Date());
            if(recordString.equals(nowString)){
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void addUserReadStudyVideoRecord(Integer itemId, Integer videoId, Integer userId){
        if(this.isNeedAddNewRecord(videoId, userId)){
            this.addUserReadStudyVideo(itemId, videoId, userId);
        }
        buyLessonItemRecordService.setStoryOfLessonItemIsRead(videoId.longValue(), userId.longValue(), true);
    }

    @Override
    public Page<UserReadStudyVideo> getUserReadStudyVideoByUserIdAndVideoId(Integer userId, Integer videoId){
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"createTime","id"));
//        UserReadStudyVideo userReadStudyVideo = new UserReadStudyVideo();
//        userReadStudyVideo.setUserId(userId);
//        userReadStudyVideo.setVideoId(videoId);
//        return userReadStudyVideoDao.findAll(Example.of(userReadStudyVideo), pageable);
        return userReadStudyVideoDao.findAllByUserIdAndVideoId(userId,videoId, pageable);

    }
}
