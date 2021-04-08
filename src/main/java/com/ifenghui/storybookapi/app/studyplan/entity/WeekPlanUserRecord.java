package com.ifenghui.storybookapi.app.studyplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 周计划推送给用户记录
 */
@Entity
@Table(name="story_week_plan_user_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class WeekPlanUserRecord implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    Integer weekPlanId;
    Integer userId;
    Integer magTaskId;
    Integer isFinish;
    Integer isFinishMag;
    Integer isFinishAll;
    Date createTime;
    Integer finishNum;
    Integer showStar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeekPlanId() {
        return weekPlanId;
    }

    public void setWeekPlanId(Integer weekPlanId) {
        this.weekPlanId = weekPlanId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMagTaskId() {
        return magTaskId;
    }

    public void setMagTaskId(Integer magTaskId) {
        this.magTaskId = magTaskId;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public Integer getIsFinishMag() {
        return isFinishMag;
    }

    public void setIsFinishMag(Integer isFinishMag) {
        this.isFinishMag = isFinishMag;
    }

    public Integer getIsFinishAll() {
        return isFinishAll;
    }

    public void setIsFinishAll(Integer isFinishAll) {
        this.isFinishAll = isFinishAll;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public Integer getShowStar() {
        return showStar;
    }

    public void setShowStar(Integer showStar) {
        this.showStar = showStar;
    }
}
