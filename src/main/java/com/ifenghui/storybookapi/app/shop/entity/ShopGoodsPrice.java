package com.ifenghui.storybookapi.app.shop.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.GoodsPriceStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 商城商品价格表
 */
@Entity
@Table(name = "story_shop_goods_price")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShopGoodsPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    Integer goodsId;

    Integer price;

    Integer starNumber;

    Integer type;

    Integer orderBy;

    Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStarNumber() {
        return starNumber;
    }

    public void setStarNumber(Integer starNumber) {
        this.starNumber = starNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(GoodsPriceStyle type) {
        this.type = type.getId();
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
