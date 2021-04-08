package com.ifenghui.storybookapi.app.presale.service.impl;



import com.ifenghui.storybookapi.app.presale.exception.PreSaleActivityNotTokenException;
import com.ifenghui.storybookapi.app.presale.service.YiZhiActivityUserService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.dao.UserTokenDao;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Component
public class YiZhiActivityUserServiceImpl implements YiZhiActivityUserService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserTokenDao userTokenDao;


    @Override
    public List<UserToken> getValidUserTokenListByUserId(long userId) {
        List<UserToken> userTokenList = userTokenDao.findValidUserTokensByUserId(userId,new Sort(Sort.Direction.DESC,"id"));
        return userTokenList;
    }

    @Override
    public UserToken getUserIdByToken(String token){
        return userTokenDao.findOneByToken(token);
    }

    @Override
    public Integer checkAndGetCurrentUserId(String token) throws PreSaleActivityNotTokenException {
        UserToken userToken=this.getUserIdByToken(token);
        if(userToken==null){
            throw new PreSaleActivityNotTokenException();
        }
        if(userToken.getUser()==null){
            throw new PreSaleActivityNotTokenException("token相关用户不存在");
        }
        return userToken.getUser().getId().intValue();

    }
}
