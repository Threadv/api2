package com.ifenghui.storybookapi.app.system.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.ifenghui.storybookapi.style.CheckCodeStyle;

import java.util.Map;

public interface MessageService {

    /**
     * 发送短信
     */

    public SendSmsResponse sendSms(String phone, String templateCode, Map<String, String> map);

}
