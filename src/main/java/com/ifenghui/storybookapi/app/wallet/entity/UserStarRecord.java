package com.ifenghui.storybookapi.app.wallet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.AddStyle;
import com.ifenghui.storybookapi.style.StarRechargeStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 星星记录
 */
@Entity
@Table(name="story_user_star_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserStarRecord implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    /**
     * 增减数额
     */
    Integer amount;
    /**
     * 增减类型1，增加，2减少
     */
    Integer type;
    /**
     * 星星来源
     */
    Integer buyType;

    /**
     * 文字描述
     */
    String intro;

    /**
     * 增加/减少后的余额
     */
    Integer balance;
    /**
     * 用户id
     */
    Integer userId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

//    public void setType(Integer type) {
//        this.type = type;
//    }
    public void setAddStyle(AddStyle addStyle){
        this.type=addStyle.getId();
    }
    public Integer getBuyType() {
        return buyType;
    }

    public void setStarRechargeStyle(StarRechargeStyle starRechargeStyle){
        this.buyType=starRechargeStyle.getId();
    }
//    public void setBuyType(Integer buyType) {
//        this.buyType = buyType;
//    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
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
