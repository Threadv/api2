package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.story.dao.VipSerialGetRecordDao;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.VipSerialGetRecord;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.VipSerialGetRecordService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiDuplicateException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class VipSerialGetRecordServiceImpl implements VipSerialGetRecordService {

    @Autowired
    VipSerialGetRecordDao vipSerialGetRecordDao;

    @Autowired
    UserService userService;

    @Autowired
    SerialStoryService serialStoryService;

    @Override
    public Integer isGetVipSerialRecord(Integer userId, Integer serialStoryId) {
        VipSerialGetRecord vipSerialGetRecord = vipSerialGetRecordDao.getDistinctByUserIdAndAndSerialStoryId(userId, serialStoryId);
        if(vipSerialGetRecord != null){
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public VipSerialGetRecord addVipSerialGetRecord(Integer userId, Integer serialStoryId) {
        if(this.isGetVipSerialRecord(userId, serialStoryId).equals(0)){
            VipSerialGetRecord vipSerialGetRecord = new VipSerialGetRecord();
            vipSerialGetRecord.setCreateTime(new Date());
            vipSerialGetRecord.setSerialStoryId(serialStoryId);
            vipSerialGetRecord.setUserId(userId);
            return vipSerialGetRecordDao.save(vipSerialGetRecord);
        } else {
            throw new ApiDuplicateException("已领取过这个系列！");
        }
    }

    @Override
    public VipSerialGetRecord createVipSerialGetRecord(Integer userId, Integer serialStoryId) {
        SerialStory serialStory = serialStoryService.getSerialStory(serialStoryId.longValue());
        if(serialStory == null){
            throw new ApiNotFoundException("未找到该系列！");
        }
        User user = userService.getUser(userId.longValue());
        if (user == null) {
            throw new ApiNotFoundException("未找到该用户！");
        }
        if (user.getSvip() < 2){
            throw new ApiNotFoundException("您没有领取权限！");
        }
        return this.addVipSerialGetRecord(userId, serialStoryId);
    }
}
