package com.ifenghui.storybookapi.app.transaction.entity.subscription;

/**
 * Created by wml on 2017/2/16.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 订阅订单（旧的 已不产生订单）
 */
@Entity
@Table(name="story_pay_subscription_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "订阅订单管理",addAble = false,editAble = false,deleteAble = false)
public class PaySubscriptionOrder {

    public void init(){
//        this.setOriginalPrice(paySubscriptionPrice.getPrice());//原价
//        paySubscriptionOrder.setAmount(lastAmount);//折扣/优惠后的金额
        this.setOrderCode("");//订单号
        this.setSuccessTime(new Date());
        this.setStatus(0);
        this.setSubscriptionId(0L);
        this.setDiscount(1);
//        paySubscriptionOrder.setPriceId(priceId);
        this.setPayStyle(OrderPayStyle.ALI_PAY);//余额订阅
        this.setCode("");
//        paySubscriptionOrder.setMonth(paySubscriptionPrice.getMonth());
        this.setCreateTime(new Date());
        this.setSuccessTime(new Date());
        this.setIsDel(0);

    }
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
//    @CmsColumn(name="id")
    Long id;
//    @CmsColumn(name="原价")
    Integer originalPrice;

//    @CmsColumn(name="折扣后所支付金额")
    Integer amount;
//    @CmsColumn(name="订单号")
    String orderCode;
//    @CmsColumn(name="支付类型",enumClassName = OrderPayType.class,dataType = CmsDataType.MAP,inputType = CmsInputType.SELECT)
//    Integer buyType;

//    @CmsColumn(name="成功时间")
    Date successTime;
//    @CmsColumn(name="状态",enumClassName = OrderStatusType.class,dataType = CmsDataType.MAP,inputType = CmsInputType.SELECT)
    Integer status;
//    @CmsColumn(name="订阅记录表id")
    Long subscriptionId;
//    @CmsColumn(name="订阅期刊价格id")
    Long priceId;
//    @CmsColumn(name="月数",enumClassName = OrderStatusType.class,dataType = CmsDataType.MAP,inputType = CmsInputType.SELECT)
    Integer month;
//    @CmsColumn(name="购买类型",enumClassName = OrderProductType.class,dataType = CmsDataType.MAP,inputType = CmsInputType.SELECT)
//    Integer targetType;
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;

//    @CmsColumn(name="用户",inputType = CmsInputType.FORGIN)
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = true, updatable = true)
    @NotFound(action= NotFoundAction.IGNORE)
    User user;

//    @CmsColumn(name="购买时的折扣")
    Integer discount;
//    @CmsColumn(name="类型1微信2支付宝3余额4兑换码")
    Integer type;
//    @CmsColumn(name="兑换码")
    String code;
//    @CmsColumn(name="isDel")
    Integer isDel;
    @JsonIgnore
    Integer isTest;
    @JsonIgnore
    String channel;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getType() {
        return type;
    }



    public void setPayStyle(OrderPayStyle payStyle){
        this.type=payStyle.getId();
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
}
