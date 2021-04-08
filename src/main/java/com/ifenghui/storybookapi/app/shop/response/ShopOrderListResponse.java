package com.ifenghui.storybookapi.app.shop.response;


import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopOrder;

import java.util.List;

public class ShopOrderListResponse extends ApiPageResponse {

    //全部订单
    List<ShopOrder> shopOrderList;

    public List<ShopOrder> getShopOrderList() {
        return shopOrderList;
    }

    public void setShopOrderList(List<ShopOrder> shopOrderList) {
        this.shopOrderList = shopOrderList;
    }

}
