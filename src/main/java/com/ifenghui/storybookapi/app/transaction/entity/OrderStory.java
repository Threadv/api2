package com.ifenghui.storybookapi.app.transaction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.entity.User;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 故事与订单关联
 */
@Entity
@Table(name="story_order_story")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="单本购买订单单本故事关联",addAble = false,editAble = false,deleteAble = false)
/**
 * Created by wml on 2017/2/16.
 */
public class OrderStory implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
//    Long storyId;
    Long userId;
//    @CmsColumn(name="订单id",intro = "单本购买订单id")
    Long orderId;

    Long storyId;

    Integer storyPrice;

//    @JsonIgnore
//    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId",insertable = false, updatable = false)
//    @NotFound(action= NotFoundAction.IGNORE)
//    User user;

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
//    @JoinColumn(name = "storyId",insertable = false, updatable = false)
//    @NotFound(action= NotFoundAction.IGNORE)
//    Story story;

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    PayStoryOrder payStoryOrder;

//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

//    public Story getStory() {
//        return story;
//    }
//
//    public void setStory(Story story) {
//        if(story!=null&&story.getId()!=null){
//            this.storyId=story.getId();
//        }
//        this.story = story;
//    }

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

    public PayStoryOrder getPayStoryOrder() {
        return payStoryOrder;
    }

    public void setPayStoryOrder(PayStoryOrder payStoryOrder) {
        this.payStoryOrder = payStoryOrder;
    }

    public Integer getStoryPrice() {
        return storyPrice;
    }

    public void setStoryPrice(Integer storyPrice) {
        this.storyPrice = storyPrice;
    }
}
