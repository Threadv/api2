package com.ifenghui.storybookapi.app.presale.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 物流信息
 */
@Entity
@Table(name = "story_pre_sale_gift")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PreSaleGift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    //userId
    Integer userId;

    //payId
    Integer payId;

    //商品Id
    Integer goodsId;

    //活动Id
    Integer activityId;

    Integer userType;

    //礼品名称
    String name;

    //收货人
    String receiver;

    //电话
    String phone;

    //地址
    String address;

    //领取状态
    Integer status;

    //创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    //快递公司
    String expressCompanyName;

    //发货状态
    Integer expressStatus;

    //快递单号
    String expressCode;

    @Transient
    PreSaleGoods preSaleGoods;
    @Transient
    Activity activity;



    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getExpressCompanyName() {
        return expressCompanyName;
    }

    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName;
    }

    public Integer getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(Integer expressStatus) {
        this.expressStatus = expressStatus;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public PreSaleGoods getPreSaleGoods() {
        return preSaleGoods;
    }

    public void setPreSaleGoods(PreSaleGoods preSaleGoods) {
        this.preSaleGoods = preSaleGoods;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}