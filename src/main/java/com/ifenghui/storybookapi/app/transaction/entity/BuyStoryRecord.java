package com.ifenghui.storybookapi.app.transaction.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.app.story.entity.Story;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wml on 2016/12/23.
 */

/**
 * 故事购买记录
 */
@Entity
@Table(name="story_buy_story_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="单本故事故事购买记录",addAble = false,editAble = false,deleteAble = false)
public class BuyStoryRecord implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
//    @CmsColumn(name="用户")
    Long userId;
//    @CmsColumn(name="类型")
    Integer type;

    @JsonIgnore
    Integer isTest;

    Long storyId;
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "storyId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
//    @CmsColumn(name="故事",inputType= CmsInputType.FORGIN,foreignName = "故事购买记录")
    Story story;

//    @CmsColumn(name="创建时间")
    Date createTime;

    @JsonProperty("isRecentUpdate")
    public Integer getRecentUpdate(){
        long now = System.currentTimeMillis();
        long lastWeek = now - 604800000;
        if(this.createTime.getTime() > lastWeek){
            return 1;
        } else {
            return 0;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        if (story != null){
           this.storyId = story.getId();
        }
        this.story = story;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

}
