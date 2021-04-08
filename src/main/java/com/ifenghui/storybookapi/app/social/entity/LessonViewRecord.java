package com.ifenghui.storybookapi.app.social.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.story.entity.Lesson;

import javax.persistence.*;
import java.util.Date;

/**
 * 课程浏览记录
 */
@Entity
@Table(name="story_lesson_view_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LessonViewRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer lessonId;

    Integer userId;

    Integer itemId;

    Integer targetValue;

    Integer targetType;

    Date createTime;

    Integer lessonNum;

    @Transient
    Lesson lesson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
