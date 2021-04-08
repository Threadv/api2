package com.ifenghui.storybookapi.app.presale.response;


import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGoods;



public class PreSaleGoodsResponse extends ApiPageResponse {

    PreSaleGoods preSaleGoods;

    public PreSaleGoods getPreSaleGoods() {
        return preSaleGoods;
    }

    public void setPreSaleGoods(PreSaleGoods preSaleGoods) {
        this.preSaleGoods = preSaleGoods;
    }
}
