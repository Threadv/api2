package com.ifenghui.storybookapi.app.transaction.entity.coupon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.OrderStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 代金券和订单关联关系表
 */
@Entity
@Table(name="story_coupon_order_relate")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CouponOrderRelate {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer orderId;

    Integer couponId;

    Date createTime;

    Integer orderType;

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

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderStyle orderType) {
        this.orderType = orderType.getId();
    }
}
