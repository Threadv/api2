package com.ifenghui.storybookapi.app.shop.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * 商城栏目
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShopGoodsCategory {

    Integer id;

    String name;

    Integer isShow;

    List<ShopGoods> shopGoodslist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public List<ShopGoods> getShopGoodslist() {
        return shopGoodslist;
    }

    public void setShopGoodslist(List<ShopGoods> shopGoodslist) {
        this.shopGoodslist = shopGoodslist;
    }
}
