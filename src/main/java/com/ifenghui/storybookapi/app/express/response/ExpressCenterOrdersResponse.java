package com.ifenghui.storybookapi.app.express.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;

import java.util.List;

public class ExpressCenterOrdersResponse extends ApiPageResponse{
    List<ExpressCenterOrder> expressCenterOrders;
    String phone;

    public List<ExpressCenterOrder> getExpressCenterOrders() {
        return expressCenterOrders;
    }

    public void setExpressCenterOrders(List<ExpressCenterOrder> expressCenterOrders) {
        this.expressCenterOrders = expressCenterOrders;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
