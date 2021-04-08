package com.ifenghui.storybookapi.app.wallet.response;

/**
 * Created by w
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.wallet.entity.PayCallbackRecord;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargePrice;

import java.util.List;

public class PayCallbackRecordResponse extends ApiResponse {
    PayCallbackRecord payCallbackRecord;

    public PayCallbackRecord getPayCallbackRecord() {
        return payCallbackRecord;
    }

    public void setPayCallbackRecord(PayCallbackRecord payCallbackRecord) {
        this.payCallbackRecord = payCallbackRecord;
    }


}
