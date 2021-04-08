package com.ifenghui.storybookapi.app.social.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * SerialStory 签到记录
 */
@Entity
@Table(name="story_check_in_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CheckInRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    //用户id
    Integer userId;

    //签到时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    Date checkInTime;

    //签到的第几天
    Integer dayCount;

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

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }
}
