package com.ifenghui.storybookapi.app.shop.response;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsPrice;

import java.util.List;

public class ShopPriceListResponse extends BaseResponse {


    List<ShopGoodsPrice> priceList;

    public List<ShopGoodsPrice> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<ShopGoodsPrice> priceList) {
        this.priceList = priceList;
    }
}
