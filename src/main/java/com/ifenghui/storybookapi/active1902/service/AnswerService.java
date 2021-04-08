package com.ifenghui.storybookapi.active1902.service;

import com.ifenghui.storybookapi.active1902.entity.Answer;

import java.util.List;

/**
 * @Date: 2019/2/19 15:47
 * @Description:
 */
public interface AnswerService {


    /**
     * 获取问题答案列表
     * @param questionId
     * @return
     */
    List<Answer> getAnswersByQuestionId(Integer questionId);

    Answer findOne(Integer id);

    void delete(Integer id);

    Answer save(Answer answer);

    Answer update(Answer answer);
}
