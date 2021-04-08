package com.ifenghui.storybookapi.app.social.service.impl;


import com.ifenghui.storybookapi.app.social.dao.UserReadRecordLogDao;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecordLog;
import com.ifenghui.storybookapi.app.social.service.UserReadRecordLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserReadRecordLogServiceImpl implements UserReadRecordLogService {

    @Autowired
    UserReadRecordLogDao userReadRecordLogDao;

    @Override
    public UserReadRecordLog addUserReadRecordLog(Integer storyId, Integer userId, Integer type) {
        UserReadRecordLog userReadRecordLog = new UserReadRecordLog();
        userReadRecordLog.setCreateTime(new Date());
        userReadRecordLog.setUserId(userId);
        userReadRecordLog.setStoryId(storyId);
        userReadRecordLog.setType(type);
        return userReadRecordLogDao.save(userReadRecordLog);
    }
}
