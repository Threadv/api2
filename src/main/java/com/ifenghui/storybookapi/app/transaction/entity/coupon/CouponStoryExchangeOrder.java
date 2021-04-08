package com.ifenghui.storybookapi.app.transaction.entity.coupon;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.OrderStatusStyle;
import com.ifenghui.storybookapi.style.OrderStyle;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="story_coupon_story_exchange_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CouponStoryExchangeOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer orderId;

    Integer couponId;

    Integer orderType;

    Date createTime;

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setOrderStyle(OrderStyle orderStyle){
        this.orderType = orderStyle.getId();
    }

    public OrderStyle getOrderStyle(){
        if(orderType != null){
            return OrderStyle.getById(this.orderType);
        }
        return null;
    }
}
