package com.ifenghui.storybookapi.active1902.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.util.List;

/**
 * @Date: 2019/2/19 13:52
 * @Description:
 */
@Entity
@Table(name = "activity1902_question")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer questionId;
    Integer scheduleId;
    String name;
    String content;

    String audio;
    @Transient
    List<Answer> answerList;

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAudio() {
        return audio;
    }

    public String getAudioUrl() {
        return MyEnv.env.getProperty("oss.url")+audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
