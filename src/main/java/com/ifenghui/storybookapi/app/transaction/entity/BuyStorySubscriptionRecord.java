package com.ifenghui.storybookapi.app.transaction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.app.story.entity.Story;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 故事订阅购买记录
 */
@Entity
@Table(name="story_buy_story_subscription_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BuyStorySubscriptionRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    //    @CmsColumn(name="用户")
    Long userId;

    Integer magazineId;

    @JsonIgnore
    Integer isTest;

    Long storyId;

    @Transient
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

    @JsonProperty("type")
    public Integer getType(){
        return 2;
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

    public Integer getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(Integer magazineId) {
        this.magazineId = magazineId;
    }
}
