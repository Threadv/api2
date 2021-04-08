package com.ifenghui.storybookapi.app.presale.service.impl;

import com.ifenghui.storybookapi.app.presale.dao.PreSalePayCallBackDao;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePayCallBack;
import com.ifenghui.storybookapi.app.presale.service.PreSalePayCallBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class PreSalePreSalePayCallBackServiceImpl implements PreSalePayCallBackService {


    @Autowired
    PreSalePayCallBackDao preSalePayCallBackDao;


    @Override
    public PreSalePayCallBack getPayCallbackRecordByPayId(Integer payId) {

        PreSalePayCallBack preSalePayCallBack = new PreSalePayCallBack();

        preSalePayCallBack.setPayId(payId);
        PreSalePayCallBack preSalePayCallBackDaoOne = preSalePayCallBackDao.findOne(Example.of(preSalePayCallBack));
        return preSalePayCallBackDaoOne;
    }

    @Override
    public PreSalePayCallBack addPayCallback(PreSalePayCallBack preSalePayCallBack) {

        PreSalePayCallBack save = preSalePayCallBackDao.save(preSalePayCallBack);
        return save;
    }

    @Override
    public Page<PreSalePayCallBack> findAll(PreSalePayCallBack payCallBack, PageRequest pageRequest) {
        return preSalePayCallBackDao.findAll(Example.of(payCallBack),pageRequest);
    }
}

