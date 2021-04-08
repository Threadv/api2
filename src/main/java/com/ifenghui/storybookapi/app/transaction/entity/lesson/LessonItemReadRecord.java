package com.ifenghui.storybookapi.app.transaction.entity.lesson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 小课程的阅读记录
 */
@Entity
@Table(name="story_lesson_item_read_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LessonItemReadRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer lessonId;

    Integer lessonItemId;

    Integer lessonNum;

    Date createTime;


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

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getLessonItemId() {
        return lessonItemId;
    }

    public void setLessonItemId(Integer lessonItemId) {
        this.lessonItemId = lessonItemId;
    }

    public Integer getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
