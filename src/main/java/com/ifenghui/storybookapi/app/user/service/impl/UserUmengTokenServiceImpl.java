package com.ifenghui.storybookapi.app.user.service.impl;

import com.ifenghui.storybookapi.app.user.dao.UserUmengTokenDao;
import com.ifenghui.storybookapi.app.user.entity.UserUmengToken;
import com.ifenghui.storybookapi.app.user.service.UserUmengTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserUmengTokenServiceImpl implements UserUmengTokenService {

    @Autowired
    UserUmengTokenDao userUmengTokenDao;

    @Override
    public UserUmengToken addUserUmengToken(Integer userId, String umengToken, String userAgent, Integer isSend) {
        UserUmengToken userUmengToken = new UserUmengToken();
        userUmengToken.setUserId(userId);
        userUmengToken.setUmengToken(umengToken);
        userUmengToken.setUserAgent(userAgent);
        userUmengToken.setIsSend(isSend);
        userUmengToken.setCreateTime(new Date());
        return userUmengTokenDao.save(userUmengToken);
    }

    @Override
    public UserUmengToken updateUserUmengToken(Integer userId, String umengToken, String userAgent, Integer isSend) {
        UserUmengToken userUmengToken = userUmengTokenDao.findUserUmengTokenByUmengToken(umengToken);
        if(userUmengToken != null){
            userUmengToken.setUserId(userId);
            userUmengTokenDao.save(userUmengToken);
        } else {
            userUmengToken = this.addUserUmengToken(userId, umengToken, userAgent, isSend);
        }
        return userUmengToken;
    }
}
