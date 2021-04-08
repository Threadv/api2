package com.ifenghui.storybookapi.active1902.service;

import com.ifenghui.storybookapi.active1902.entity.UserAnswer;

/**
 * @Date: 2019/2/19 14:52
 * @Description:
 */
public interface UserAnswerService {


    /**
     * 添加用户回答记录
     * @param userId
     * @param scheduleId
     * @param questionId
     * @param answerId
     * @return
     */
    UserAnswer addRecord(Integer userId, Integer scheduleId, Integer questionId, Integer answerId);
}
