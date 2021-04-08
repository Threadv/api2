package com.ifenghui.storybookapi.app.app.service.impl;

/**
 * Created by jia on 2016/12/22.
 */

import com.ifenghui.storybookapi.app.app.dao.FeedbackDao;
import com.ifenghui.storybookapi.app.app.entity.Feedback;

import com.ifenghui.storybookapi.app.app.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Component
public class FeedbackServiceImpl implements FeedbackService{
    @Autowired
    FeedbackDao feedbackDao;

    //    @Transactional
    @Override
    public Feedback getFeedback(long id) {
        return feedbackDao.findOne(id);
    }

    @Override
    public Feedback addFeedback(Feedback feedback){
        Feedback f=feedbackDao.save(feedback);
        return f;
    }

    @Override
    public void del(Long id) {
       Feedback feedback =  feedbackDao.findOne(id);
       feedback.setStatus(2);
       feedbackDao.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbacksByUserId(long userId) {
        List<Feedback> fs=feedbackDao.findAllByUserId(userId);
        return fs;
    }

    @Override
    public Page<Feedback> getFeedbacksByUserIdAndPage(long userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<Feedback>  feedbacks=feedbackDao.getFeedbacksByUserId(userId,pageable);
        return feedbacks;
    }
    @Override
    public Page<Feedback> getFeedbacksByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<Feedback> feedbacks=feedbackDao.findAll(pageable);
        return feedbacks;
    }

    @Override
    public Page<Feedback> findAll(Feedback feedback, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        return feedbackDao.findAll(Example.of(feedback),pageable);
    }

    @Override
    public Integer findCountBy(Long userId, Integer readType) {
        return feedbackDao.findCountBy(userId,readType);
    }


}
