package com.ifenghui.storybookapi.adminapi.controlleradmin.code.service;

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessage;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessageDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by wml on 2016/12/27.
 */
public interface SmsMessageDetailService {

    /**
     * 获得列表
     * @param smsMessageDetail
     * @param pageRequest
     * @return
     */
    Page<SmsMessageDetail> findAllSmsMessage(SmsMessageDetail smsMessageDetail, PageRequest pageRequest);

    List<SmsMessageDetail> findAllSmsMessageBy(SmsMessageDetail smsMessageDetail);

    SmsMessageDetail findOne(Integer id);

    SmsMessageDetail findOneByCode(String code);

    SmsMessageDetail add(SmsMessageDetail smsMessageDetail);

    SmsMessageDetail update(SmsMessageDetail smsMessageDetail);

}
