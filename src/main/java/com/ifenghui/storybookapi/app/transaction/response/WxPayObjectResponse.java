package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import net.sf.json.JSONObject;

public class WxPayObjectResponse extends ApiResponse {

    JSONObject wxPayObject;

    public JSONObject getWxPayObject() {
        return wxPayObject;
    }

    public void setWxPayObject(JSONObject wxPayObject) {
        this.wxPayObject = wxPayObject;
    }
}
