package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.goods.Goods;

/**
 * Created by wml on 2016/12/30.
 */
public class GetLogisticsResponse extends ApiResponse {

    LogisticsNew logistics;
    Goods goods;

    public LogisticsNew getLogistics() {
        return logistics;
    }

    public void setLogistics(LogisticsNew logistics) {
        this.logistics = logistics;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
