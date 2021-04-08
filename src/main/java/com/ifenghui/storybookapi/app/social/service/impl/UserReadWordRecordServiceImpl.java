package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.social.dao.UserReadWordRecordDao;
import com.ifenghui.storybookapi.app.social.entity.UserReadWordRecord;
import com.ifenghui.storybookapi.app.social.service.UserReadWordRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserReadWordRecordServiceImpl implements UserReadWordRecordService {

    @Autowired
    UserReadWordRecordDao userReadWordRecordDao;

    @Override
    public UserReadWordRecord addUserReadRecord(Integer userId, Integer storyId, Integer wordCount, Integer vocabularyCount) {
        UserReadWordRecord userReadWordRecord = new UserReadWordRecord();
        userReadWordRecord = userReadWordRecordDao.getOneByStoryIdAndUserId(storyId,userId);
        if(userReadWordRecord == null){
            userReadWordRecord = new UserReadWordRecord();
            userReadWordRecord.setUserId(userId);
            userReadWordRecord.setStoryId(storyId);
            userReadWordRecord.setWordCount(wordCount);
            userReadWordRecord.setVocabularyCount(vocabularyCount);
            userReadWordRecord.setCreateTime(new Date());
        }
        userReadWordRecord.setReadTime(new Date());
        userReadWordRecord = userReadWordRecordDao.save(userReadWordRecord);
        return userReadWordRecord;
    }
}
