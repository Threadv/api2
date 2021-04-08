package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;

public class PreSaleCodeResponse extends ApiPageResponse {

    PreSaleCode code;

    public PreSaleCode getCode() {
        return code;
    }

    public void setCode(PreSaleCode code) {
        this.code = code;
    }
}
