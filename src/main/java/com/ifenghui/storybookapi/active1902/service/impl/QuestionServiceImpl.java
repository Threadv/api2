package com.ifenghui.storybookapi.active1902.service.impl;



import com.ifenghui.storybookapi.active1902.dao.AnswerDao;
import com.ifenghui.storybookapi.active1902.dao.QuestionDao;
import com.ifenghui.storybookapi.active1902.entity.Answer;
import com.ifenghui.storybookapi.active1902.entity.Question;
import com.ifenghui.storybookapi.active1902.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date: 2019/2/20 13:36
 * @Description:
 */

@Component
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    AnswerDao answerDao;
    @Autowired
    QuestionDao questionDao;

    @Override
    public Question findQuestionsByScheduleIdAndQuestionId(Integer scheduleId, Integer questionId) {

        Question question = questionDao.findQuestionsByScheduleIdAndQuestionId(scheduleId, questionId);
        if(question != null){
            List<Answer> answerList = answerDao.findAnswersByQuestionId(question.getId());
            question.setAnswerList(answerList);
        }
        return question;
    }

    @Override
    public List<Question> findQuestionsByScheduleId(Integer scheduleId) {

        List<Question> questionList = questionDao.findQuestionsByScheduleId(scheduleId);
        for (Question q: questionList) {
            List<Answer> answerList = answerDao.findAnswersByQuestionId(q.getId());
            q.setAnswerList(answerList);
        }

        return questionList;
    }

    @Override
    public Question findOne(Integer id) {
        return questionDao.findOne(id);
    }

    @Override
    public Question save(Question question) {
        return questionDao.save(question);
    }

    @Override
    public Question update(Question question) {
        return questionDao.save(question);
    }

    @Override
    public void delete(Integer id) {
        questionDao.delete(id);
    }
}
