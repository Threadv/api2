package com.ifenghui.storybookapi.app.user.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2016/12/23.
 * 用户token表
 */
@Entity
@Table(name="story_user_token")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserToken implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;


    String token;

    String refreshToken;
    String device;

    String userAgent;

    Date createTime;

    @Transient
    User user;

    Integer isValid;

    String deviceName;

    String deviceUnique;

    Date lastLoginTime;

    String addr;

    Long userId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceUnique() {
        return deviceUnique;
    }

    public void setDeviceUnique(String deviceUnique) {
        this.deviceUnique = deviceUnique;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
