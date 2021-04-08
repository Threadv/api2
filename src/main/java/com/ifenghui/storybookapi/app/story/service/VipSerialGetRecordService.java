package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.entity.VipSerialGetRecord;

public interface VipSerialGetRecordService {

    Integer isGetVipSerialRecord(Integer userId, Integer serialStoryId);

    VipSerialGetRecord addVipSerialGetRecord(Integer userId, Integer serialStoryId);

    VipSerialGetRecord createVipSerialGetRecord(Integer userId, Integer serialStoryId);
}
