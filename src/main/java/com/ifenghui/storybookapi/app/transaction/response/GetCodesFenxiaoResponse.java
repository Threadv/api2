package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.app.transaction.response.FormatRequestCodesDataResponse;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

/**
 * Created by wml on 2016/12/30.
 */
public class GetCodesFenxiaoResponse extends ApiResponse {

    List<FormatRequestCodesDataResponse> body;




    public List<FormatRequestCodesDataResponse> getBody() {
        return body;
    }

    public void setBody(List<FormatRequestCodesDataResponse> body) {
        this.body = body;
    }
}
