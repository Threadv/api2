package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 故事ip标签 与 故事关联表
 */
@Entity
@Table(name="story_ip_label_story")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class IpLabelStory implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    Integer storyId;
    Integer ipId;
    Integer ipLabelId;
    Integer orderBy;
    Date createTime;
    Integer ipLabelParentId;

    @Transient
    Story story;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public Integer getIpId() {
        return ipId;
    }

    public void setIpId(Integer ipId) {
        this.ipId = ipId;
    }

    public Integer getIpLabelId() {
        return ipLabelId;
    }

    public void setIpLabelId(Integer ipLabelId) {
        this.ipLabelId = ipLabelId;
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

    public Integer getIpLabelParentId() {
        return ipLabelParentId;
    }

    public void setIpLabelParentId(Integer ipLabelParentId) {
        this.ipLabelParentId = ipLabelParentId;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
