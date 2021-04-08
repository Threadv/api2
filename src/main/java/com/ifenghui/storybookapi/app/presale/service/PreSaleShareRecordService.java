package com.ifenghui.storybookapi.app.presale.service;


import com.ifenghui.storybookapi.app.presale.entity.PreSaleShareRecord;

import java.util.List;

public interface PreSaleShareRecordService {


    /**
     * 查分享记录
     * @param userId
     * @param activityId
     * @return
     */
    List<PreSaleShareRecord> findRecordList(Integer userId, Integer activityId);

    /**
     * 添加分享记录
     * @param userId
     * @param activityId
     * @return
     */
    PreSaleShareRecord addShareRecord(Integer userId, Integer activityId);

}
