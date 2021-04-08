package com.ifenghui.storybookapi.app.wallet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.AddStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="story_user_account_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/**
 * 故事飞船流水
 */
public class UserAccountRecord implements UserAccountRecordInterface {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    /**
     * 增加/减少数量
     */
    Integer amount;

    /**
     * 1增加/2减少
     */
    Integer type;

    /**
     * 产生原因
     */
    Integer buyType;

    /**
     * 文字描述
     */
    String intro;

    /**
     * 余额
     */
    Integer balance;

    /**
     * 钱包类型1ios 2android
     */
    Integer walletType;


    /**
     * 外部订单号，全局唯一标识
     */
    String outTradeNo;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    User user;

    Long userId;


//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.type=addStyle.getId();
    }

    public Integer getBuyType() {
        return buyType;
    }

//    public void setBuyType(Integer buyType) {
//        this.buyType = buyType;
//    }

    public void setRechargeStyle(RechargeStyle rechargeStyle){
        this.buyType=rechargeStyle.getId();
    }
    @Override
    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
    @Override
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if(user!=null){
            this.userId=user.getId();
        }
    }
    @Override
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getWalletType() {
        return walletType;
    }

    public void setWalletStyle(WalletStyle walletStyle) {
        this.walletType = walletStyle.getId();
    }
    @Override
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
