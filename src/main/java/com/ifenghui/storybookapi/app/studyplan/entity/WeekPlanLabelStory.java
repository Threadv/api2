package com.ifenghui.storybookapi.app.studyplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.WeekPlanLabelStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 周计划标签与故事，视频，杂志关联表
 */
@Entity
@Table(name="story_week_plan_label_story")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WeekPlanLabelStory implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    Integer targetValue;
    Integer labelId;
    Integer orderBy;
    Integer labelType;
    Integer targetType;
    Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public WeekPlanLabelStyle getLabelType() {
        return WeekPlanLabelStyle.getById(labelType);
    }

    public void setLabelType(Integer labelType) {
        this.labelType = labelType;
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
}
