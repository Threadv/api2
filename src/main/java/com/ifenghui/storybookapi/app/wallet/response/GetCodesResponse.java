package com.ifenghui.storybookapi.app.wallet.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifenghui.storybookapi.app.transaction.response.FormatRequestCodesDataResponse;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

/**
 * Created by wml on 2016/12/30.
 */
public class GetCodesResponse extends ApiResponse {
    @JsonIgnore
    List<FormatRequestCodesDataResponse> body;

    List<FormatRequestCodesDataResponse> codes;

    public List<FormatRequestCodesDataResponse> getCodes() {
        return codes;
    }

    public void setCodes(List<FormatRequestCodesDataResponse> codes) {
        this.codes = codes;
    }

    public List<FormatRequestCodesDataResponse> getBody() {
        return body;
    }

    public void setBody(List<FormatRequestCodesDataResponse> body) {
        this.body = body;
    }
}
