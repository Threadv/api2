package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGoods;

import java.util.List;

public class PreSaleGoodsListResponse extends ApiPageResponse {

    List<PreSaleGoods> preSaleGoodsList;

    public List<PreSaleGoods> getPreSaleGoodsList() {
        return preSaleGoodsList;
    }

    public void setPreSaleGoodsList(List<PreSaleGoods> preSaleGoodsList) {
        this.preSaleGoodsList = preSaleGoodsList;
    }
}
