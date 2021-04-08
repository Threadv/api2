package com.ifenghui.storybookapi.app.social.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * SerialStory 签到表
 */
@Entity
@Table(name="story_check_in")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CheckIn {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    //用户id
    Integer userId;

    //签到时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    Date checkInTime;

    //签到连续天数
    Integer continueDays;

    //签到总天数
    Integer countDays;

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

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Integer getContinueDays() {
        return continueDays;
    }

    public void setContinueDays(Integer continueDays) {
        this.continueDays = continueDays;
    }

    public Integer getCountDays() {
        return countDays;
    }

    public void setCountDays(Integer countDays) {
        this.countDays = countDays;
    }
}
