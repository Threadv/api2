package com.ifenghui.storybookapi.app.shop.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.RechargePayStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 商城订单回调表
 */
@Entity
@Table(name = "story_shop_pay_callback_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShopPayCallBackRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String callbackMsg;

    Integer orderId;

    //支付类型
    Integer type;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCallbackMsg() {
        return callbackMsg;
    }

    public void setCallbackMsg(String callbackMsg) {
        this.callbackMsg = callbackMsg;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(RechargePayStyle type) {
        this.type = type.getId();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
