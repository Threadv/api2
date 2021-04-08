package com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp;

/**
 * Created by jia on 2016/12/22.
 */

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessage;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

public class SmsMessagesResponse extends ApiPageResponse {
    List<SmsMessage> smsMessages;

    public List<SmsMessage> getSmsMessages() {
        return smsMessages;
    }

    public void setSmsMessages(List<SmsMessage> smsMessages) {
        this.smsMessages = smsMessages;
    }
}
