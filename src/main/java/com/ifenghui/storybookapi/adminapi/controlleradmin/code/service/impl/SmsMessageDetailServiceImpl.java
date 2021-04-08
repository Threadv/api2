package com.ifenghui.storybookapi.adminapi.controlleradmin.code.service.impl;

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.dao.SmsMessageDao;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.dao.SmsMessageDetailDao;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessage;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessageDetail;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.service.SmsMessageDetailService;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.service.SmsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by wml on 2016/12/27.
 */
@Transactional
@Component
public class SmsMessageDetailServiceImpl implements SmsMessageDetailService {

    @Autowired
    SmsMessageDetailDao smsMessageDetailDao;


    @Override
    public Page<SmsMessageDetail> findAllSmsMessage(SmsMessageDetail smsMessageDetail, PageRequest pageRequest) {
        return smsMessageDetailDao.findAll(Example.of(smsMessageDetail),pageRequest);
    }

    @Override
    public List<SmsMessageDetail> findAllSmsMessageBy(SmsMessageDetail smsMessageDetail) {
        return smsMessageDetailDao.findAll(Example.of(smsMessageDetail));
    }

    @Override
    public SmsMessageDetail findOne(Integer id) {
        return smsMessageDetailDao.findOne(id);
    }

    @Override
    public SmsMessageDetail findOneByCode(String code) {
        return smsMessageDetailDao.findOneByCode(code);
    }

    @Override
    public SmsMessageDetail add(SmsMessageDetail smsMessageDetail) {
//        SmsMessageDetail detailFind=new SmsMessageDetail();
//        detailFind.setPhone(smsMessageDetail.getPhone());
//        detailFind.setMessageId(smsMessageDetail.getMessageId());
//        long cont=smsMessageDetailDao.count(Example.of(detailFind));
//        if(cont>0){
//            return null;
//        }

        return smsMessageDetailDao.save(smsMessageDetail);
    }

    @Override
    public SmsMessageDetail update(SmsMessageDetail smsMessageDetail) {
        return smsMessageDetailDao.save(smsMessageDetail);
    }
}
