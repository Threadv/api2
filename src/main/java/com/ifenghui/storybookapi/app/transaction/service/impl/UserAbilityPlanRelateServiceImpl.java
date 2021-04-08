package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanJoinDao;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.app.transaction.dao.AbilityPlanOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.UserAbilityPlanRelateDao;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.UserAbilityPlanRelate;
import com.ifenghui.storybookapi.app.transaction.entity.vip.UserSvip;
import com.ifenghui.storybookapi.app.transaction.service.AbilityPlanOrderService;
import com.ifenghui.storybookapi.app.transaction.service.UserAbilityPlanRelateService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Date: 2018/11/8 19:47
 * @Description:
 */

@Transactional
@Component
public class UserAbilityPlanRelateServiceImpl implements UserAbilityPlanRelateService {

    @Autowired
    WeekPlanJoinDao weekPlanJoinDao;
    @Autowired
    WeekPlanJoinService weekPlanJoinService;
    @Autowired
    UserAbilityPlanRelateDao userAbilityPlanRelateDao;

    @Autowired
    UserService userService;

    @Autowired
    AbilityPlanOrderService abilityPlanOrderService;

    @Autowired
    AbilityPlanOrderDao abilityPlanOrderDao;

    @Autowired
    UserDao userDao;


    @Override
    public Date getUserAbilityPlanRelateStartTime(Long userId, Date startTime) {

        Date start = startTime;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<UserAbilityPlanRelate> relateList = userAbilityPlanRelateDao.findUserAbilityPlanRelatesByUserId(userId.intValue(), sort);
        int count = 0;
        for (UserAbilityPlanRelate rel : relateList) {
            count++;
            if (count > 1) {
                Date end = rel.getEndTime();
                if (start.getTime() <= end.getTime()) {
                    startTime = rel.getStartTime();
                }
            }
            start = rel.getStartTime();
        }
        return startTime;
    }

    @Override
    public UserAbilityPlanRelate getUserAbilityPlanRelateByUserIdAndType(Integer userId, Integer type) {

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<UserAbilityPlanRelate> relateList = userAbilityPlanRelateDao.findUserAbilityPlanRelatesByUserIdAndType(userId, type, sort);
        if (relateList.size() > 0) {
            return relateList.get(0);
        }
        return null;
    }

    @Override
    public void updateAbilityRelateByUserId(Integer userId, WeekPlanStyle weekPlanStyle) {

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<UserAbilityPlanRelate> relateList = userAbilityPlanRelateDao.findUserAbilityPlanRelatesByUserIdAndType(userId, WeekPlanStyle.DEFAULT.getId(), sort);
        for (UserAbilityPlanRelate r : relateList) {
            r.setAbilityPlanStyle(AbilityPlanStyle.getById(weekPlanStyle.getId()));
            userAbilityPlanRelateDao.save(r);
        }

    }

    @Override
    @Transactional
    public UserAbilityPlanRelate createUserAbilityPlanRelate(Integer userId, AbilityPlanCodeStyle abilityPlanStyle) {
        UserAbilityPlanRelate userAbilityPlanRelate = this.getLastestUserAbilityPlanRelateRecord((long) userId);
        Date beginTime = new Date();
        if (userAbilityPlanRelate != null) {
            if (beginTime.getTime() < userAbilityPlanRelate.getEndTime().getTime()) {
                beginTime = userAbilityPlanRelate.getEndTime();
            }
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

        cal.add(Calendar.DATE, abilityPlanStyle.getDays());
        //获取对应结束时间
        Date time2 = cal.getTime();
        Long endTimeStr = time2.getTime();
        Date endTime = new Date(endTimeStr);
        return this.addUserAbilityPlanRelate(userId, abilityPlanStyle.getAbilityPlanStyle(), startTime, endTime);
    }

    @Override
    public UserAbilityPlanRelate getLastestUserAbilityPlanRelateRecord(Long userId) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<UserAbilityPlanRelate> userAbilityPlanRelateList = userAbilityPlanRelateDao.findByUserIdAndEndTime(userId.intValue(), new Date(), sort);
        if (userAbilityPlanRelateList != null && userAbilityPlanRelateList.size() > 0) {
            return userAbilityPlanRelateList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public UserAbilityPlanRelate addUserAbilityPlanRelate(Integer userId, AbilityPlanStyle abilityPlanStyle, Date startTime, Date endTime) {
        UserAbilityPlanRelate userAbilityPlanRelate = new UserAbilityPlanRelate();
        userAbilityPlanRelate.setUserId(userId);
        userAbilityPlanRelate.setAbilityPlanStyle(abilityPlanStyle);
        userAbilityPlanRelate.setCreateTime(new Date());
        userAbilityPlanRelate.setStartTime(startTime);
        userAbilityPlanRelate.setEndTime(endTime);
        userAbilityPlanRelateDao.save(userAbilityPlanRelate);
        //修改用户是否优能计划状态 0 false  1 true
        userService.changeUserIsAbilityPlanStyle(userId.longValue(), 1);

        return userAbilityPlanRelate;
    }

    @Override
    public Integer countUserAbilityPlan(Integer userId) {

        return userAbilityPlanRelateDao.countUserAbilityPlan(userId);
    }

    @Override
    public void resetAbilityPlanUser() {
        int pageNo = 0;
        int pageSize = 20;
        PageRequest pageRequest = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.ASC, "id"));

        Page<User> userPage = userDao.findAllAbilityPlan(pageRequest);
        this.resetUserAbilityPlan(userPage.getContent());
        for (int i = 1; i < userPage.getTotalPages(); i++) {
            pageRequest = new PageRequest(i, pageSize, new Sort(Sort.Direction.ASC, "id"));
            userPage = userDao.findAllAbilityPlan(pageRequest);
            this.resetUserAbilityPlan(userPage.getContent());
        }
    }

    //验证是否已经过期
    private boolean resetUserAbilityPlan(List<User> users) {
        //查询数据
        for (User user : users) {
            int userId = user.getId().intValue();
            boolean flag = this.checkUserAbilityPlan(user.getId());
            if (flag == false) {
                // 设置buyNum为初始值 上锁状态
                WeekPlanJoin join24 = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId, WeekPlanStyle.TWO_FOUR);
                if(join24 != null){
                    join24.setBuyNum(1);
                    weekPlanJoinDao.save(join24);
                }
                WeekPlanJoin join46 = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId, WeekPlanStyle.FOUR_SIX);
                if(join46 != null){
                    join46.setBuyNum(1);
                    weekPlanJoinDao.save(join46);
                }
                //订单设置为年龄段设置为未选择 0
                abilityPlanOrderService.setPlanTypeExpire(userId);
                user.setIsAbilityPlan(0);
                userDao.save(user);
            }
        }
        return false;
    }

    //验证是否已经过期
    private boolean checkUserAbilityPlan(Long userId) {

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<UserAbilityPlanRelate> userAbilityPlanRelateList = userAbilityPlanRelateDao.findByUserIdAndEndTime(userId.intValue(), new Date(), sort);
        if (userAbilityPlanRelateList.size() > 0) {
            return true;
        }
        return false;
    }


    @Override
    public Page<UserAbilityPlanRelate> findAll(UserAbilityPlanRelate userAbilityPlanRelate, PageRequest pageRequest) {
        return userAbilityPlanRelateDao.findAll(Example.of(userAbilityPlanRelate),pageRequest);
    }

    @Override
    public void delete(Integer id) {
         userAbilityPlanRelateDao.delete(id);
    }
}
