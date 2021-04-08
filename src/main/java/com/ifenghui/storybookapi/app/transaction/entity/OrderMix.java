package com.ifenghui.storybookapi.app.transaction.entity;

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
import com.ifenghui.storybookapi.app.transaction.response.AbilityPlanPrice;
import com.ifenghui.storybookapi.app.transaction.response.SvipPrice;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单混合记录表，所有订单创建时需要在这个表创建个关联记录,
 * 主要功能用于 订单记录的展示
 */
@Entity
@Table(name="story_order_mix")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderMix {


//    @JsonIgnore
//    public static int type_story = 1;
//
//    @JsonIgnore
//    public static int type_subscription = 2;
//
//    @JsonIgnore
//    public static int type_serial_story = 3;
//
//    @JsonIgnore
//    public static int type_lesson = 4;
//
//    @JsonIgnore
//    public static int type_activity = 5;


    public OrderMix(){}

    public OrderMix(PayStoryOrder findPayStoryOrder){
        this.orderType = OrderStyle.STORY_ORDER.getId();
        this.setUserId(findPayStoryOrder.getUser().getId().intValue());
        this.setOrderId(findPayStoryOrder.getId().intValue());
        this.setCreateTime(findPayStoryOrder.getCreateTime());
        this.setIsDel(findPayStoryOrder.getIsDel());
        this.setBuyType(findPayStoryOrder.getBuyType());
    }

    public OrderMix(OrderStory orderStory){
        this.orderType = OrderStyle.STORY_ORDER.getId();
        this.setUserId(orderStory.getUserId().intValue());
        this.setOrderId(orderStory.getId().intValue());
        this.setCreateTime(orderStory.getCreateTime());
//        this.setIsDel(orderStory);

    }

    public OrderMix(PayLessonOrder payLessonOrder) {
        this.orderType = OrderStyle.LESSON_ORDER.getId();
        this.setUserId(payLessonOrder.getUserId());
        this.setOrderId(payLessonOrder.getId());
        this.setCreateTime(payLessonOrder.getCreateTime());
        this.setIsDel(payLessonOrder.getIsDel());
        this.setBuyType(payLessonOrder.getType());
    }

    public OrderMix(PaySerialStoryOrder paySerialStoryOrder){
        this.orderType =  OrderStyle.SERIAL_ORDER.getId();
        this.setUserId(paySerialStoryOrder.getUserId());
        this.setOrderId(paySerialStoryOrder.getId());
        this.setCreateTime(paySerialStoryOrder.getCreateTime());
        this.setIsDel(paySerialStoryOrder.getIsDel());
        this.setBuyType(paySerialStoryOrder.getType());
    }
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    //1故事订单，2订阅订单
    @JsonProperty("type")
    Integer orderType;

    Integer orderId;
    //购买用户
    Integer userId;

    //状态
    Integer status;

    //删除状态
    Integer isDel;

    //创建时间
    Date createTime;


//    @Transient
//    String mixCode;

    //原价
    @Transient
    Integer originalPrice;



    //实际金额
    @Transient
    Integer amount;




    @JsonProperty("orderCode")
    public String getOrderCodeNew(){
        return "allOrder_" + this.orderId;
    }

    @Transient
    //完成时间
    Date successTime;



    @Transient
    OrderStyle orderStyle;


    @Transient
//    @CmsColumn(name="订单号")
     PaySubscriptionPrice paySubscriptionPrice;


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

    @Transient
    PayRechargeOrder rechargeOrder;

    @Transient
    PayLessonPrice payLessonPrice;

    @Transient
    Integer buyType;//支付方式


    @Transient
    SerialStory serialStory;//故事集


    @Transient
    List<Story> storys;

    //在获取订单接口(getUserPayOrderV2)调用时,返回封装购买宝宝会读（优能计划）的信息
    @Transient
    AbilityPlanPrice abilityPlanPrice;

    public AbilityPlanPrice getAbilityPlanPrice() {
        return abilityPlanPrice;
    }

    public void setAbilityPlanPrice(AbilityPlanPrice abilityPlanPrice) {
        this.abilityPlanPrice = abilityPlanPrice;
    }

    @Transient
    OrderPayActivity activityGoods;//获得商品冗余

//    @TODO:活动表的类型对象

    @Transient
    SvipPrice svipPrice;

    public SvipPrice getSvipPrice() {
        return svipPrice;
    }

    public void setSvipPrice(SvipPrice svipPrice) {
        this.svipPrice = svipPrice;
    }

    public OrderStyle getOrderStyle() {
        this.orderStyle = OrderStyle.getById(this.orderType);
        return orderStyle;
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

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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
        return "T"+this.getOrderType()+"I"+this.getOrderId();
//        return mixCode;
    }

//    public void setMixCode(String mixCode) {
//        this.mixCode = mixCode;
//    }



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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OrderPayActivity getActivityGoods() {
        return activityGoods;
    }

    public void setActivityGoods(OrderPayActivity activityGoods) {
        this.activityGoods = activityGoods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
