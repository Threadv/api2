package com.ifenghui.storybookapi.app.transaction.entity.abilityplan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.AbilityPlanStyle;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import javax.persistence.*;
import java.util.Date;

/**
 * @Date: 2018/11/8 18:24
 * @Description:
 */
@Entity
@Table(name="story_ability_plan_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AbilityPlanOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer planeType;

    Integer originalPrice;

    Integer couponAmount;

    Integer amount;

    String code;

    Date successTime;

    Integer status;

    Date createTime;

    Integer userDiscount;

    Integer type;

    Integer isDel;

    Integer isTest;

    String channel;

    String remark;

    Integer priceId;

    Integer onlineOnly;

    @Transient
    Integer mixOrderId;

    public Integer getMixOrderId() {
        return mixOrderId;
    }

    public void setMixOrderId(Integer mixOrderId) {
        this.mixOrderId = mixOrderId;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPlaneType() {
        return planeType;
    }

//    public void setPlaneType(Integer planeType) {
//        this.planeType = planeType;
//    }

    public void setAbilityPlanStyle(AbilityPlanStyle planeType) {
        this.planeType = planeType.getId();
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
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

    public Integer getUserDiscount() {
        return userDiscount;
    }

    public void setUserDiscount(Integer userDiscount) {
        this.userDiscount = userDiscount;
    }

    public Integer getType() {
        return type;
    }

    /*public void setType(Integer type) {
        this.type = type;
    }*/

    public void setPayStyle(OrderPayStyle type) {
        this.type = type.getId();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getOnlineOnly() {
        return onlineOnly;
    }

    public void setOnlineOnly(Integer onlineOnly) {
        this.onlineOnly = onlineOnly;
    }
}
