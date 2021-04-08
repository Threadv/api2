package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.transaction.dao.BuyStoryRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.transaction.service.BuyStoryRecordService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BuyStoryRecordServiceImpl implements BuyStoryRecordService {

    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;

    @Autowired
    UserService userService;

    @Override
    public void recoverBuyStoryRecordData() {
        List<BuyStoryRecord> buyStoryRecordList = buyStoryRecordDao.findAll();
        for(BuyStoryRecord item : buyStoryRecordList){
            if(!item.getStory().getStoryId().equals(0) && (item.getStory().getType().equals(1) || item.getStory().getType().equals(4))){
                BuyStoryRecord buyStoryRecord = new BuyStoryRecord();
                buyStoryRecord.setUserId(item.getUserId());
                buyStoryRecord.setCreateTime(item.getCreateTime());
                Integer storyId = item.getStory().getStoryId();
                buyStoryRecord.setStoryId(storyId.longValue());
                buyStoryRecord.setType(item.getType());
                buyStoryRecord.setIsTest(item.getIsTest());
                buyStoryRecordDao.save(buyStoryRecord);
            }
        }
    }
    @Override
    public BuyStoryRecord addBuyStoryRecord(Long userId, Long storyId, Integer type){
        User user = userService.getUser(userId);
        BuyStoryRecord buyStoryRecord = new BuyStoryRecord();
        buyStoryRecord.setIsTest(user.getIsTest());
        buyStoryRecord.setStoryId(storyId);
        buyStoryRecord.setType(type);
        buyStoryRecord.setUserId(userId);
        buyStoryRecord.setCreateTime(new Date());
        buyStoryRecord = buyStoryRecordDao.save(buyStoryRecord);
        return buyStoryRecord;
    }

    @Override
    public BuyStoryRecord createBuyStoryRecord(Long userId, Long storyId, Integer type) {
        Integer countRecord = buyStoryRecordDao.getCountRecrodsByUserIdAndStoryId(userId, storyId);
        if(countRecord == null || countRecord == 0){
            return this.addBuyStoryRecord(userId, storyId, type);
        } else {
            return null;
        }
    }
}
