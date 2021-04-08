package com.ifenghui.storybookapi.app.studyplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.ReadRecordTypeStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 周计划阅读记录
 */
@Entity
@Table(name="story_week_plan_read_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WeekPlanReadRecord implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    Integer targetValue;
    Integer userId;
    Integer wordCount;
    Date createTime;
    Integer planType;
    Integer targetType;
    Integer readType;
    Integer isStory;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(WeekPlanStyle planType) {
        this.planType = planType.getId();
    }

    public Integer getTargetType() {
        return targetType;
    }

    public LabelTargetStyle getLabelTargetType() {
        return LabelTargetStyle.getById(targetType);
    }

    public void setLabelTargetType(LabelTargetStyle labelTargetType) {
        this.targetType = labelTargetType.getId();
    }

    public Integer getReadType() {
        return readType;
    }

    public void setReadType(ReadRecordTypeStyle readType) {
        this.readType = readType.getId();
    }

    public Integer getIsStory() {
        return isStory;
    }

    public void setIsStory(Integer isStory) {
        this.isStory = isStory;
    }
}
