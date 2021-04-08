package com.ifenghui.storybookapi.app.transaction.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.OrderStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 活动支付订单
 * v1：只记录成功订单
 */
@Entity
@Table(name="story_order_pay_activity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderPayActivity {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer amount;

    Integer activityGoodsId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    Integer status;

    //冗余说明文字
    String content;

    //冗余图表
    String icon;

    Integer payType;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getActivityGoodsId() {
        return activityGoodsId;
    }

    public void setActivityGoodsId(Integer activityGoodsId) {
        this.activityGoodsId = activityGoodsId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public String getIconUrl() {
        return MyEnv.env.getProperty("oss.url")+"/"+icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }
}
