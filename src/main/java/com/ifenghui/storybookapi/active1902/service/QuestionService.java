package com.ifenghui.storybookapi.active1902.service;

import com.ifenghui.storybookapi.active1902.entity.Question;

import java.util.List;

/**
 * @Date: 2019/2/20 13:36
 * @Description:
 */
public interface QuestionService {


    /**
     * 单个问题
     * @param scheduleId
     * @param questionId
     * @return
     */
    Question findQuestionsByScheduleIdAndQuestionId(Integer scheduleId, Integer questionId);
    /**
     * 当前排期问题列表
     * @param scheduleId
     * @return
     */
    List<Question> findQuestionsByScheduleId(Integer scheduleId);

    Question findOne(Integer id);

    Question save(Question question);

    Question update(Question question);

    void delete(Integer id);
}
