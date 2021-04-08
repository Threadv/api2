package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;
import net.sf.json.JSONObject;

public class PreSalePayResponse extends ApiPageResponse {

    PreSalePay preSalePay;

    JSONObject jsonObject;

    public PreSalePay getPreSalePay() {
        return preSalePay;
    }

    public void setPreSalePay(PreSalePay preSalePay) {
        this.preSalePay = preSalePay;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
