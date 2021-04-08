package com.ifenghui.storybookapi.app.user.service.impl;

import com.ifenghui.storybookapi.app.user.dao.UserRelateDao;
import com.ifenghui.storybookapi.app.user.entity.UserRelate;
import com.ifenghui.storybookapi.app.user.service.UserRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: api2
 * @description:
 * @author: wjs
 * @create: 2018-12-13 09:30
 **/
@Service
public class UserRelateServiceImpl implements UserRelateService {

    @Autowired
    UserRelateDao userRelateDao;

    @Override
    public UserRelate getUserRelateByPhone(String phone) {
        return userRelateDao.findByPhone(phone);
    }

    @Override
    public UserRelate getUserRelateByUnionid(String unionid) {
        return userRelateDao.findByUnionid(unionid);
    }
}
