package com.ifenghui.storybookapi.active1902.service.impl;


import com.ifenghui.storybookapi.active1902.dao.AnswerDao;
import com.ifenghui.storybookapi.active1902.entity.Answer;
import com.ifenghui.storybookapi.active1902.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date: 2019/2/19 15:47
 * @Description:
 * @author :
 */
@Component
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerDao answerDao;


    @Override
    public List<Answer> getAnswersByQuestionId(Integer questionId) {

        return answerDao.findAnswersByQuestionId(questionId);
    }

    @Override
    public Answer findOne(Integer id) {
        return answerDao.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        answerDao.delete(id);
    }

    @Override
    public Answer save(Answer answer) {
        return answerDao.save(answer);
    }

    @Override
    public Answer update(Answer answer) {
        return answerDao.save(answer);
    }
}
