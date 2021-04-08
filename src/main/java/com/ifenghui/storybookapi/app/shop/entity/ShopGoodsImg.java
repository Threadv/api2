package com.ifenghui.storybookapi.app.shop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;

/**
 * 商城商品图片表
 */
@Entity
@Table(name = "story_shop_goods_img")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShopGoodsImg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    Integer goodsId;

    @JsonIgnore
    String imgPath;

    Integer orderBy;

    @JsonProperty("imgPathUrl")
    public String getImgPathUrl() {
        return MyEnv.env.getProperty("shoposs.url") + "goods/" + imgPath;
    }

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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
