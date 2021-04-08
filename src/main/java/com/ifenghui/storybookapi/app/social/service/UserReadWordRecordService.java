package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.entity.UserReadWordRecord;

public interface UserReadWordRecordService {

    /**
     * 添加飞船故事课阅读识字数记录
     * @param userId
     * @param storyId
     * @param wordCount
     * @param vocabularyCount
     * @return
     */
    UserReadWordRecord addUserReadRecord(Integer userId, Integer storyId, Integer wordCount, Integer vocabularyCount);

}
