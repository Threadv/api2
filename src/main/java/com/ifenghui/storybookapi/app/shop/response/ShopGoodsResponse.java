package com.ifenghui.storybookapi.app.shop.response;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoods;

public class ShopGoodsResponse extends BaseResponse {

    Integer svipStatus;

    Integer starCount;

    ShopGoods goods;

    public Integer getSvipStatus() {
        return svipStatus;
    }

    public void setSvipStatus(Integer svipStatus) {
        this.svipStatus = svipStatus;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public ShopGoods getGoods() {
        return goods;
    }

    public void setGoods(ShopGoods goods) {
        this.goods = goods;
    }
}
