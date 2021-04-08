package com.ifenghui.storybookapi.app.transaction.entity.coupon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


import javax.persistence.*;
import java.util.Date;

/**
 * 赠阅券和用户关联表
 */
@Entity
@Table(name="story_coupon_deferred_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="用户赠阅券管理",addAble = false)
/**
 * Created by wml on 2017/6/22
 */
public class CouponDeferredUser {

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

    @Transient
//    @CmsColumn(name="优惠券名称")
    String name;

    @Transient
//    @CmsColumn(name="优惠券描述")
    String content;

    @Transient
//    @CmsColumn(name="赠阅券优惠月数")
    Integer validTime;

    @Transient
//    @CmsColumn(name="是否可用（1可用）")
    Integer isUsable;

    @JsonIgnore
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
    CouponDeferred couponDeferred;

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

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
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

    public Integer getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(Integer isUsable) {
        this.isUsable = isUsable;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public CouponDeferred getCouponDeferred() {
        return couponDeferred;
    }

    public void setCouponDeferred(CouponDeferred couponDeferred) {
        this.couponDeferred = couponDeferred;
    }
}
