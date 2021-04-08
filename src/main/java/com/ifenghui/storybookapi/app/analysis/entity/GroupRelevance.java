package com.ifenghui.storybookapi.app.analysis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.app.story.entity.Story;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wml on 2016/12/27.
 * 首页和故事关联关系
 */
@Entity
@Table(name="story_group_relevance")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "分组故事关联",intro = "",addAble = false)
public class GroupRelevance implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;


//    @CmsColumn(name="故事id")
    Long storyId;

//    @OneToOne(cascade = { CascadeType.REFRESH},fetch = FetchType.LAZY)
//    @JoinColumn(name = "storyId",insertable = false, updatable = false)
//    @NotFound(action= NotFoundAction.IGNORE)
    @Transient
    Story story;


    Long groupId;


    Integer isDel;


    Integer status;

    Integer orderBy;

//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW)
    Date createTime;

//    @JsonIgnore
//    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
//    @JoinColumn(name = "groupId",insertable = false, updatable = false)
//    @NotFound(action= NotFoundAction.IGNORE)
//    DisplayGroup group;


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

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
