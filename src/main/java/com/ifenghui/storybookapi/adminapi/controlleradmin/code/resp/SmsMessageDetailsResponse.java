package com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp;

/**
 * Created by jia on 2016/12/22.
 */

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessage;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessageDetail;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;

import java.util.List;

public class SmsMessageDetailsResponse extends ApiPageResponse {
    List<SmsMessageDetail> smsMessageDetails;

    public List<SmsMessageDetail> getSmsMessageDetails() {
        return smsMessageDetails;
    }

    public void setSmsMessageDetails(List<SmsMessageDetail> smsMessageDetails) {
        this.smsMessageDetails = smsMessageDetails;
    }
}
