package com.ifenghui.storybookapi.app.express.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * 物流中心手机号绑定
 */
@Entity
@Table(name="story_express_center_phone_bind")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class ExpressCenterPhoneBind implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

//   1 app用户，2微信用户
    Integer userType;

    //绑定的手机号
    String phone;


    Date createTime;

    Integer userOutId;

    //是否是当前已绑号码 活跃状态0不会瓯越;1活跃
    Integer isActive;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTimeFormat(){
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserOutId() {
        return userOutId;
    }

    public void setUserOutId(Integer userOutId) {
        this.userOutId = userOutId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
