package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.story.entity.Story;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户故事阅读完成记录
 */
@Entity
@Table(name="story_user_read_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="阅读记录",addAble = false,editAble = false,deleteAble = false)
/**
 * Created by wml on 2017/11/07.
 */
public class UserReadRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    Long storyId;
    Long userId;
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;
//    @CmsColumn(name="故事类型",dataType = CmsDataType.MAP,enumClassName = StoryType.class)
//1看故事，2听音频，属于故事类型
    Integer type;

    @Transient
    Story story;


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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
