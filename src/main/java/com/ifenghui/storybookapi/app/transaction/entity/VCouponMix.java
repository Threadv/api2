package com.ifenghui.storybookapi.app.transaction.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 优惠券统一记录表（视图）
 */
@Entity
@Table(name="story_v_coupon_mix")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="用户过期券管理",addAble = false)
/**
 * Created by wml on 2017/6/27
 */
public class VCouponMix {

//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    @Id
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
//    @CmsColumn(name="类型（1 coupon_user,2 coupon_de.._user）",inputType = CmsInputType.RADIO,dataType = CmsDataType.MAP,enumClassName = PublishStatus.class)
    Integer type;

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
//    @CmsColumn(name="可订阅多长时间")
    Integer validTime;

    @Column
//    @CmsColumn(name="有效期")
    Date endTime;

    @Column
//    @CmsColumn(name="创建时间")
    Date createTime;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}
