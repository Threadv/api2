package com.ifenghui.storybookapi.app.transaction.entity.coupon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户的代金券的关联
 */
@Entity
@Table(name="story_coupon_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="用户优惠券管理",addAble = false)
/**
 * Created by wml on 2017/5/18
 */
public class CouponUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Column
//    @CmsColumn(name="优惠券id")
    Long couponId;
    @Column
//    @CmsColumn(name="用户id")
    Long userId;

    @Column
//    @CmsColumn(name="状态",inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class)
    Integer status;
    @Column
//    @CmsColumn(name="是否已读（0未读1已读）")
    Integer isView;

    @Column
//    @CmsColumn(name="手机")
    String phone;

    @Transient
//    @CmsColumn(name="优惠券名称")
    String name;

    @Transient
//    @CmsColumn(name="优惠券描述")
    String content;

    @Transient
//    @CmsColumn(name="优惠券金额")
    Integer amount;

    @Transient
    Integer maxAmount;

    @Transient
//    @CmsColumn(name="是否可用（1可用）")
    Integer isUsable;

    String channel;

    @Column
//    @CmsColumn(name="有效期")
    Date endTime;

    @Column
//    @CmsColumn(name="创建时间")
    Date createTime;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "couponId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    Coupon coupon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsView() {
        return isView;
    }

    public void setIsView(Integer isView) {
        this.isView = isView;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(Integer isUsable) {
        this.isUsable = isUsable;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
