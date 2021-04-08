package com.ifenghui.storybookapi.app.user.service.impl;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.app.user.dao.UserAccountDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.app.user.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Component
public class UserAccountServiceImpl implements UserAccountService {


    @Autowired
    UserDao userDao;

    @Autowired
    UserAccountDao userAccountDao;

    @Override
    public UserAccount getUserAccountByUserIdAndSrcType(Long userId, Integer srcType) {

        UserAccount userAccount = userAccountDao.getOneByUserIdAndSrcType(userId,srcType);
        return userAccount;
    }

    @Override
    public UserAccount getUserAccountBySrcId(String srcId) {
        return userAccountDao.findBySrcId(srcId);
    }
}
