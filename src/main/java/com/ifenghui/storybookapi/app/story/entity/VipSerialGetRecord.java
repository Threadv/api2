package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 会员领取系列记录
 */
@Entity
@Table(name="story_vip_serial_get_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VipSerialGetRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer serialStoryId;

    Date createTime;

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

    public Integer getSerialStoryId() {
        return serialStoryId;
    }

    public void setSerialStoryId(Integer serialStoryId) {
        this.serialStoryId = serialStoryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
