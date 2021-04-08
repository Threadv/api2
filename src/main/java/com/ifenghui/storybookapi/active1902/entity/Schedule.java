package com.ifenghui.storybookapi.active1902.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.ifenghui.storybookapi.config.MyEnv;
import org.springframework.core.env.Environment;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Date: 2019/2/19 13:52
 * @Description:
 */
@Entity
@Table(name = "activity1902_schedule")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Schedule {
    static public Environment env;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer  id;

    Integer  finish_num;
    Integer  add_num;
    String icon;
    String img;
    String name;
    String questionImg;
    String titImg;
    Date createTime;
    Integer status;

    @Transient
    List<Question> questions;

    @JsonProperty("questionImgUrl")
    public String getQuestionImgUrl() {
        return MyEnv.env.getProperty("oss.url")+"activity1902/"+this.questionImg;
    }

    @JsonProperty("titImgUrl")
    public String getTitImgUrl() {
        return MyEnv.env.getProperty("oss.url")+"activity1902/"+this.titImg;
    }

    public String getQuestionImg() {
        return questionImg;
    }

    public void setQuestionImg(String questionImg) {
        this.questionImg = questionImg;
    }

    public String getTitImg() {
        return titImg;
    }

    public void setTitImg(String titImg) {
        this.titImg = titImg;
    }

    @JsonProperty("iconUrl")
    public String getIconUrl() {
        return MyEnv.env.getProperty("oss.url")+"activity1902/"+this.icon;
    }

    @JsonProperty("imgUrl")
    public String getImgUrl() {
        return MyEnv.env.getProperty("oss.url")+"activity1902/"+this.img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getFinish_num() {
        return finish_num;
    }

    public void setFinish_num(Integer finish_num) {
        this.finish_num = finish_num;
    }

    public Integer getAdd_num() {
        return add_num;
    }

    public void setAdd_num(Integer add_num) {
        this.add_num = add_num;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
