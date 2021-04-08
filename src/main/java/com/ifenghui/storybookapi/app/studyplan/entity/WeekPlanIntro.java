package com.ifenghui.storybookapi.app.studyplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 周计划介绍表
 */
@Entity
@Table(name="story_week_plan_intro")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WeekPlanIntro {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    Integer weekNum;

    @JsonIgnore
    Integer planType;

    @Transient
    WeekPlanStyle weekPlanStyle;

    Integer status;
    Date createTime;

    @Transient
    Integer isHasPlan;

    @Transient
    Integer hasCandyNum;

    @Transient
    Integer isBuy;

    @JsonProperty("totalCandy")
    public Integer getTotalCandy() {
        return 8;
    }

    public Integer getIsHasPlan() {
        return isHasPlan;
    }

    public Integer getHasCandyNum() {
        return hasCandyNum;
    }

    public Integer getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }

    public void setHasCandyNum(Integer hasCandyNum) {
        this.hasCandyNum = hasCandyNum;
    }

    public void setIsHasPlan(Integer isHasPlan) {
        this.isHasPlan = isHasPlan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
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

    public WeekPlanStyle getWeekPlanStyle() {
        return WeekPlanStyle.getById(this.planType);
    }

    public void setWeekPlanStyle(WeekPlanStyle weekPlanStyle) {
        this.weekPlanStyle = weekPlanStyle;
    }
}
