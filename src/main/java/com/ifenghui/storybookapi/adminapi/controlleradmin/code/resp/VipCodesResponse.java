package com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp;

/**
 * Created by jia on 2016/12/22.
 */

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.entity.Feedback;

import java.util.List;

public class VipCodesResponse extends ApiResponse {
    List<CodeTypeItem> codeTypeItems;

    public List<CodeTypeItem> getCodeTypeItems() {
        return codeTypeItems;
    }

    public void setCodeTypeItems(List<CodeTypeItem> codeTypeItems) {
        this.codeTypeItems = codeTypeItems;
    }
}
