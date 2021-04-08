package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;

public class PreSaleGiftResponse extends ApiPageResponse {

    PreSaleGift preSaleGift;

    public PreSaleGift getPreSaleGift() {
        return preSaleGift;
    }

    public void setPreSaleGift(PreSaleGift preSaleGift) {
        this.preSaleGift = preSaleGift;
    }
}
