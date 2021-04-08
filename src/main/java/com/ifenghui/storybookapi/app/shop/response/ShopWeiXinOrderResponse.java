package com.ifenghui.storybookapi.app.shop.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopOrder;
import net.sf.json.JSONObject;

public class ShopWeiXinOrderResponse extends BaseResponse {

    ShopOrder shopOrder;

    JSONObject jsonObject;

    public ShopOrder getShopOrder() {
        return shopOrder;
    }

    public void setShopOrder(ShopOrder shopOrder) {
        this.shopOrder = shopOrder;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
