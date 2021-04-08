package com.ifenghui.storybookapi.app.shop.response;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopExpress;

public class ShopExpressResponse extends BaseResponse {


    ShopExpress express;

    public ShopExpress getExpress() {
        return express;
    }

    public void setExpress(ShopExpress express) {
        this.express = express;
    }
}
