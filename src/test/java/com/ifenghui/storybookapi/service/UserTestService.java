package com.ifenghui.storybookapi.service;

import com.ifenghui.storybookapi.app.user.dao.UserAccountDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.dao.UserTokenDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import com.ifenghui.storybookapi.style.UserAccountStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserTestService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserTokenDao userTokenDao;

    @Autowired
    UserAccountDao userAccountDao;

    public User getUser(){
        String phone="138001382542";
        User user=userDao.findOneByPhone(phone);
        if(user!=null){
            return user;
        }
        user=new User.Builder().initAdd().setPhone(phone).build();
        user=userDao.save(user);



        return user;
    }

    public UserAccount getOtherUserAccount(){
//        String phone="138001382542";
        User user=this.getUser();
        UserAccount userAccount=userAccountDao.findBySrcId("abcde");
        if(userAccount!=null){
            return userAccount;
        }
        userAccount=new UserAccount();
        userAccount.setSrcId("abcde");
        userAccount.setAccountStyle(UserAccountStyle.GUEST);
        userAccount.setCreateTime(new Date());
        userAccount.setUserId(user.getId());
        return userAccountDao.save(userAccount);

//        return user;
    }


    public UserToken getUserToken(){


        User user=this.getUser();
        UserToken userToken=userTokenDao.findOneByToken("1234");
        if(userToken==null){
            userToken=new UserToken();
            userToken.setUserId(user.getId());
            userToken.setCreateTime(new Date());
            userToken.setToken("123");
            userToken.setRefreshToken("123");
            userToken.setDevice("");
            userToken.setUserAgent("ua");
            userToken.setDeviceUnique("123");
            userToken.setDeviceName("123");
            userToken.setAddr("");
            userToken.setIsValid(1);
            userToken=userTokenDao.save(userToken);
        }
        return userToken;
    }
}
