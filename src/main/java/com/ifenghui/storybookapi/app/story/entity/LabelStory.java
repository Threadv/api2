package com.ifenghui.storybookapi.app.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wml on 2016/12/23.
 * 故事标签 与 故事关联表（最早的标签关联）
 */
//@CmsTable(name="故事标签关联")
@Entity
@Table(name="story_label_story")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LabelStory implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    Long storyId;

    Long labelId;


    Integer status;

    Integer orderBy;

    Date createTime;

//    @Transient
//    Integer isBuy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
