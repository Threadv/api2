package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;

import java.util.List;

public class PreSaleCodeListResponse extends ApiPageResponse {

    List<PreSaleCode> codeList;

    public List<PreSaleCode> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<PreSaleCode> codeList) {
        this.codeList = codeList;
    }
}
