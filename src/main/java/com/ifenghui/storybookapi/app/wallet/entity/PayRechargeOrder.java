package com.ifenghui.storybookapi.app.wallet.entity;

/**
 * Created by wml on 2017/2/16.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.RechargePayStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 充值订单
 */
@Entity
@Table(name="story_pay_recharge_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PayRechargeOrder {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    /**
     * 金额
     */
    Integer amount;
    /**
     * 订单号
     */
    String orderCode;
    /**
     * 支付类型
     * OrderPayType
     */
    Integer buyType;
    /**
     * 支付账号
     */
    String payAccount;
    /**
     * 成功时间
     */
    Date successTime;
    /**
     * 状态
     * OrderStatusType
     */
    Integer status;
    /**
     * 类型（购买/订阅）",enumClassName =
     *
     */
    Integer type;
    /**
     * 渠道
     */
    String channel;
    /**
     * 支付宝交易号trade_no/微信支付订单号transaction_id
     */
    String tradeNo;
    /**
     * 是否是测试订单
     */
    Integer isTest;
    /**
     * 包名
     */
    String appName;//包名

    /**
     * 创建时间
     */
    Date createTime;

    /**
     * 钱包类型 1：ios 2：android
     */
    Integer walletType;

//    @CmsColumn(name="用户",inputType = CmsInputType.FORGIN)
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    User user;

    Integer userId;

    @Transient
    String wxPayNotifyUrl;

//    @CmsColumn(isShowToList = false)
    @Transient
    String aliPayNotifyUrl;

//    @CmsColumn(isShowToList = false)
    @Transient
    String iosAliPayNotifyUrl;

//    @CmsColumn(isShowToList = false)
    @Transient
    String iosPayNotifyUrl;
    @Transient
    String huaweiPayNotifyUrl;
//
//    @Transient
//    String childAliPayNotifyUrl;
//
//    @Transient
//    String childWxPayNotifyUrl;
//
//    @Transient
//    String childHuaweiPayNotifyUrl;
//
//
//    @Transient
//    String iosChildAliPayNotifyUrl;
//
//    @Transient
//    String iosChildWxPayNotifyUrl;

//    @CmsColumn(isShowToList = false)
    @Transient
    String orderString;

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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getBuyType() {
        return buyType;
    }

//    public void setBuyType(Integer buyType) {
//        this.buyType = buyType;
//    }
    public void setPayStyle(RechargePayStyle rechargePayStyle) {
        this.buyType = rechargePayStyle.getId();
    }

    public RechargePayStyle getPayStyle(){
        if(buyType!=null){
            return  RechargePayStyle.getById(buyType);
        }
        return null;
    }
    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

//    public void setType(Integer type) {
//        this.type = type;
//    }

    /**
     * 充值类型，做什么用，是否有后置内容
     * @param rechargeStyle
     */
    public void setRechargeStyle(RechargeStyle rechargeStyle){
        this.type=rechargeStyle.getId();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
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
        if(user!=null){
            this.userId=user.getId().intValue();
        }
        this.user = user;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWxPayNotifyUrl() {
        return wxPayNotifyUrl;
    }

    public void setWxPayNotifyUrl(String wxPayNotifyUrl) {
        this.wxPayNotifyUrl = wxPayNotifyUrl;
    }

    public String getAliPayNotifyUrl() {
        return aliPayNotifyUrl;
    }

    public void setAliPayNotifyUrl(String aliPayNotifyUrl) {
        this.aliPayNotifyUrl = aliPayNotifyUrl;
    }

    public String getIosAliPayNotifyUrl() {
        return iosAliPayNotifyUrl;
    }

    public void setIosAliPayNotifyUrl(String iosAliPayNotifyUrl) {
        this.iosAliPayNotifyUrl = iosAliPayNotifyUrl;
    }

    public String getIosPayNotifyUrl() {
        return iosPayNotifyUrl;
    }

    public void setIosPayNotifyUrl(String iosPayNotifyUrl) {
        this.iosPayNotifyUrl = iosPayNotifyUrl;
    }

    public String getHuaweiPayNotifyUrl() {
        return huaweiPayNotifyUrl;
    }

    public void setHuaweiPayNotifyUrl(String huaweiPayNotifyUrl) {
        this.huaweiPayNotifyUrl = huaweiPayNotifyUrl;
    }



    public String getOrderString() {
        return orderString;
    }

    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }

    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getWalletType() {
        return walletType;
    }

    public void setWalletStyle(WalletStyle walletStyle) {
        this.walletType = walletStyle.getId();
    }

    @Transient
    public WalletStyle getWalletStyle(){
        return WalletStyle.getById(this.walletType);
    }
}
