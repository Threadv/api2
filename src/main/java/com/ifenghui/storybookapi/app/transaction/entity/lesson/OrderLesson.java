package com.ifenghui.storybookapi.app.transaction.entity.lesson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 课程和订单关联表
 */
@Entity
@Table(name="story_order_lesson")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderLesson {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer orderId;

    Integer lessonId;

    Integer lessonItemId;

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
