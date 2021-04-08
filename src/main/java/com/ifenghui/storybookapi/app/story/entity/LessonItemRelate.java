package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 小课程与课程关联表
 */
@Entity
@Table(name="story_lesson_item_relate")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LessonItemRelate implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer type;

    Integer itemId;

    Integer storyId;

    Integer status;

    Date createTime;

    @Transient
    Story story;

    @Transient
    LessonStudyVideo lessonStudyVideo;

    @Transient
    Integer isRead;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
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

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public LessonStudyVideo getLessonStudyVideo() {
        return lessonStudyVideo;
    }

    public void setLessonStudyVideo(LessonStudyVideo lessonStudyVideo) {
        this.lessonStudyVideo = lessonStudyVideo;
    }
}
