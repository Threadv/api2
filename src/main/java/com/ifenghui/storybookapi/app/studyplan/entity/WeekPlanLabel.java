package com.ifenghui.storybookapi.app.studyplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.WeekPlanLabelStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * 周计划标签
 */
@Entity
@Table(name="story_week_plan_label")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WeekPlanLabel implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    String content;
    Integer labelType;
    Integer parentId;
    Integer orderBy;
    Date createTime;

    @Transient
    String parentName;

    @Transient
    Integer isFinish;

    @Transient
    Integer finishNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WeekPlanLabelStyle getLabelType() {
        return WeekPlanLabelStyle.getById(labelType);
    }

    public void setLabelType(Integer labelType) {
        this.labelType = labelType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }
    
    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }
}
