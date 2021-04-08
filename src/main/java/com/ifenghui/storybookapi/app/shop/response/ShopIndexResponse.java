package com.ifenghui.storybookapi.app.shop.response;


import com.ifenghui.storybookapi.app.app.entity.Ads2;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsCategory;

import java.util.List;

public class ShopIndexResponse extends BaseResponse {

    Integer starCount;

    List<Ads2> adsList;

    List<ShopGoodsCategory> shopGoodsCategoryList;

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public List<Ads2> getAdsList() {
        return adsList;
    }

    public void setAdsList(List<Ads2> adsList) {
        this.adsList = adsList;
    }

    public List<ShopGoodsCategory> getShopGoodsCategoryList() {
        return shopGoodsCategoryList;
    }

    public void setShopGoodsCategoryList(List<ShopGoodsCategory> shopGoodsCategoryList) {
        this.shopGoodsCategoryList = shopGoodsCategoryList;
    }
}
