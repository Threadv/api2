package com.ifenghui.storybookapi.app.transaction.entity.vip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.OrderPayStyle;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="story_pay_vip_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PayVipOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer originalPrice;

    Integer couponAmount;

    Integer amount;

    Date successTime;

    Integer status;

    Date createTime;

    Integer userDiscount;

    Integer type;

    String code;

    Integer isDel;

    Integer isTest;

    String channel;

    Integer priceId;

    String remark;

    @Transient
    Integer mixOrderId;

    public Integer getMixOrderId() {
        return mixOrderId;
    }

    public void setMixOrderId(Integer mixOrderId) {
        this.mixOrderId = mixOrderId;
    }

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

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserDiscount() {
        return userDiscount;
    }

    public void setUserDiscount(Integer userDiscount) {
        this.userDiscount = userDiscount;
    }

    public Integer getType() {
        return type;
    }

    public void setPayStyle(OrderPayStyle type) {
        this.type = type.getId();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
