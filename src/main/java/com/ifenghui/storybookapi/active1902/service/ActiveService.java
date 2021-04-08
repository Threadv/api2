package com.ifenghui.storybookapi.active1902.service;

/**
 * @Date: 2019/2/19 17:15
 * @Description:
 */
public interface ActiveService {

    /***
     * 添加故事购买记录
     * @param userId
     * @param storyId
     */
    void addBuyStoryRecord(String token, Integer userId, Integer storyId, Integer scheduleId);
    /**
     * 添加故事集购买记录
     * @param userId
     * @param serialStoryId
     */
    void addBuySerialStoryRecord(String token, Integer userId, Integer serialStoryId, Integer scheduleId);
}
