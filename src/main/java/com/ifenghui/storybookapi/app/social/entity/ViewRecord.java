package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.entity.User;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户故事的浏览记录表
 */
@Entity
@Table(name="story_view_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="观看记录",addAble = false,editAble = false,deleteAble = false)
/**
 * Created by jia on 2016/12/23.
 */
public class ViewRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    Long storyId;
//    Long userId;
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;
//    @CmsColumn(name="故事类型",dataType = CmsDataType.MAP,enumClassName = StoryType.class)
    Integer type;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = true, updatable = true)
    @NotFound(action= NotFoundAction.IGNORE)
    User user;

//    @Transient
//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
//    @JoinColumn(name = "storyId",insertable = true, updatable = true)
//    @NotFound(action= NotFoundAction.IGNORE)
//    @CmsColumn(name="故事",inputType= CmsInputType.FORGIN)
//    Story story;
//@CmsColumn(isShowToList = false)
@Transient
Story story;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
