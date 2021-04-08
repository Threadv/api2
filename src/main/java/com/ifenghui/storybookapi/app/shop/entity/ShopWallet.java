package com.ifenghui.storybookapi.app.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Date;

/**
 * 商城钱包，星星。
 */
@Entity
@Table(name="story_wallet")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShopWallet {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;
    /**
     * ios余额
     */
    Integer balance;

    Integer status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    /**
     * 星星余额
     */
    Integer starCount;

    /**
     * android余额
     */
    Integer balanceAndroid;

    Integer discount;

    /**
     * 小程序余额
     */
    Integer balanceSmallProgram;

    String balanceCash;

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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public Integer getBalanceAndroid() {
        return balanceAndroid;
    }

    public void setBalanceAndroid(Integer balanceAndroid) {
        this.balanceAndroid = balanceAndroid;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getBalanceSmallProgram() {
        return balanceSmallProgram;
    }

    public void setBalanceSmallProgram(Integer balanceSmallProgram) {
        this.balanceSmallProgram = balanceSmallProgram;
    }

    public String getBalanceCash() {
        return balanceCash;
    }

    public void setBalanceCash(String balanceCash) {
        this.balanceCash = balanceCash;
    }
}
