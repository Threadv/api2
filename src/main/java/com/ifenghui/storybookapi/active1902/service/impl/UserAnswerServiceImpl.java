package com.ifenghui.storybookapi.active1902.service.impl;


import com.ifenghui.storybookapi.active1902.dao.UserAnswerDao;
import com.ifenghui.storybookapi.active1902.entity.UserAnswer;
import com.ifenghui.storybookapi.active1902.service.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Date: 2019/2/19 14:52
 * @Description:
 */
@Component
public class UserAnswerServiceImpl implements UserAnswerService {


    @Autowired
    UserAnswerDao userAnswerDao;
    @Override
    public UserAnswer addRecord(Integer userId, Integer scheduleId, Integer questionId, Integer answerId) {
        UserAnswer userAnswer = new UserAnswer();

        userAnswer.setUserId(userId);
        userAnswer.setScheduleId(scheduleId);
        userAnswer.setQuestionId(questionId);
        userAnswer.setAnswerId(answerId);
        userAnswer.setCreateTime(new Date());

        return userAnswerDao.save(userAnswer);
    }
}
