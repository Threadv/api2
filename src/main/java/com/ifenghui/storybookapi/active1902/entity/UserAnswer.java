package com.ifenghui.storybookapi.active1902.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * @Date: 2019/2/19 13:52
 * @Description:
 */
@Entity
@Table(name = "activity1902_user_answer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserAnswer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer  id;

    Integer userId;
    Integer scheduleId;
    Integer questionId;
    Integer answerId;
    Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }
}
