package com.ifenghui.storybookapi.app.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.CheckCodeStyle;

import javax.persistence.*;
import java.util.Date;
/**
 * 手机验证码
 */
@Entity
@Table(name="story_phone_check_code")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PhoneCheckCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String content;

    Date endTime;

    Date createTime;

    Integer type;

    String phone;

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

    public Integer getType() {
        return type;
    }

    public void setType(CheckCodeStyle type) {
        this.type = type.getId();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
