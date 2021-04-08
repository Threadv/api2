package com.ifenghui.storybookapi.app.transaction.entity.coupon;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户的故事兑换券表（用户与故事兑换券类型的关联）
 */
@Entity
@Table(name="story_coupon_story_exchange_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CouponStoryExchangeUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer couponId;

    Integer userId;

    Integer status;

    Integer isExpire;

//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTime;

//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "couponId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    CouponStoryExchange couponStoryExchange;


    @Transient
    String name;

    @Transient
    String content;

    @Transient
    Integer isUsable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(Integer isExpire) {
        this.isExpire = isExpire;
    }

    public CouponStoryExchange getCouponStoryExchange() {
        return couponStoryExchange;
    }

    public void setCouponStoryExchange(CouponStoryExchange couponStoryExchange) {
        this.couponStoryExchange = couponStoryExchange;
    }

    public String getName() {
        return this.couponStoryExchange.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return this.couponStoryExchange.getContent();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsUsable() {
        if(this.getStatus().equals(0)){
            this.isUsable = 1;
        } else {
            this.isUsable = 0;
        }
        return isUsable;
    }

    public void setIsUsable(Integer isUsable) {
        this.isUsable = isUsable;
    }
}
