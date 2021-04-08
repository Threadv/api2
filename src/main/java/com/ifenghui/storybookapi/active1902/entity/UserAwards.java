package com.ifenghui.storybookapi.active1902.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;

import javax.persistence.*;
import java.util.Date;

/**
 * @Date: 2019/2/19 13:52
 * @Description:
 */
@Entity
@Table(name = "activity1902_user_awards")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserAwards {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer type;
    Integer userId;
    Integer scheduleId;
    Integer storyId;
    Integer serialStoryId;
    Date createTime;

    @Transient
    Story story;
    @Transient
    SerialStory serialStory;
    @Transient
    Schedule schedule;

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public SerialStory getSerialStory() {
        return serialStory;
    }

    public void setSerialStory(SerialStory serialStory) {
        this.serialStory = serialStory;
    }

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

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public Integer getSerialStoryId() {
        return serialStoryId;
    }

    public void setSerialStoryId(Integer serialStoryId) {
        this.serialStoryId = serialStoryId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
