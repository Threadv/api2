package com.ifenghui.storybookapi.adminapi.controlleradmin.code.service.impl;

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.dao.SmsMessageDao;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessage;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.service.SmsMessageService;
import com.ifenghui.storybookapi.app.app.dao.AdDao;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.Ads2;
import com.ifenghui.storybookapi.app.app.response.IndexAds;
import com.ifenghui.storybookapi.app.app.service.AdService;
import com.ifenghui.storybookapi.app.transaction.service.PaySubscriptionPriceService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wml on 2016/12/27.
 */
@Transactional
@Component
public class SmsMessageServiceImpl implements SmsMessageService {

    @Autowired
    SmsMessageDao smsMessageDao;


    @Override
    public Page<SmsMessage> findAllSmsMessage(SmsMessage smsMessage, PageRequest pageRequest) {
        return smsMessageDao.findAll(Example.of(smsMessage),pageRequest);
    }

    @Override
    public SmsMessage findOne(Integer id) {
        return smsMessageDao.findOne(id);
    }

    @Override
    public SmsMessage add(SmsMessage smsMessage) {
        return smsMessageDao.save(smsMessage);
    }

    @Override
    public SmsMessage update(SmsMessage smsMessage) {
        return smsMessageDao.save(smsMessage);
    }
}
