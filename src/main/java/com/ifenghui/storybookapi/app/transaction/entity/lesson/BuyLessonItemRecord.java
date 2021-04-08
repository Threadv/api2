package com.ifenghui.storybookapi.app.transaction.entity.lesson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 课程的每个小课程的购买记录
 */
@Entity
@Table(name="story_buy_lesson_item_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BuyLessonItemRecord implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer lessonItemId;

    Integer isTest;

    Integer isRead;

    Integer lessonId;

    Date createTime;

    Integer lessonNum;

    Integer isFree;

    //20190506 wsl增加，用于区分宝宝会读增加的记录，
    // 宝宝会读增加的记录有时效性
    Integer isBaobao;

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

    public Integer getLessonItemId() {
        return lessonItemId;
    }

    public void setLessonItemId(Integer lessonItemId) {
        this.lessonItemId = lessonItemId;
    }

    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public Integer getIsBaobao() {
        return isBaobao;
    }

    public void setIsBaobao(Integer isBaobao) {
        this.isBaobao = isBaobao;
    }
}
