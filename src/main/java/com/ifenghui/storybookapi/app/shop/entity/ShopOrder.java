package com.ifenghui.storybookapi.app.shop.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.OrderStatusStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 商城订单表
 */
@Entity
@Table(name = "story_shop_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    Integer userId;

    Integer goodsId;

    Integer goodsNumber;

    Integer totalAmount;

    Integer totalStar;

    Integer status;

    Integer priceId;

    Integer buyType;

    Integer isDel;

    Integer isTest;

    Integer express_status;

    String channel;

    String remark;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date successTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    @Transient
    ShopGoodsPrice shopGoodsPrice;

    @Transient
    String orderCode;

    @Transient
    ShopGoods shopGoods;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Transient
    Date expireTime;

    @Transient
    Long timeCount;

    public Long getTimeCount() throws Exception{

        Long now = System.currentTimeMillis();
        long after = this.getExpireTime().getTime();
        Long time = 0L;
        if (after > now) {
            time = after - now;
        }
        return timeCount = time;
    }

    public Date getExpireTime() throws Exception {

        long dateTime = this.getCreateTime().getTime() + 15 * 60 * 1000;
        return expireTime = new Date(dateTime);
    }

    public ShopGoods getShopGoods() {
        return shopGoods;
    }

    public void setShopGoods(ShopGoods shopGoods) {
        this.shopGoods = shopGoods;
    }

    public ShopGoodsPrice getShopGoodsPrice() {
        return shopGoodsPrice;
    }

    public void setShopGoodsPrice(ShopGoodsPrice shopGoodsPrice) {
        this.shopGoodsPrice = shopGoodsPrice;
    }

    public String getOrderCode() {

        return "shop_" + orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalStar() {
        return totalStar;
    }

    public void setTotalStar(Integer totalStar) {
        this.totalStar = totalStar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(OrderStatusStyle status) {
        this.status = status.getId();
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
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

    public Integer getExpress_status() {
        return express_status;
    }

    public void setExpress_status(Integer express_status) {
        this.express_status = express_status;
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

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
