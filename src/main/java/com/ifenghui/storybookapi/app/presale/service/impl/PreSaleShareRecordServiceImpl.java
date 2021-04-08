package com.ifenghui.storybookapi.app.presale.service.impl;


import com.ifenghui.storybookapi.app.presale.dao.PreSaleShareRecordDao;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleShareRecord;
import com.ifenghui.storybookapi.app.presale.service.PreSaleShareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Component
public class PreSaleShareRecordServiceImpl implements PreSaleShareRecordService {


    @Autowired
    PreSaleShareRecordDao shareRecordDao;


    @Override
    public List<PreSaleShareRecord> findRecordList(Integer userId, Integer activityId) {

        PreSaleShareRecord shareRecord = new PreSaleShareRecord();
        shareRecord.setUserId(userId);
        shareRecord.setActivityId(activityId);
        List<PreSaleShareRecord> shareRecordList = shareRecordDao.findAll(Example.of(shareRecord));
        return shareRecordList;
    }

    /**
     * 添加分享记录
     * @param userId
     * @param activityId
     * @return
     */
    @Override
    public PreSaleShareRecord addShareRecord(Integer userId, Integer activityId) {

        PreSaleShareRecord shareRecord = new PreSaleShareRecord();
        shareRecord.setUserId(userId);
        shareRecord.setActivityId(activityId);
        shareRecord.setCreateTime(new Date());
        PreSaleShareRecord preSaleShareRecord = shareRecordDao.save(shareRecord);

        return preSaleShareRecord;
    }
}
