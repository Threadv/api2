package com.ifenghui.storybookapi.app.social.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.SignRecordStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 弹窗领取标记
 */
@Entity
@Table(name="story_sign_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SignRecord implements Serializable{


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    Integer userId;
    Integer type;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    Date createTime;

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

    public Integer getType() {
        return type;
    }

    public SignRecordStyle getSignRecordStyle() {
        return SignRecordStyle.getById(type);
    }

    public void setType(SignRecordStyle type) {
        this.type = type.getId();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
