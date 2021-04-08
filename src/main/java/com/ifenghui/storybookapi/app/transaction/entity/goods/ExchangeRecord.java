package com.ifenghui.storybookapi.app.transaction.entity.goods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="story_exchange_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/**
 * 星乐园兑换记录
 */
@Deprecated
public class ExchangeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Long userId;

    Long goodsId;

    String goodsName;

    Integer buyNumber;

    Integer amount;

    Integer type;

    Integer status;

//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    @Transient
    String intro;

    @Transient
    String goodsImg;

    @Transient
    ExpressRecord expressRecord;

    @Transient
    List<ExchangeRecordVipcode> vipcodes;

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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(Integer buyNumber) {
        this.buyNumber = buyNumber;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public ExpressRecord getExpressRecord() {
        return expressRecord;
    }

    public void setExpressRecord(ExpressRecord expressRecord) {
        this.expressRecord = expressRecord;
    }

    public String getIntro() {
        if(intro == null){
            intro = "";
        }
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getGoodsImg() {
        if(goodsImg == null){
            goodsImg = "";
        }
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public List<ExchangeRecordVipcode> getVipcodes() {
        return vipcodes;
    }

    public void setVipcodes(List<ExchangeRecordVipcode> vipcodes) {
        this.vipcodes = vipcodes;
    }
}