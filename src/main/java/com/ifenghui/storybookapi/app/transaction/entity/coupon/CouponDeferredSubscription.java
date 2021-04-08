package com.ifenghui.storybookapi.app.transaction.entity.coupon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="story_coupon_deferred_subscription")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="订阅订单订单和优惠券关联",addAble = false,editAble = false,deleteAble = false)
/**
 * 订阅订单订单和延长券关联
 * Created by wml on 2017/5/19.
 */
public class CouponDeferredSubscription {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
//    @CmsColumn(name="用户id")
    Long userId;
//    @CmsColumn(name="期刊订单id")
    Long orderId;
//    @CmsColumn(name="用户优惠券id")
    @Column(name="couponId")
    Long couponUserId;
//    @JsonIgnore
//    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId",insertable = true, updatable = true)
//    @NotFound(action= NotFoundAction.IGNORE)
//    User user;


//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "couponId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    CouponDeferredUser couponDeferredUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCouponUserId() {
        return couponUserId;
    }

    public void setCouponUserId(Long couponUserId) {
        this.couponUserId = couponUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CouponDeferredUser getCouponDeferredUser() {
        return couponDeferredUser;
    }

    public void setCouponDeferredUser(CouponDeferredUser couponDeferredUser) {
        this.couponDeferredUser = couponDeferredUser;
    }
}
