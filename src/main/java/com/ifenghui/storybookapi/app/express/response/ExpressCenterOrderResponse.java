package com.ifenghui.storybookapi.app.express.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;

import java.util.List;

public class ExpressCenterOrderResponse extends ApiResponse {
    ExpressCenterOrder expressCenterOrder;


    public ExpressCenterOrder getExpressCenterOrder() {
        return expressCenterOrder;
    }

    public void setExpressCenterOrder(ExpressCenterOrder expressCenterOrder) {
        this.expressCenterOrder = expressCenterOrder;
    }
}
