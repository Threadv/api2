package com.ifenghui.storybookapi.app.presale.service.impl;



import com.ifenghui.storybookapi.app.presale.dao.PreSaleLogDao;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleLog;
import com.ifenghui.storybookapi.app.presale.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LogServiceImpl implements LogService {

    @Autowired
    PreSaleLogDao preSaleLogDao;


    @Override
    public PreSaleLog add(PreSaleLog preSaleLog) {
        return preSaleLogDao.save(preSaleLog);
    }
}
