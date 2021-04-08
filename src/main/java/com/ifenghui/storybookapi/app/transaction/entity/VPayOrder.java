package com.ifenghui.storybookapi.app.transaction.entity;

/**
 * Created by wml on 2017/6/29.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.Coupon;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferred;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonOrder;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonPrice;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionPrice;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 总订单表（旧的视图）
 */
@Entity
@Table(name="story_v_pay_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "单本购买和订阅总订单视图管理",addAble = false,editAble = false,deleteAble = false)
public class VPayOrder {
    @JsonIgnore
    public static int type_story = 1;

    @JsonIgnore
    public static int type_subscription = 2;

    @JsonIgnore
    public static int type_serial_story = 3;

    @JsonIgnore
    public static int type_lesson = 4;

//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    @CmsColumn(name="id")
//    @JsonIgnore
//    @Transient
    public VPayOrder(){}

    public VPayOrder(PayStoryOrder findPayStoryOrder){
        this.type=1;
        this.setUserId(findPayStoryOrder.getUser().getId());
        this.setOrderId(findPayStoryOrder.getId());
        this.setCreateTime(findPayStoryOrder.getCreateTime());
        this.setIsDel(findPayStoryOrder.getIsDel());
        this.setBuyType(findPayStoryOrder.getBuyType());
    }

    public VPayOrder(OrderStory orderStory){
        this.type=1;
        this.setUserId(orderStory.getUserId());
        this.setOrderId(orderStory.getId());
        this.setCreateTime(orderStory.getCreateTime());
//        this.setIsDel(orderStory);

    }

    public VPayOrder(PayLessonOrder payLessonOrder) {
        this.type = type_lesson;
        this.setUserId(payLessonOrder.getUserId().longValue());
        this.setOrderId(payLessonOrder.getId().longValue());
        this.setCreateTime(payLessonOrder.getCreateTime());
        this.setIsDel(payLessonOrder.getIsDel());
        this.setBuyType(payLessonOrder.getType());
    }

    public VPayOrder(PaySerialStoryOrder paySerialStoryOrder){
        this.type = type_serial_story;
        this.setUserId(paySerialStoryOrder.getUserId().longValue());
        this.setOrderId(paySerialStoryOrder.getId().longValue());
        this.setCreateTime(paySerialStoryOrder.getCreateTime());
        this.setIsDel(paySerialStoryOrder.getIsDel());
        this.setBuyType(paySerialStoryOrder.getType());
    }

    //OrderStory orderStory



    @JsonIgnore
//            @Transient
//    @Column(name = "order_id",insertable = false,updatable = false)
    Long id;

//    @Id
//    @Column(name = "id",insertable = false,updatable = false)
    @Id
    @Column(insertable = false,updatable = false)
    Long orderId;


    @Transient
    String mixCode;

//    @CmsColumn(name="原价")
    Integer originalPrice;

//    @CmsColumn(name="userId")
    Long userId;

//    @CmsColumn(name="折扣后所支付金额")
    Integer amount;
//    @CmsColumn(name="订单号")
    @JsonIgnore
    String orderCode;

    @JsonProperty("orderCode")
    public String getOrderCodeNew(){
        return "allOrder_" + this.orderId;
    }
//    @CmsColumn(name="成功时间")
    Date successTime;
//    @CmsColumn(name="状态",enumClassName = OrderStatusType.class,dataType = CmsDataType.MAP,inputType = CmsInputType.SELECT)
    Integer status;
//    @CmsColumn(name="type",enumClassName = OrderStatusType.class,dataType = CmsDataType.MAP,inputType = CmsInputType.SELECT)
    // 1故事订单 2订阅订单
    Integer type;
    @JsonIgnore
//    @CmsColumn(name="删除",enumClassName = OrderStatusType.class,dataType = CmsDataType.MAP,inputType = CmsInputType.SELECT)
    Integer isDel;

//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;

//    @CmsColumn(name="用户",inputType = CmsInputType.FORGIN)
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    User user;

    @Transient
    OrderStyle orderStyle;

    public OrderStyle getOrderStyle() {
        this.orderStyle = OrderStyle.getById(this.type);
        return orderStyle;
    }



    @Column(updatable = false,insertable = false)
    @ManyToMany(cascade={CascadeType.REFRESH},targetEntity = Story.class,fetch= FetchType.LAZY)
    @JoinTable(name="story_order_story",
            joinColumns={@JoinColumn(name="orderId",referencedColumnName="orderId",updatable = false,insertable = false)}
            ,inverseJoinColumns={@JoinColumn(name="storyId",referencedColumnName="id")})
//    @CmsColumn(name="故事",inputType = CmsInputType.CHECKBOX,dataType = CmsDataType.DATABASE,isShowToList = false)
    List<Story> storys;


    @Transient
//    @CmsColumn(name="订单号")
            PaySubscriptionPrice paySubscriptionPrice;

    @Transient
    SerialStory serialStory;//故事集
//TODO: 需要两个券的列表

    /**
     * 现金券
     */
    @Transient
    List<Coupon> coupons;

    /**
     * 延长券
     */
    @Transient
    CouponDeferred couponDeferred;

//    @Transient
//    List<CouponAllInOne> couponMices;

//    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
//    @JoinColumn(name = "orderId",insertable = false, updatable = false)
//    @NotFound(action= NotFoundAction.IGNORE)
    @Transient
    PayRechargeOrder rechargeOrder;

    @Transient
    PayLessonPrice payLessonPrice;

    @Transient
    Integer buyType;//支付方式
//    /**
//     * 优惠券
//     */
//    @JsonIgnore
//    @Transient
//    List<Coupon> coupons;
//
//    /**
//     * 赠阅券
//     */
//    @JsonIgnore
//    @Transient
//    List<CouponDeferred> couponDeferreds;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
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

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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

    public List<Story> getStorys() {
        if(storys==null){storys=new ArrayList();}
        return storys;
    }

    public void setStorys(List<Story> storys) {
        this.storys = storys;
    }

    public PaySubscriptionPrice getPaySubscriptionPrice() {
        return paySubscriptionPrice;
    }

    public void setPaySubscriptionPrice(PaySubscriptionPrice paySubscriptionPrice) {
        this.paySubscriptionPrice = paySubscriptionPrice;
    }



    public List<Coupon> getCoupons() {
        if(coupons==null){
            coupons=new ArrayList<>();
        }
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public CouponDeferred getCouponDeferred() {
        return couponDeferred;
    }

    public void setCouponDeferred(CouponDeferred couponDeferred) {
        this.couponDeferred = couponDeferred;
    }

    public String getMixCode() {
        mixCode="T"+this.getType()+"I"+this.getOrderId();
        return mixCode;
    }

    public void setMixCode(String mixCode) {
        this.mixCode = mixCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public PayRechargeOrder getRechargeOrder() {
        return rechargeOrder;
    }

    public void setRechargeOrder(PayRechargeOrder rechargeOrder) {
        this.rechargeOrder = rechargeOrder;
    }

    public SerialStory getSerialStory() {
        return serialStory;
    }

    public void setSerialStory(SerialStory serialStory) {
        this.serialStory = serialStory;
    }

    public PayLessonPrice getPayLessonPrice() {
        return payLessonPrice;
    }

    public void setPayLessonPrice(PayLessonPrice payLessonPrice) {
        this.payLessonPrice = payLessonPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
