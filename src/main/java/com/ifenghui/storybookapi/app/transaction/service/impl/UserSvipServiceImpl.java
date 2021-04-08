package com.ifenghui.storybookapi.app.transaction.service.impl;


import com.ifenghui.storybookapi.app.social.service.VipFriendCardService;
import com.ifenghui.storybookapi.app.transaction.dao.UserSvipDao;

import com.ifenghui.storybookapi.app.transaction.entity.vip.UserSvip;
import com.ifenghui.storybookapi.app.transaction.service.UserSvipService;

import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.User;

import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.SvipStyle;
import com.ifenghui.storybookapi.style.VipFriendCardTypeStyle;
import com.ifenghui.storybookapi.style.VipPriceStyle;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class UserSvipServiceImpl implements UserSvipService {

    @Autowired
    UserSvipDao userSvipDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    VipFriendCardService vipFriendCardService;

    //验证是否已经过期
    public boolean checkUserSvip(Long userId) {
        //查询数据
        List<UserSvip> userSvips = userSvipDao.findByUserIdAndTypeAndEndTime(userId,3,new Date());
        if(userSvips.size()>0){
            return true;
        }
        List<UserSvip> userSvipList = userSvipDao.findByUserIdAndTypeAndEndTime(userId, 4, new Date());
        if(userSvipList.size() > 0) {
            return true;
        }
        return false;
    }

    //验证是否已经过期
    public boolean resetUserVip(List<User> users) {
        //查询数据
        for(User user:users){
            boolean flag=this.checkUserSvip(user.getId());
            if(flag==false){
                user.setSvip(0);
                userDao.save(user);
            }
        }
        return false;
    }

    @Transactional
    @Override
    public UserSvip addUserSvip(Integer userId, SvipStyle svipStyle, Date startTime, Date endTime){
        UserSvip userSvip = new UserSvip();
        userSvip.setUserId(userId.longValue());
        userSvip.setCreateTime(new Date());
        userSvip.setEndTime(endTime);
        userSvip.setStartTime(startTime);
        userSvip.setSvipStyle(svipStyle);
        userSvipDao.save(userSvip);
        userService.changeUserSvipStyle(userId.longValue(),svipStyle);
        if(svipStyle.equals(SvipStyle.LEVEL_THREE) || svipStyle.equals(SvipStyle.LEVEL_FOUR)){
            vipFriendCardService.createVipFriendCard(userId, VipFriendCardTypeStyle.CARD_18_08);
        }
        return userSvip;
    }

    @Override
    public UserSvip createUserSvip(Integer userId, VipPriceStyle vipPriceStyle){
        UserSvip userSvip = this.getLastestUserSvipRecord((long) userId);
        Date beginTime = new Date();
        if(userSvip != null){
            beginTime = userSvip.getEndTime();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginTime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 0);
        //获取对应结束时间
        Date time = cal.getTime();
        Long startTimeStr = time.getTime();
        Date startTime = new Date(startTimeStr);

        cal.add(Calendar.DATE, vipPriceStyle.getDays());
        //获取对应结束时间
        Date time2 = cal.getTime();
        Long endTimeStr = time2.getTime();
        Date endTime = new Date(endTimeStr);
        return this.addUserSvip(userId, vipPriceStyle.getSvipStyle(), startTime, endTime);
    }

    @Override
    public void resetSvipUser() {

        int pageNo=0;
        int pageSize=20;
        PageRequest pageRequest=new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.ASC,"id"));

        Page<User> userPage= userDao.findAllSvip(pageRequest);
        this.resetUserVip(userPage.getContent());
        for(int i=1;i<userPage.getTotalPages();i++){
            pageRequest=new PageRequest(i,pageSize,new Sort(Sort.Direction.ASC,"id"));
            userPage= userDao.findAllSvip(pageRequest);
            this.resetUserVip(userPage.getContent());
        }
    }

    @Override
    public UserSvip getLastestUserSvipRecord(Long userId){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<UserSvip> userSvipList = userSvipDao.findByUserIdAndEndTime(userId, new Date(), sort);
        if(userSvipList != null && userSvipList.size() > 0){
            return userSvipList.get(0);
        } else {
            return null;
        }
    }

}
