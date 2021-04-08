package com.ifenghui.storybookapi.app.transaction.entity.serial;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.RechargePayStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 故事集订单
 */
@Entity
@Table(name="story_pay_serial_story_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PaySerialStoryOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer originalPrice;

    Integer amount;

    Date successTime;

    Integer status;

    Integer serialStoryId;

    Date createTime;

    Integer discount;

    Integer type;

    String code;

    Integer isDel;

    Integer isTest;

    String channel;

    Integer couponAmount;

    Integer userDiscount;

    Integer couponNum;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @JsonProperty("orderCode")
    public String getOrderCode() {
        return MyEnv.env.getProperty("order.prefix") + "_"+ id;
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

    public Integer getSerialStoryId() {
        return serialStoryId;
    }

    public void setSerialStoryId(Integer serialStoryId) {
        this.serialStoryId = serialStoryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getType() {
        return type;
    }

//    public void setType(Integer type) {
//        this.type = type;
//    }

    public void setPayStyle(OrderPayStyle payStyle){
        this.type=payStyle.getId();
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

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getUserDiscount() {
        return userDiscount;
    }

    public void setUserDiscount(Integer userDiscount) {
        this.userDiscount = userDiscount;
    }

    public Integer getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(Integer couponNum) {
        this.couponNum = couponNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
