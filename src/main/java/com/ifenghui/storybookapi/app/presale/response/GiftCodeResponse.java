package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;

import java.util.List;

public class GiftCodeResponse extends ApiPageResponse {

    List<PreSaleGift> giftList;
    List<PreSaleCode> codeList;


    public List<PreSaleGift> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<PreSaleGift> giftList) {
        this.giftList = giftList;
    }

    public List<PreSaleCode> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<PreSaleCode> codeList) {
        this.codeList = codeList;
    }
}
