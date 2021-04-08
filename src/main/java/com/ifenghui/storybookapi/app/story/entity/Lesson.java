package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 课程表
 */
@Entity
@Table(name="story_lesson")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class Lesson implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String name;

    String rightAge;

    @JsonIgnore
    String imgPath;

    @JsonProperty("imgPath")
    String getImgPathUrl(){
        return MyEnv.env.getProperty("oss.url") + "lesson/" + imgPath;
    }

    @JsonProperty("iconPath")
    String getIconPathUrl() {
        if(this.id == 1){
            return MyEnv.env.getProperty("oss.url") + "lesson/enlightenIcon.png";
        } else {
            return MyEnv.env.getProperty("oss.url") + "lesson/growthIcon.png";
        }
    }

    @JsonProperty("smallPath")
    String getSmallPathUrl() {
        if(this.id == 1){
            return MyEnv.env.getProperty("oss.url") + "lesson/enlightenSmall.png";
        } else {
            return MyEnv.env.getProperty("oss.url") + "lesson/growthSmall.png";
        }
    }

    @JsonProperty("intro")
    String getIntro() {
        if(this.id == 1){
            return "故事·认知·亲子";
        } else {
            return "故事·识字·亲子";
        }
    }

    @JsonProperty("shortName")
    public String getShortName(){
        if(this.id == 1) {
            return "启蒙版";
        } else {
            return "成长版";
        }
    }

    String duration;

    Integer lessonItemCount;

    Integer orderBy;

    Integer status;

    Date createTime;

    Integer perPrice;

    @Transient
    Integer hasBuyNum;

    @Transient
    Integer hasUpdateNum;

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

    public String getRightAge() {
        return rightAge;
    }

    public void setRightAge(String rightAge) {
        this.rightAge = rightAge;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getLessonItemCount() {
        return lessonItemCount;
    }

    public void setLessonItemCount(Integer lessonItemCount) {
        this.lessonItemCount = lessonItemCount;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getPerPrice() {
        return perPrice;
    }

    public void setPerPrice(Integer perPrice) {
        this.perPrice = perPrice;
    }

    public Integer getHasBuyNum() {
        return hasBuyNum;
    }

    public void setHasBuyNum(Integer hasBuyNum) {
        this.hasBuyNum = hasBuyNum;
    }

    public Integer getHasUpdateNum() {
        return hasUpdateNum;
    }

    public void setHasUpdateNum(Integer hasUpdateNum) {
        this.hasUpdateNum = hasUpdateNum;
    }
}
