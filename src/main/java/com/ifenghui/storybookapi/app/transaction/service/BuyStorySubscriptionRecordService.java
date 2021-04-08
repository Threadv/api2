package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.response.GetUserStoryListResponse;

import java.util.Date;

public interface BuyStorySubscriptionRecordService {

    /**
     * 用户已购买故事列表
     * @param userId
     * @param groupId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public GetUserStoryListResponse getUserStoryList(Long userId, Integer groupId, Integer pageNo, Integer pageSize);

    /**
     * 设置故事详情
     * @param story
     * @return
     */
    public Story setStoryDetail(Story story);
}
