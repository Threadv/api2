package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.goods.Goods;

public class GetGoodsResponse extends ApiResponse {

    Goods goods;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
