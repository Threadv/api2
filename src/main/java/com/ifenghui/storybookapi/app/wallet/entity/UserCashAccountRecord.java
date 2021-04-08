package com.ifenghui.storybookapi.app.wallet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.AddStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户现金账户流水记录
 */
@Entity
@Table(name="story_user_cash_account_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserCashAccountRecord implements UserAccountRecordInterface{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    Long userId;

    Integer amount;

    Integer type;

    Date createTime;

    String intro;

    Integer balance;

    String outTradeNo;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Override
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    @Override
    public Integer getType() {
        return type;
    }

//    public void setType(Integer type) {
//        this.type = type;
//    }

    public void setAddStyle(AddStyle addStyle){
        this.type = addStyle.getId();
    }
    @Override
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Override
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
    @Override
    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
    @Override
    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    @Override
    public Date getCreateTimeFormat() {
        return createTime;
    }
}
