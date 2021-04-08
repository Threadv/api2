package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.app.app.entity.Feedback;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by jia on 2016/12/22.
 */

public interface FeedbackService {

    Feedback getFeedback(long id);

    Feedback addFeedback(Feedback feedback);

    void del(Long id);

    List<Feedback>  getFeedbacksByUserId(long userId);
    /**
     * 通过 userId  分页获取用户反馈
     */
    Page<Feedback>  getFeedbacksByUserIdAndPage(long userId,Integer pageNo,Integer pageSize);
    /**
     * 分页获取用户反馈
     */
    Page<Feedback> getFeedbacksByPage(Integer pageNo, Integer pageSize);

    Page<Feedback> findAll(Feedback feedback, Integer pageNo, Integer pageSize);

    Integer findCountBy(Long user,Integer readType);
}
