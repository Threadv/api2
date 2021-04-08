package com.ifenghui.storybookapi.app.transaction.response;

/**
 * Created by jia on 2017/1/9.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.api.response.base.ApiStatus;

public class EditShoppingCartResponse extends ApiResponse {

    ApiStatus rule;
    Integer count;
    Integer sumPrice ;

    public ApiStatus getRule() {
        return rule;
    }

    public void setRule(ApiStatus rule) {
        this.rule = rule;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Integer sumPrice) {
        this.sumPrice = sumPrice;
    }

}
