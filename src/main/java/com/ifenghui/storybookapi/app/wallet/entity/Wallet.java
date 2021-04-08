package com.ifenghui.storybookapi.app.wallet.entity;

/**
 * Created by wml on 2017/2/16.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.ifenghui.storybookapi.app.user.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 钱包，保存用户的人民币余额，星星/经验余额。
 */
@Entity
@Table(name="story_wallet")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Wallet implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    Long id;

    /**
     * ios余额
     */
    Integer balance;

    /**
     * 星星余额
     */
    Integer starCount;

    Integer status;


    Date createTime;

    Integer userId;

    /**
     * android余额
     */
    Integer balanceAndroid;

    /**
     * 小程序余额
     */
    Integer balanceSmallProgram;

    Integer discount;

    /**
     * 分享大使现金余额
     */
    Integer balanceCash;

    @Transient
    String discountIntro;


    //    @CmsColumn(name="用户",inputType = CmsInputType.FORGIN)
//    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId",insertable = false, updatable = false)
//    @NotFound(action= NotFoundAction.IGNORE)
    @Transient
    User user;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getStarCount() {
        if(starCount == null){
            starCount = 0;
        }
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getDiscountIntro() {
        if(this.discount == 90){
            this.discountIntro = "再享9折";
        } else if(this.discount == 80){
            this.discountIntro = "再享8折";
        } else if(this.discount == 70){
            this.discountIntro = "再享7折";
        }
        return discountIntro;
    }

    public void setDiscountIntro(String discountIntro) {
        this.discountIntro = discountIntro;
    }

    public Integer getBalanceSmallProgram() {
        return balanceSmallProgram;
    }

    public void setBalanceSmallProgram(Integer balanceSmallProgram) {
        this.balanceSmallProgram = balanceSmallProgram;
    }

    public Integer getBalanceCash() {
        return balanceCash;
    }

    public void setBalanceCash(Integer balanceCash) {
        this.balanceCash = balanceCash;
    }
}
