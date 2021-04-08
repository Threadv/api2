package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;

import java.util.List;

public class PreSaleGiftListResponse extends ApiPageResponse {

    List<PreSaleGift> preSaleGiftList;

    public List<PreSaleGift> getPreSaleGiftList() {
        return preSaleGiftList;
    }

    public void setPreSaleGiftList(List<PreSaleGift> preSaleGiftList) {
        this.preSaleGiftList = preSaleGiftList;
    }
}
