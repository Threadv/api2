package com.ifenghui.storybookapi.app.presale.response;




import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;

import java.util.List;

public class PreSalePaysResponse extends ApiPageResponse {

    List<PreSalePay> preSalePays;


    public List<PreSalePay> getPreSalePays() {
        return preSalePays;
    }

    public List<PreSalePay> getPreSalePay() {
        return preSalePays;
    }

    public void setPreSalePays(List<PreSalePay> preSalePays) {
        this.preSalePays = preSalePays;
    }
}
