package com.ifenghui.storybookapi.app.wallet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.CashAccountApplyStyle;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="story_cash_account_cash_apply")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/**
 * 推广大使提现申请
 */
public class CashAccountCashApply {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String intro;

    Integer amount;

    Integer status;

    Integer callbackStatus;

    Integer resultStatus;

    Integer type;

    Integer userId;

    String orderId;

    String account;

    Date createTime;

    Date successTime;

    String callbackMsg;

    String userInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCallbackStatus() {
        return callbackStatus;
    }

    public void setCallbackStatus(Integer callbackStatus) {
        this.callbackStatus = callbackStatus;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Integer getType() {
        return type;
    }

//    public void setType(Integer type) {
//        this.type = type;
//    }

    public void setType(CashAccountApplyStyle cashAccountApplyStyle){
        this.type = cashAccountApplyStyle.getId();
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

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCallbackMsg() {
        return callbackMsg;
    }

    public void setCallbackMsg(String callbackMsg) {
        this.callbackMsg = callbackMsg;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
