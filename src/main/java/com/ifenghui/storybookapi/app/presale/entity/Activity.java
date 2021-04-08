package com.ifenghui.storybookapi.app.presale.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "story_pre_sale_activity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String content;

    Integer userType;

    //开始时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date startTime;

    //结束时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTime;

    String wxRedirectUri;

    String wxRedirectKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getWxRedirectUri() {
        return wxRedirectUri;
    }

    public void setWxRedirectUri(String wxRedirectUri) {
        this.wxRedirectUri = wxRedirectUri;
    }

    public String getWxRedirectKey() {
        return wxRedirectKey;
    }

    public void setWxRedirectKey(String wxRedirectKey) {
        this.wxRedirectKey = wxRedirectKey;
    }
}
