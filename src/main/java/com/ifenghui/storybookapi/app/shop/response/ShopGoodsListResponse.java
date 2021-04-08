package com.ifenghui.storybookapi.app.shop.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoods;
import java.util.List;

public class ShopGoodsListResponse extends BaseResponse {


    String category;

    Integer isShow;

    List<ShopGoods> goodsList;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public List<ShopGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ShopGoods> goodsList) {
        this.goodsList = goodsList;
    }
}
