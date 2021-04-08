package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;

public interface BuyStoryRecordService {

    /**
     * 恢复音频类型购买记录
     */
    void recoverBuyStoryRecordData();

    public BuyStoryRecord addBuyStoryRecord(Long userId, Long storyId, Integer type);


    public BuyStoryRecord createBuyStoryRecord(Long userId, Long storyId, Integer type);
}
