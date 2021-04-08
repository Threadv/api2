package com.ifenghui.storybookapi.adminapi.manager.service.impl;


import com.ifenghui.storybookapi.adminapi.manager.dao.ManagerTokenDao;
import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerToken;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManagerTokenServiceImpl implements ManagerTokenService {

    @Autowired
    ManagerTokenDao managerTokenDao;

    @Override
    public ManagerToken save(ManagerToken managerToken) {
        return managerTokenDao.save(managerToken);
    }

    @Override
    public ManagerToken findOne(Integer id) {
        return managerTokenDao.findOne(id);
    }

    @Override
    public ManagerToken findOneByToken(String token) {
        return managerTokenDao.findOneByToken(token);
    }

    @Override
    public ManagerToken logout(String token) {
        ManagerToken managerToken=managerTokenDao.findOneByToken(token);
        managerTokenDao.delete(managerToken);
        return null;
    }
}
