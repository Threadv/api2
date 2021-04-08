package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户完成阅读计划表
 */
@Entity
@Table(name="story_user_read_plan_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserReadPlanRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer planName;

    java.sql.Date finishRecord;

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

    public Integer getPlanName() {
        return planName;
    }

    public void setPlanName(Integer planName) {
        this.planName = planName;
    }

    public java.sql.Date getFinishRecord() {
        return finishRecord;
    }

    public void setFinishRecord(java.sql.Date finishRecord) {
        this.finishRecord = finishRecord;
    }
}
