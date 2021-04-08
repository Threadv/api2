package com.ifenghui.storybookapi.app.studyplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 周计划参加记录表
 */
@Entity
@Table(name="story_week_plan_join")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WeekPlanJoin implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer planType;

    Date createTime;

    Date beginTime;

    Integer buyNum;

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

    public WeekPlanStyle getWeekPlanStyle() {
        return WeekPlanStyle.getById(planType);
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setWeekPlanStyle(WeekPlanStyle weekPlanStyle) {
        this.planType = weekPlanStyle.getId();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }
}
