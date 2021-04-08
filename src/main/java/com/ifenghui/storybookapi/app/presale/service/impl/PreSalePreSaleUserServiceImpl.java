package com.ifenghui.storybookapi.app.presale.service.impl;



import com.ifenghui.storybookapi.app.presale.dao.PreSaleUserDao;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleUser;
import com.ifenghui.storybookapi.app.presale.service.PreSaleUserService;
import com.ifenghui.storybookapi.util.weixin.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;


@Transactional
@Component
public class PreSalePreSaleUserServiceImpl implements PreSaleUserService {

    @Autowired
    PreSaleUserDao userDao;


    @Override
    public PreSaleUser getUserByUnionid(String unionid) {
        PreSaleUser user = userDao.getUserByUnionid(unionid);
        return user;
    }

    /**
     * 注册用户
     *
     * @param code
     * @return
     */
    @Override
    public PreSaleUser addUser(String code) throws Exception {

        Map<String, String> map = UserInfoUtil.wxUserInfo(code);
        PreSaleUser preSaleUser = new PreSaleUser();
        String nick = map.get("nickname");
        String openid = map.get("openid");
        String unionid = map.get("unionid");
        String headimgurl = map.get("headimgurl");

        PreSaleUser user = this.findUserByOpenId(openid);
        if (user != null) {
            return user;
        }
        preSaleUser.setNick(nick);
        preSaleUser.setOpenId(openid);
        preSaleUser.setUnionId(unionid);
        preSaleUser.setIcon(headimgurl);
        preSaleUser.setCreateTime(new Date());
        preSaleUser.setUserType(2);
        PreSaleUser save = userDao.save(preSaleUser);
        return save;
    }

    @Override
    public PreSaleUser addUser(PreSaleUser preSaleUser) {
        PreSaleUser findUser=userDao.getUserByUnionid(preSaleUser.getUnionId());
        if(findUser!=null){
            if(!preSaleUser.getNick().equals(findUser.getNick())||!preSaleUser.getIcon().equals(findUser.getIcon())){
                findUser.setIcon(preSaleUser.getIcon());
                findUser.setNick(preSaleUser.getNick());
                findUser=userDao.save(findUser);
            }
            return findUser;
        }
        preSaleUser.setCreateTime(new Date());
        preSaleUser.setUserType(2);
        PreSaleUser save = userDao.save(preSaleUser);
        return save;
    }

    /**
     * 通过openId查询user
     *
     * @param openid
     * @return
     */
    @Override
    public PreSaleUser findUserByOpenId(String openid) {

        PreSaleUser preSaleUser = new PreSaleUser();
        preSaleUser.setOpenId(openid);
        PreSaleUser user = userDao.findOne(Example.of(preSaleUser));
        if (user != null) {
            return user;
        }
        return null;
    }



    @Override
    public PreSaleUser findUserById(Integer userId) {

        PreSaleUser user = userDao.findOne(userId);
        return user;
    }

    @Override
    public Page<PreSaleUser> findAll(PreSaleUser preSaleUser, PageRequest pageRequest) {
        return userDao.findAll(Example.of(preSaleUser),pageRequest);
    }
}
