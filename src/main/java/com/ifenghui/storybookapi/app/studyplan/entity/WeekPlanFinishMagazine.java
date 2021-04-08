package com.ifenghui.storybookapi.app.studyplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 周计划杂志任务完成记录
 */
@Entity
@Table(name="story_week_plan_finish_magazine")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WeekPlanFinishMagazine {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    Integer magPlanId;
    Integer userId;
    Integer wordNum;
    Integer planType;
    Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMagPlanId() {
        return magPlanId;
    }

    public void setMagPlanId(Integer magPlanId) {
        this.magPlanId = magPlanId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWordNum() {
        return wordNum;
    }

    public void setWordNum(Integer wordNum) {
        this.wordNum = wordNum;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(WeekPlanStyle planType) {
        this.planType = planType.getId();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
