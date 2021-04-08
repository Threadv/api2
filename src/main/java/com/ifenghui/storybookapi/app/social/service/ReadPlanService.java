package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.entity.ReadPlan;
import com.ifenghui.storybookapi.app.social.entity.UserJoinReadPlan;
import com.ifenghui.storybookapi.app.social.entity.UserReadPlanRecord;

import java.text.ParseException;
import java.util.List;

public interface ReadPlanService {

    UserJoinReadPlan addUserJoinReadPlan(Integer userId, Integer planName);

    UserJoinReadPlan joinReadPlan(Integer userId, Integer planName);

    List<UserReadPlanRecord> getUserReadPlanRecordListByPlanNameAndUserId(Integer planName, Integer userId);

    ReadPlan getReadPlan(Integer planName);

    ReadPlan getOneMonthReadPlan(Integer planName) throws ParseException;

    public UserJoinReadPlan getUserJoinReadPlanByUserIdAndPlanName(Integer userId, Integer planName);

    public UserReadPlanRecord addUserReadPlanRecord(Integer userId, Integer planName, java.sql.Date finishRecord);

    public void createUserReadPlanRecord(Integer userId);

    public UserReadPlanRecord getUserReadPlanRecordByUserIdAndFinishRecord(Integer userId, java.sql.Date finishRecord);

    public Integer getReadPlanJoinNumberByPlanName(Integer planName);
}
