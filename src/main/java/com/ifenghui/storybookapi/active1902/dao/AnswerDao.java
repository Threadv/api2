package com.ifenghui.storybookapi.active1902.dao;

import com.ifenghui.storybookapi.active1902.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Date: 2019/2/19 14:10
 * @Description:
 */
public interface AnswerDao extends JpaRepository<Answer,Integer> {


    /**
     * 查看问题答案
     * @param questionId
     * @return
     */
    List<Answer> findAnswersByQuestionId(Integer questionId);
}
