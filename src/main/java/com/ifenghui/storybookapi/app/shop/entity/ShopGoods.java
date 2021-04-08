package com.ifenghui.storybookapi.app.shop.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.GoodsCategoryType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 商城商品表
 */
@Entity
@Table(name = "story_shop_goods")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShopGoods {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    Integer categoryId;

    @JsonIgnore
    String goodsImg;

    String goodsIntro;

    String goodsName;

    Integer type;

    String value;

    Integer status;

    Integer orderBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    String intro;

    @Transient
    List<ShopGoodsPrice> shopGoodsPriceList;

    @Transient
    List<ShopGoodsPrice> shopGoodsPriceListAll;

    @Transient
    List<ShopGoodsImg> shopGoodsImgList;

    @JsonProperty("goodsImgPath")
    public String getGoodsImgPath() {
        return MyEnv.env.getProperty("shoposs.url") + "goods/" + goodsImg;
    }


    public List<ShopGoodsPrice> getShopGoodsPriceList() {
        return shopGoodsPriceList;
    }

    public void setShopGoodsPriceList(List<ShopGoodsPrice> shopGoodsPriceList) {
        this.shopGoodsPriceList = shopGoodsPriceList;
    }

    public List<ShopGoodsPrice> getShopGoodsPriceListAll() {
        return shopGoodsPriceListAll;
    }

    public void setShopGoodsPriceListAll(List<ShopGoodsPrice> shopGoodsPriceListAll) {
        this.shopGoodsPriceListAll = shopGoodsPriceListAll;
    }

    public List<ShopGoodsImg> getShopGoodsImgList() {
        return shopGoodsImgList;
    }

    public void setShopGoodsImgList(List<ShopGoodsImg> shopGoodsImgList) {
        this.shopGoodsImgList = shopGoodsImgList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public GoodsCategoryType getCategory() {
        if (categoryId != null) {
            return GoodsCategoryType.getById(categoryId);
        }
        return null;
    }
//    public void setCategoryId(Integer categoryId) {
//        this.categoryId = categoryId;
//    }

    public void setCategory(GoodsCategoryType goodsCategoryType) {
        this.categoryId = goodsCategoryType.getId();
    }

    public String getGoodsImg() {
         return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
