package com.ifenghui.storybookapi.app.transaction.entity;

/**
 * Created by wml on 2017/2/16.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 故事订单表
 */
@Entity
@Table(name="story_pay_story_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "单本购买订单管理",addAble = false,editAble = false,deleteAble = false)
/**
 * 故事购买记录
 * 单本/订阅/系列购买后都会创建这个记录
 */
public class PayStoryOrder {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
//    @CmsColumn(name="id")
    Long id;
//    @CmsColumn(name="原价")
    Integer originalPrice;

    Integer couponAmount;

//    @CmsColumn(name="折扣后所支付金额")
    Integer amount;
//    @CmsColumn(name="订单号")
    String orderCode;
//    @CmsColumn(name="支付类型",enumClassName = OrderPayType.class,dataType = CmsDataType.MAP,inputType = CmsInputType.SELECT)
    Integer buyType;

//    @CmsColumn(name="成功时间")
    Date successTime;
//    @CmsColumn(name="状态",enumClassName = OrderStatusType.class,dataType = CmsDataType.MAP,inputType = CmsInputType.SELECT)
    Integer status;
//    @CmsColumn(name="商品id")
    Long payTarget;

//    @CmsColumn(name="购买类型",enumClassName = OrderProductType.class,dataType = CmsDataType.MAP,inputType = CmsInputType.SELECT)
    Integer targetType;
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;

    Long userId;

//    @CmsColumn(name="用户",inputType = CmsInputType.FORGIN)
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    User user;
//    @CmsColumn(name="数量")
    Integer num;
//    @CmsColumn(name="购买时的折扣")
    Integer discount;
//    @CmsColumn(name="isDel")
    Integer isDel;

    @JsonIgnore
    Integer isTest;

    @JsonIgnore
    String channel;

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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
//    @CmsColumn(isShowToList = false)
//    @Transient
//    String notifyUrl;


//    public Long getDealerId() {
//        return dealerId;
//    }
//
//    public void setDealerId(Long dealerId) {
//        this.dealerId = dealerId;
//    }

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

    @JsonProperty("orderCode")
    public String getOrderCode() {
        return MyEnv.env.getProperty("order.prefix") + "_"+ id;
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

    public Long getPayTarget() {
        return payTarget;
    }

    public void setPayTarget(Long payTarget) {
        this.payTarget = payTarget;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
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

//    public void setUser(User user) {
//
//        this.user = user;
//        if(user.getId()!=null){
//            this.userId=user.getId();
//        }
//    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
    //    public String getNotifyUrl() {
//        return notifyUrl;
//    }
//
//    public void setNotifyUrl(String notifyUrl) {
//        this.notifyUrl = notifyUrl;
//    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserDiscount() {
        return userDiscount;
    }

    public void setUserDiscount(Integer userDiscount) {
        this.userDiscount = userDiscount;
    }

    public void setPayStyle(OrderPayStyle payStyle){
        this.buyType = payStyle.getId();
    }

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
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
