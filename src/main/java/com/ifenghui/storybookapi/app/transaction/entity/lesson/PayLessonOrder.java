package com.ifenghui.storybookapi.app.transaction.entity.lesson;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.OrderPayStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 购买课程订单
 */

@Entity
@Table(name="story_pay_lesson_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PayLessonOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer originalPrice;

    Integer couponAmount;

    Integer amount;

    Date successTime;

    Integer status;

    Integer lessonId;

    Date createTime;

    Integer userDiscount;

    Integer orderDiscount;

    Integer type;

    String code;

    Integer isDel;

    Integer isTest;

    String channel;

    Integer num;

    Integer priceId;

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

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
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

    public Integer getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(Integer orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public void setPayStyle(OrderPayStyle payStyle){
        this.type=payStyle.getId();
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
