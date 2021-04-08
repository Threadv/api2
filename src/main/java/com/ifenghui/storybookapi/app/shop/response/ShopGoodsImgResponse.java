package com.ifenghui.storybookapi.app.shop.response;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsImg;

public class ShopGoodsImgResponse extends BaseResponse {


    ShopGoodsImg goodsImg;

    public ShopGoodsImg getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(ShopGoodsImg goodsImg) {
        this.goodsImg = goodsImg;
    }
}
