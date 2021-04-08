package com.ifenghui.storybookapi.app.user.service.impl;

import com.ifenghui.storybookapi.app.user.dao.UserInfoDao;
import com.ifenghui.storybookapi.app.user.entity.UserInfo;
import com.ifenghui.storybookapi.app.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional(rollbackFor = Exception.class)
@Component
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoDao userInfoDao;


    @Transactional
    @Override
    public Void addUserInfo(Long userId,String name, String phone,String addr){

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setName(name);
        userInfo.setPhone(phone);
        userInfo.setAddr(addr);
        userInfo.setCreateTime(new Date());
        userInfoDao.save(userInfo);
        return null;
    }
    @Override
    public UserInfo getUserInfo(Long userId){

        UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);

        return userInfo;
    }

}
