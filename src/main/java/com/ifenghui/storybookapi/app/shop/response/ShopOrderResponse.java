package com.ifenghui.storybookapi.app.shop.response;


import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopOrder;
import net.sf.json.JSONObject;

import java.util.List;

public class ShopOrderResponse extends BaseResponse {

    ShopOrder shopOrder;

    public ShopOrder getShopOrder() {
        return shopOrder;
    }

    public void setShopOrder(ShopOrder shopOrder) {
        this.shopOrder = shopOrder;
    }

}
