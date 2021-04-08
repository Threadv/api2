package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.social.dao.ReadPlanDao;
import com.ifenghui.storybookapi.app.social.dao.UserJoinReadPlanDao;
import com.ifenghui.storybookapi.app.social.dao.UserReadPlanRecordDao;
import com.ifenghui.storybookapi.app.social.entity.ReadPlan;
import com.ifenghui.storybookapi.app.social.entity.UserJoinReadPlan;
import com.ifenghui.storybookapi.app.social.entity.UserReadPlanRecord;
import com.ifenghui.storybookapi.app.social.service.ReadPlanService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.exception.ApiDuplicateException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ReadPlanServiceImpl implements ReadPlanService {

    @Autowired
    UserJoinReadPlanDao userJoinReadPlanDao;

    @Autowired
    UserReadPlanRecordDao userReadPlanRecordDao;

    @Autowired
    ReadPlanDao readPlanDao;

    @Override
    public UserJoinReadPlan addUserJoinReadPlan(Integer userId, Integer planName) {
        UserJoinReadPlan userJoinReadPlan = new UserJoinReadPlan();
        userJoinReadPlan.setCreateTime(new Date());
        userJoinReadPlan.setPlanName(planName);
        userJoinReadPlan.setUserId(userId);
        return userJoinReadPlanDao.save(userJoinReadPlan);
    }

    @Override
    public UserJoinReadPlan joinReadPlan(Integer userId, Integer planName){
        ReadPlan readPlan = this.getReadPlan(planName);
        if (readPlan == null){
            throw new ApiNotFoundException("未找到当前阅读计划！");
        }
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        Integer nowPlan = Integer.parseInt(dateFormat.format(now));
        if(nowPlan.intValue() != planName){
            throw new ApiDuplicateException("不能参加往期的计划活动！");
        }
        UserJoinReadPlan userJoinReadPlan = this.getUserJoinReadPlanByUserIdAndPlanName(userId, planName);
        if(userJoinReadPlan == null){
            return this.addUserJoinReadPlan(userId, planName);
        } else {
            throw new ApiDuplicateException("您已参加过此阅读计划！");
        }
    }

    @Override
    public List<UserReadPlanRecord> getUserReadPlanRecordListByPlanNameAndUserId(Integer planName, Integer userId){
        UserJoinReadPlan userJoinReadPlan = this.getUserJoinReadPlanByUserIdAndPlanName(userId, planName);
        if(userJoinReadPlan == null){
            return null;
        } else {
            return userReadPlanRecordDao.getAllByPlanNameAndUserIdOrderByFinishRecordAsc(planName, userId);
        }
    }

    @Override
    public ReadPlan getReadPlan(Integer planName) {
        ReadPlan readPlan = readPlanDao.getReadPlanByPlanName(planName);
        if(readPlan == null){
            return null;
        } else {
            return readPlan;
        }
    }

    @Override
    public ReadPlan getOneMonthReadPlan(Integer planName) throws ParseException {
        ReadPlan readPlan = this.getReadPlan(planName);
        if(readPlan == null) {
            return null;
        }
        String planNameStr = planName.toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date now = format.parse(planNameStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MONTH, -1);
        String lastPlanNameStr =  format.format( cal.getTime());
        Integer lastPlanName = Integer.parseInt(lastPlanNameStr);
        ReadPlan lastReadPlan = this.getReadPlan(lastPlanName);
        if(lastReadPlan != null && lastReadPlan.getStatus().equals(2)) {
            readPlan.setLastMonthRewardUser(lastReadPlan.getRewardUserUrl());
        }
        readPlan.setJoinNumber(this.getReadPlanJoinNumberByPlanName(planName));
        return readPlan;
    }

    @Override
    public UserJoinReadPlan getUserJoinReadPlanByUserIdAndPlanName(Integer userId, Integer planName){
        return userJoinReadPlanDao.getUserJoinReadPlanByUserIdAndPlanName(userId, planName);
    }

    @Override
    public Integer getReadPlanJoinNumberByPlanName(Integer planName){
        Integer joinNumber = userJoinReadPlanDao.getUserJoinReadPlanNumberByPlanName(planName);
        if(joinNumber == null){
            joinNumber = 0;
        }
        return joinNumber;
    }

    @Override
    public UserReadPlanRecord addUserReadPlanRecord(Integer userId, Integer planName, java.sql.Date finishRecord){
        UserReadPlanRecord userReadPlanRecord = new UserReadPlanRecord();
        userReadPlanRecord.setFinishRecord(finishRecord);
        userReadPlanRecord.setPlanName(planName);
        userReadPlanRecord.setUserId(userId);
        return userReadPlanRecordDao.save(userReadPlanRecord);
    }

    @Override
    public void createUserReadPlanRecord(Integer userId){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date planNameDate = new Date();
        Integer planNameInt = Integer.parseInt(format.format(planNameDate));
        ReadPlan readPlan = this.getReadPlan(planNameInt);
        UserJoinReadPlan userJoinReadPlan = this.getUserJoinReadPlanByUserIdAndPlanName(userId, planNameInt);
        if(readPlan == null || userJoinReadPlan == null){
            return ;
        }
        java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
        UserReadPlanRecord userReadPlanRecord = this.getUserReadPlanRecordByUserIdAndFinishRecord(userId, now);
        if(userReadPlanRecord == null){
            this.addUserReadPlanRecord(userId, planNameInt, now);
        }
    }
    @Override
    public UserReadPlanRecord getUserReadPlanRecordByUserIdAndFinishRecord(Integer userId, java.sql.Date finishRecord){
        return userReadPlanRecordDao.getUserReadPlanRecordByFinishRecordAndAndUserId(finishRecord, userId);
    }

}
