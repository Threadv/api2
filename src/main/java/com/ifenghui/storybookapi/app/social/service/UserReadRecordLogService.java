package com.ifenghui.storybookapi.app.social.service;


import com.ifenghui.storybookapi.app.social.entity.UserReadRecordLog;

public interface UserReadRecordLogService {

    UserReadRecordLog addUserReadRecordLog(Integer storyId, Integer userId, Integer type);

}
