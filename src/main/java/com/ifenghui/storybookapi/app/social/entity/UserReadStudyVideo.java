package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户课程视频完成记录表
 */
@Entity
@Table(name="story_user_read_study_video")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserReadStudyVideo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer itemId;

    Integer videoId;

    Integer userId;

    Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
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
}
