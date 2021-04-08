package com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp;

/**
 * Created by jia on 2016/12/22.
 */

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessageDetail;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;

import java.util.List;

public class PreSaleCodesResponse extends ApiPageResponse {
    List<PreSaleCode> preSaleCodes;

    public List<PreSaleCode> getPreSaleCodes() {
        return preSaleCodes;
    }

    public void setPreSaleCodes(List<PreSaleCode> preSaleCodes) {
        this.preSaleCodes = preSaleCodes;
    }
}
