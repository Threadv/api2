package com.ifenghui.storybookapi.app.user.service.impl;

import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanUserRecordService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.dao.UserExtendDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserExtendServiceImpl implements UserExtendService {

    @Autowired
    UserExtendDao userExtendDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    WeekPlanUserRecordService weekPlanUserRecordService;

    @Override
    public UserExtend findUserExtendByUserId(Long userId) {
        UserExtend userExtend = new UserExtend();
        userExtend = userExtendDao.getUserExtendByUserId(userId.intValue());
        if (userExtend == null) {
            userExtend = this.createUserExtend(userId.intValue(), 0);
        }
        return userExtend;
    }

    @Override
    public UserExtend changeUserExtend(Long userId, Integer wordCount, Integer vocabularyCount) {
        UserExtend userExtend = this.findUserExtendByUserId(userId);
        if (userExtend == null) {
            userExtend = new UserExtend();
            userExtend.setDeviceLimitNum(3);
            userExtend.setCashBalance(0);
            userExtend.setFriendTradeAmount(0);
            userExtend.setFriendTradeNum(0);
            userExtend.setShareFriendNum(0);
            userExtend.setWeekPlanType(0);
            userExtend.setWeekPlanTypeNew(0);
        }
        userExtend.setUserId(userId.intValue());
        userExtend.setWordCount(wordCount);
        userExtend.setVocabularyCount(vocabularyCount);
        userExtend.setUserParentId(0);
        userExtend = userExtendDao.save(userExtend);
        return userExtend;
    }

    @Override
    public UserExtend createUserExtend(Integer userId, Integer userParentId) {
        UserExtend userExtend = new UserExtend();
        userExtend.setUserId(userId);
        userExtend.setVocabularyCount(0);
        userExtend.setWordCount(0);
        userExtend.setUserParentId(userParentId);
        userExtend.setDeviceLimitNum(3);
        userExtend.setCashBalance(0);
        userExtend.setFriendTradeAmount(0);
        userExtend.setFriendTradeNum(0);
        userExtend.setShareFriendNum(0);
        userExtend.setWeekPlanType(0);
        userExtend.setWeekPlanTypeNew(0);
        userExtend.setPlanChangeCount(0);
        return userExtendDao.save(userExtend);
    }

    @Override
    public UserExtend editParentId(Integer userId, Integer userParentId) {
        UserExtend userExtend = userExtendDao.getUserExtendByUserId(userId);
        if (userExtend == null) {
            UserExtend newUserExtend = this.createUserExtend(userId, userParentId);
            this.changeUserShareFriendNum(userParentId);
            return newUserExtend;
        }
        if (userId.intValue() == userParentId.intValue()) {
            return userExtend;
        }
        if (userExtend.getUserParentId() == 0) {
            userExtend.setUserParentId(userParentId);
            this.changeUserShareFriendNum(userParentId);
            return userExtendDao.save(userExtend);
        }
//        如果已经有上级id不再修改
        return userExtend;
    }

    @Override
    public Page<UserExtend> getUsersByUserExtendParentId(Integer userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "userId"));
        return userExtendDao.getUserExtendsByUserParentId(userId, pageable);
    }

    @Override
    public List<User> getUserListFromUserExtendList(List<UserExtend> userExtendList) {
        List<User> userList = new ArrayList<>();
        for (UserExtend item : userExtendList) {
            User user = userService.getUser(item.getUserId());
            userList.add(user);
        }
        return userList;
    }

    @Override
    public UserExtend changeUserShareTradeData(Integer userId, Integer cashBalance, Integer friendTradeNum, Integer friendTradeAmount) {
        UserExtend userExtend = userExtendDao.getUserExtendByUserId(userId);
        if (userExtend == null) {
            userExtend = this.createUserExtend(userId, 0);
        }
        userExtend.setFriendTradeNum(friendTradeNum);
        userExtend.setCashBalance(cashBalance);
        userExtend.setFriendTradeAmount(friendTradeAmount);
        return userExtendDao.save(userExtend);
    }

    @Override
    public UserExtend changeUserShareFriendNum(Integer userId) {
        UserExtend userExtend = userExtendDao.getUserExtendByUserId(userId);
        if (userExtend == null) {
            userExtend = this.createUserExtend(userId, 0);
        }
        Integer shareFriendNum = userExtendDao.getCountUserExtendByUserParentId(userId);
        if (shareFriendNum == null) {
            shareFriendNum = 0;
        }
        userExtend.setShareFriendNum(shareFriendNum);
        return userExtendDao.save(userExtend);
    }

    @Override
    public void changeUserWeekPlanType(Integer userId, WeekPlanStyle weekPlanStyle) {
        UserExtend userExtend = userExtendDao.getUserExtendByUserId(userId);
        if (userExtend == null) {
            userExtend = this.createUserExtend(userId, 0);
        }
        if(userExtend.getWeekPlanType() > 0){
            return;
        }
        /**
         * 查询用户是否有第二周的推送记录 2-4 4-6 任意
         */
        Integer record24 = weekPlanUserRecordService.getCountByUserIdAndWeekPlanId(userId, 2);
        Integer record46 = weekPlanUserRecordService.getCountByUserIdAndWeekPlanId(userId, 54);
        int cont = record24 + record46;
        if (cont > 0 && weekPlanStyle.getId() != userExtend.getWeekPlanType()) {
            userExtend.setPlanChangeCount(userExtend.getPlanChangeCount() + 1);
        }
        userExtendDao.save(userExtend);
    }

    @Override
    public UserExtend update(UserExtend userExtend) {
        return userExtendDao.save(userExtend);
    }
}
