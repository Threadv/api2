package com.ifenghui.storybookapi.adminapi.controlleradmin.code.service;

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessage;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.Ads2;
import com.ifenghui.storybookapi.app.app.response.IndexAds;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by wml on 2016/12/27.
 */
public interface SmsMessageService {

    /**
     * 获得列表
     * @param smsMessage
     * @param pageRequest
     * @return
     */
    Page<SmsMessage> findAllSmsMessage(SmsMessage smsMessage, PageRequest pageRequest);

    SmsMessage findOne(Integer id);

    SmsMessage add(SmsMessage smsMessage);

    SmsMessage update(SmsMessage smsMessage);

}
