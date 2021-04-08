package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 道具领取记录
 * Created by wml on 2017/11/07.
 */
@Entity
@Table(name="story_parts_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "道具领取记录",intro = "道具领取记录")
public class PartsRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    Long partId;

    Long userId;

    Long storyId;

    @JsonIgnore
    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
