package com.ifenghui.storybookapi.app.wallet.entity;

/**
 * 回调信息
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.RechargePayStyle;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="story_pay_callback_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PayCallbackRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    /**
     * 返回信息
     */
    String callbackMsg;

    /**
     * 订单号
     */
    String orderCode;
    /**
     * 支付类型 具体见OrderPayStyle
     *
     */
    Integer type;

    Date createTime;

    Long orderId;


    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId",insertable = false, updatable = false)
    PayRechargeOrder payRechargeOrder;

    public PayCallbackRecord(){
        if(this.orderId==null){
            this.orderId=0L;
        }
        if (orderCode == null) {
            this.orderCode="";
        }


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCallbackMsg() {
        return callbackMsg;
    }

    public void setCallbackMsg(String callbackMsg) {
        this.callbackMsg = callbackMsg;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

//    public Integer getType() {
//        return type;
//    }

    /**
     * 设置支付方式
     * @param rechargePayStyle
     */
    public void setPayType(RechargePayStyle rechargePayStyle){
        this.type= rechargePayStyle.getId();
    }

    public RechargePayStyle getPayType(){
        if(this.type!=null){
            return RechargePayStyle.getById(type);
        }
        return null;
    }
//    public void setType(Integer type) {
//        this.type = type;
//    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PayRechargeOrder getPayRechargeOrder() {
        return payRechargeOrder;
    }

    public void setPayRechargeOrder(PayRechargeOrder payRechargeOrder) {
        if(payRechargeOrder!=null&&payRechargeOrder.getId()!=null){
            this.orderId=payRechargeOrder.getId();
        }
        this.payRechargeOrder = payRechargeOrder;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
