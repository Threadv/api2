package com.ifenghui.storybookapi.app.transaction.entity.single;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="story_shopping_trolley")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/**
 * 购物车
 * Created by wml on 2017/2/16.
 */
public class ShoppingTrolley {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    Integer status;
    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    User user;

    Long userId;

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "storyId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    Story story;

    Long storyId;

    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if(user!=null&&this.userId==null){
            this.userId=user.getId();
        }
        this.user = user;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        if(story!=null&&this.storyId==null){
            this.storyId=story.getId();
        }
        this.story = story;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
}
