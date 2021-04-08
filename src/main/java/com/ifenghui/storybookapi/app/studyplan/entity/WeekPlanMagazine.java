package com.ifenghui.storybookapi.app.studyplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.util.Date;

/**
 * 周计划杂志介绍
 */
@Entity
@Table(name="story_week_plan_magazine")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WeekPlanMagazine {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    @JsonIgnore
    String imgPath;
    Integer wordNum;
    Integer date;
    Date createTime;

    @Transient
    Integer isFinish;

    @JsonProperty("imgPathUrl")
    String getImgPathUrl(){
        return MyEnv.env.getProperty("oss.url")+"planMagazine/" + imgPath;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getWordNum() {
        return wordNum;
    }

    public void setWordNum(Integer wordNum) {
        this.wordNum = wordNum;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }
}
