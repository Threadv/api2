package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.user.entity.User;

public interface BuySerialStoryRecordService {

    /**
     * 添加故事集购买记录
     * @param user
     * @param serialStoryId
     */
    void addBuySerialStoryRecord(User user, Integer serialStoryId);

    /**
     * 检测是否可以添加故事集购买记录
     * @param userId
     * @param serialStoryId
     */
    void checkIsCanAddBuySerialStoryRecrod(Integer userId, Integer serialStoryId);
}
