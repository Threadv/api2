package com.ifenghui.storybookapi.app.transaction.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户分享的用户交易记录表
 */
@Entity
@Table(name="story_user_share_trade_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserShareTradeRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer orderMixId;

    Integer userId;

    Integer userParentId;

    Integer orderAmount;

    Integer rewardAmount;

    Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderMixId() {
        return orderMixId;
    }

    public void setOrderMixId(Integer orderMixId) {
        this.orderMixId = orderMixId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserParentId() {
        return userParentId;
    }

    public void setUserParentId(Integer userParentId) {
        this.userParentId = userParentId;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Integer rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
