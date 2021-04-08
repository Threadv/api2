package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.util.Date;

/**
 * 阅读记录介绍表
 */
@Entity
@Table(name="story_read_plan")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReadPlan {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String intro;

    @JsonIgnore
    String rewardUserList;

    Integer planName;

    Date createTime;

    String content;

    String title;

    @Transient
    @JsonProperty("lastMonthRewardUser")
    String lastMonthRewardUser;

    @JsonIgnore
    Integer status;

    @Transient
    Integer joinNumber;

    @JsonIgnore
    @Transient
    public String getRewardUserUrl(){
        if(this.status.equals(2)){
            return MyEnv.env.getProperty("read.plan.user.reward.url") + this.planName;
        } else {
            return null;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPlanName() {
        return planName;
    }

    public void setPlanName(Integer planName) {
        this.planName = planName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRewardUserList() {
        return rewardUserList;
    }

    public void setRewardUserList(String rewardUserList) {
        this.rewardUserList = rewardUserList;
    }

    public String getLastMonthRewardUser() {
        return lastMonthRewardUser;
    }

    public void setLastMonthRewardUser(String lastMonthRewardUser) {
        this.lastMonthRewardUser = lastMonthRewardUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getJoinNumber() {
        return joinNumber;
    }

    public void setJoinNumber(Integer joinNumber) {
        this.joinNumber = joinNumber;
    }
}
