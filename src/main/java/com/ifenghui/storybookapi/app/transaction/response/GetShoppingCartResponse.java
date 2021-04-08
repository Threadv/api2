package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.single.ShoppingTrolley;

import java.util.List;

/**
 * Created by wml on 2017/2/16.
 */
public class GetShoppingCartResponse extends ApiPageResponse {

    List<ShoppingTrolley> shoppingCarts;
    String discountRule;

    public List<ShoppingTrolley> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingTrolley> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public String getDiscountRule() {
        return discountRule;
    }

    public void setDiscountRule(String discountRule) {
        this.discountRule = discountRule;
    }
}
