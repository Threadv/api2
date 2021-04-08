package com.ifenghui.storybookapi.app.presale.response;




import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePayCallBack;

import java.util.List;

public class PreSalePayCallbacksResponse extends ApiPageResponse {

    List<PreSalePayCallBack> preSalePayCallBacks;


    public List<PreSalePayCallBack> getPreSalePayCallBacks() {
        return preSalePayCallBacks;
    }

    public void setPreSalePayCallBacks(List<PreSalePayCallBack> preSalePayCallBacks) {
        this.preSalePayCallBacks = preSalePayCallBacks;
    }
}
