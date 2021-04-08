package com.ifenghui.storybookapi.active1902.dao;

import com.ifenghui.storybookapi.active1902.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Date: 2019/2/19 14:10
 * @Description:
 */
public interface QuestionDao extends JpaRepository<Question,Integer> {

   Question findQuestionsByScheduleIdAndQuestionId(Integer scheduleId, Integer questionId);

   List<Question> findQuestionsByScheduleId(Integer scheduleId);
}
