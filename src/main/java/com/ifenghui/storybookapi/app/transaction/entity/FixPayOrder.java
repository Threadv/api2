package com.ifenghui.storybookapi.app.transaction.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 总订单记录表
 */
@Entity
@Table(name="story_v_pay_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FixPayOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    Integer isDel;

    Date createTime;

    Integer type;

    Integer status;

    Integer userId;

    Integer orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
