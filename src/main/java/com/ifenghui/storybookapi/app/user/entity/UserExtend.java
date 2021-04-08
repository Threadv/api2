package com.ifenghui.storybookapi.app.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/***
 * 用户的冗余信息表
 */
@Entity
@Table(name="story_user_extend")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserExtend implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer wordCount;

    Integer vocabularyCount;

    Integer userParentId;

    Integer deviceLimitNum;

    Integer cashBalance;

    Integer shareFriendNum;

    Integer friendTradeNum;

    Integer friendTradeAmount;

    Integer weekPlanType;

    Integer planChangeCount;

    /**与weekPlanType 意义一致2.11.0 添加*/
    Integer weekPlanTypeNew;

    public Integer getWeekPlanTypeNew() {
        return weekPlanTypeNew;
    }

    public void setWeekPlanTypeNew(Integer weekPlanTypeNew) {
        this.weekPlanTypeNew = weekPlanTypeNew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getVocabularyCount() {
        return vocabularyCount;
    }

    public void setVocabularyCount(Integer vocabularyCount) {
        this.vocabularyCount = vocabularyCount;
    }

    public Integer getUserParentId() {
        return userParentId;
    }

    public void setUserParentId(Integer userParentId) {
        this.userParentId = userParentId;
    }

    public Integer getDeviceLimitNum() {
        return deviceLimitNum;
    }

    public void setDeviceLimitNum(Integer deviceLimitNum) {
        this.deviceLimitNum = deviceLimitNum;
    }

    public Integer getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(Integer cashBalance) {
        this.cashBalance = cashBalance;
    }

    public Integer getShareFriendNum() {
        return shareFriendNum;
    }

    public void setShareFriendNum(Integer shareFriendNum) {
        this.shareFriendNum = shareFriendNum;
    }

    public Integer getFriendTradeNum() {
        return friendTradeNum;
    }

    public void setFriendTradeNum(Integer friendTradeNum) {
        this.friendTradeNum = friendTradeNum;
    }

    public Integer getFriendTradeAmount() {
        return friendTradeAmount;
    }

    public void setFriendTradeAmount(Integer friendTradeAmount) {
        this.friendTradeAmount = friendTradeAmount;
    }

    public Integer getWeekPlanType() {
        return weekPlanType;
    }

    public void setWeekPlanType(Integer weekPlanType) {
        this.weekPlanType = weekPlanType;
    }

    public Integer getPlanChangeCount() {
        return planChangeCount;
    }

    public void setPlanChangeCount(Integer planChangeCount) {
        this.planChangeCount = planChangeCount;
    }
}
