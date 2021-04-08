package com.ifenghui.storybookapi.app.transaction.entity;

/**
 * Created by wml on 2017/2/16.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.style.RechargeStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单后续操作表
 */
@Entity
@Table(name="story_pay_after_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name = "后置操作订单管理",addAble = false,editAble = false,deleteAble = false)
public class PayAfterOrder {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
//    @CmsColumn(name="id")
    Long id;
//    @CmsColumn(name="用户id")
    Long userId;
//    @CmsColumn(name="充值订单id")
    Long orderId;
//    @CmsColumn(name="对应类型订单号（1购买/2订阅）")
    Long payOrderId;
//    @CmsColumn(name="类型（1购买/2订阅）")
    Integer type;
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId",insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    PayRechargeOrder rechargeOrder;


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

    public Long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
        this.payOrderId = payOrderId;
    }

    public Integer getType() {
        return type;
    }

//    public void setType(Integer type) {
//        this.type = type;
//    }

    public void setRechargeStyle(RechargeStyle rechargeStyle){
        this.type=rechargeStyle.getId();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PayRechargeOrder getRechargeOrder() {
        return rechargeOrder;
    }

    public void setRechargeOrder(PayRechargeOrder rechargeOrder) {
        this.rechargeOrder = rechargeOrder;
    }
}
