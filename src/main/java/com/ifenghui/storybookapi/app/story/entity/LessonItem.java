package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 小课程描述表
 */
@Entity
@Table(name="story_lesson_item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LessonItem implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String name;

    @JsonIgnore
    String imgPath;

    @JsonProperty("imgPathUrl")
    String getImgPathUrl(){
        return MyEnv.env.getProperty("oss.url")+"lesson/"+imgPath;
    }

    @JsonIgnore
    String greyImgPath;

    @JsonProperty("greyImgPathUrl")
    String getGreyImgPathUrl(){
        return MyEnv.env.getProperty("oss.url")+"lesson/"+greyImgPath;
    }

    Integer lessonId;

    Integer lessonNum;

    Integer status;

    Date createTime;

    Integer isFree;

    @Transient
    Integer isBuyLessonItem;

    Integer hasContent;

    @Transient
    Integer isRead;

    @Transient
    Integer needRead;

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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
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

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public Integer getIsBuyLessonItem() {
        return isBuyLessonItem;
    }

    public void setIsBuyLessonItem(Integer isBuyLessonItem) {
        this.isBuyLessonItem = isBuyLessonItem;
    }

    @JsonIgnore
    public Integer getHasContent() {
        return hasContent;
    }

    @JsonProperty("hasContent")
    public Integer getHasContentValue(){
        if(MyEnv.TEST_USER_GROUP) {
            return 1;
        } else {
            if (this.hasContent != null && this.hasContent.equals(2)) {
                return 0;
            }
            return hasContent;
        }
    }

    public Integer getContentCondition(){
        if(MyEnv.TEST_USER_GROUP){
            return 1;
        } else {
            return hasContent;
        }
    }

    public void setHasContent(Integer hasContent) {
        this.hasContent = hasContent;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getNeedRead() {
        return needRead;
    }

    public void setNeedRead(Integer needRead) {
        this.needRead = needRead;
    }

    public String getGreyImgPath() {
        return greyImgPath;
    }

    public void setGreyImgPath(String greyImgPath) {
        this.greyImgPath = greyImgPath;
    }
}
