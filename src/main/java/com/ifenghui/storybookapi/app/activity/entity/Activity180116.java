package com.ifenghui.storybookapi.app.activity.entity;

/**
 * Created by jia on 2016/12/28.
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="story_activity_180116")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="vip卡",addAble = false,editAble = false,deleteAble = false)
@Deprecated
public class Activity180116 {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String code;

    String  phone;
    Integer teacherId;
    Integer userId;

    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
