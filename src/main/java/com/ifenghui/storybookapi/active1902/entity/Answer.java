package com.ifenghui.storybookapi.active1902.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.ifenghui.storybookapi.config.MyEnv;
import org.springframework.core.env.Environment;

import javax.persistence.*;

/**
 * @Date: 2019/2/19 13:52
 * @Description:
 */
@Entity
@Table(name = "activity1902_answer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Answer {
    static public Environment env;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    Integer isRight;
    Integer questionId;
    String name;
    String content;

    @JsonProperty("answerUrl")
    public String getAnswerUrl() {
        return MyEnv.env.getProperty("oss.url")+"activity1902/"+this.content;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
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
}
