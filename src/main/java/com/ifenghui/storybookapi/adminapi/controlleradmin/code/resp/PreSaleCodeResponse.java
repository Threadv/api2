package com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp;

/**
 * Created by jia on 2016/12/22.
 */

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;

import java.util.List;

public class PreSaleCodeResponse extends ApiPageResponse {
    PreSaleCode preSaleCode;

    public PreSaleCode getPreSaleCode() {
        return preSaleCode;
    }

    public void setPreSaleCode(PreSaleCode preSaleCode) {
        this.preSaleCode = preSaleCode;
    }
}
