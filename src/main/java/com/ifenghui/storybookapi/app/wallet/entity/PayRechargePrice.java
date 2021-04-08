package com.ifenghui.storybookapi.app.wallet.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.WalletStyle;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name="story_pay_recharge_price")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/**
 * 充值价格表
 */
public class PayRechargePrice implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    /**
     * 价格
     */
    @Column
    Integer price;

    /**
     * 状态
     */
    @Column
    Integer status;

    /**
     * 排序
     */
    @Column
    Integer orderBy;

    /**
     * 内购标记
     */
    @Column
    String iap;


    @JsonIgnore
//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE,defaultValueType = CmsDefaultValueType.DATE_NOW,isShowToAdd = false,isShowToEdit = false)
    Date createTime;

    Integer type;

    @Transient
    String intro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public String getIap() {
        return iap;
    }

    public void setIap(String iap) {
        this.iap = iap;
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

    public void setWalletStyle(WalletStyle walletStyle) {
        this.type = walletStyle.getId();
    }

    public String getIntro() {
        if(this.id.equals(5L)) {
            intro = "返50元代金券";
        } else if(this.id.equals(6L)){
            intro = "余额支付再享9折";
        } else if(this.id.equals(7L)) {
            intro = "余额支付再享8折";
        } else if(this.id.equals(8L)){
            intro = "余额支付再享7折";
        } else {
            intro = "";
        }
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
