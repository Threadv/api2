package com.ifenghui.storybookapi.app.studyplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 周计划小任务完成记录
 */
@Entity
@Table(name="story_week_plan_task_finish")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WeekPlanTaskFinish {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    Integer planTaskId;
    Integer weekPlanId;
    Integer userId;
    Date createTime;
    Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanTaskId() {
        return planTaskId;
    }

    public void setPlanTaskId(Integer planTaskId) {
        this.planTaskId = planTaskId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
