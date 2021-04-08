package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleShareRecord;

public class PreSaleShareRecordResponse extends ApiPageResponse {

    PreSaleShareRecord shareRecord;

    public PreSaleShareRecord getShareRecord() {
        return shareRecord;
    }

    public void setShareRecord(PreSaleShareRecord shareRecord) {
        this.shareRecord = shareRecord;
    }
}
