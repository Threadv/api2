package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.transaction.dao.BuySerialStoryRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.serial.BuySerialStoryRecord;
import com.ifenghui.storybookapi.app.transaction.service.BuySerialStoryRecordService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.exception.ApiDuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class BuySerialStoryRecordServiceImpl implements BuySerialStoryRecordService {

    @Autowired
    BuySerialStoryRecordDao buySerialStoryRecordDao;

    @Override
    public void addBuySerialStoryRecord(User user, Integer serialStoryId) {
        List<BuySerialStoryRecord> buySerialStoryRecords = buySerialStoryRecordDao.getBuySerialStoryRecordsBySerialStoryIdAndUserId(serialStoryId, user.getId().intValue());
        if(buySerialStoryRecords.size() == 0){
            BuySerialStoryRecord buySerialStoryRecord = new BuySerialStoryRecord();
            buySerialStoryRecord.setIsTest(user.getIsTest());
            buySerialStoryRecord.setSerialStoryId(serialStoryId);
            buySerialStoryRecord.setUserId(user.getId().intValue());
            buySerialStoryRecord.setCreateTime(new Date());
            buySerialStoryRecordDao.save(buySerialStoryRecord);
        }
    }

    @Override
    public void checkIsCanAddBuySerialStoryRecrod(Integer userId, Integer serialStoryId){
        Long buySerialStoryRecordsCount=buySerialStoryRecordDao.countBuySerialStoryRecordByUserIdAndSerialStoryId(userId,serialStoryId);
//        List<BuySerialStoryRecord> buySerialStoryRecords = buySerialStoryRecordDao.getBuySerialStoryRecordsBySerialStoryIdAndUserId(serialStoryId, userId);
        if(buySerialStoryRecordsCount!=null&&buySerialStoryRecordsCount > 0){
            throw new ApiDuplicateException("此账号已拥有该故事集");
        }
    }
}

