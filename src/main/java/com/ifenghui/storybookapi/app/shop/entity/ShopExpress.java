package com.ifenghui.storybookapi.app.shop.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.ExpressStatusStyle;
import com.ifenghui.storybookapi.style.ExpressStyle;
import com.ifenghui.storybookapi.style.GoodsCategoryType;
import com.ifenghui.storybookapi.style.OrderStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 商城物流表
 */
@Entity
@Table(name="story_shop_express")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShopExpress {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    Integer orderId;

    String receiver;

    String phone;

    String address;

    String area;

    Integer expressCompany;

    String expressCode;

    Integer expressStatus;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    Integer orderType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public void setExpress(ExpressStyle expressStyle) {
        this.expressCompany = expressStyle.getId();
    }

    public Integer getExpressCompany() {
        return expressCompany;
    }

    public ExpressStyle getExpressCompanyStyle() {
        if(expressCompany!=null){
            return  ExpressStyle.getById(expressCompany);
        }
        return null;
    }



    public void setExpressCompany(ExpressStyle expressStyle) {
        this.expressCompany = expressStyle.getId();
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public Integer getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(ExpressStatusStyle expressStatus) {
        this.expressStatus = expressStatus.getId();
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
