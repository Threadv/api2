package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanFinishMagazineDao;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanFinishMagazine;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanMagazine;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanUserRecord;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanFinishMagazineService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanMagazineService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanReadRecordService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanUserRecordService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.ReadRecordTypeStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class WeekPlanFinishMagazineServiceImpl implements WeekPlanFinishMagazineService {

    @Autowired
    WeekPlanFinishMagazineDao weekPlanFinishMagazineDao;

    @Autowired
    WeekPlanMagazineService weekPlanMagazineService;

    @Autowired
    WeekPlanUserRecordService weekPlanUserRecordService;

    @Autowired
    WeekPlanReadRecordService weekPlanReadRecordService;

    @Override
    public WeekPlanFinishMagazine findOneByMagPlanIdAndUserIdAndPlanType(Integer magPlanId, Integer userId, WeekPlanStyle weekPlanStyle) {
        return weekPlanFinishMagazineDao.getDistinctByMagPlanIdAndUserIdAndPlanType(magPlanId, userId, weekPlanStyle.getId());
    }


    @Override
    public Long countByMagPlanIdAndUserIdAndPlanType(Integer magPlanId, Integer userId, WeekPlanStyle weekPlanStyle) {
        Long count = weekPlanFinishMagazineDao.getCountByMagPlanIdAndUserIdAndPlanType(magPlanId, userId, weekPlanStyle.getId());
        if(count == null) {
            return 0L;
        }
        return count;
    }

    @Override
    public WeekPlanFinishMagazine addWeekPlanFinishMagazine(Integer magPlanId, Integer userId, Integer wordNum, WeekPlanStyle weekPlanStyle) {
        WeekPlanFinishMagazine weekPlanFinishMagazine = new WeekPlanFinishMagazine();
        weekPlanFinishMagazine.setMagPlanId(magPlanId);
        weekPlanFinishMagazine.setUserId(userId);
        weekPlanFinishMagazine.setWordNum(wordNum);
        weekPlanFinishMagazine.setPlanType(weekPlanStyle);
        weekPlanFinishMagazine.setCreateTime(new Date());
        return weekPlanFinishMagazineDao.save(weekPlanFinishMagazine);
    }

    @Override
    public WeekPlanFinishMagazine createWeekPlanFinishMagazine(Integer magPlanId, Integer userId, WeekPlanStyle weekPlanStyle) {
        WeekPlanMagazine weekPlanMagazine = weekPlanMagazineService.findOne(magPlanId);
        if(weekPlanMagazine == null) {
            throw new ApiNotFoundException("未找到改杂志任务！");
        }
        Long hasFinish = this.countByMagPlanIdAndUserIdAndPlanType(magPlanId, userId, weekPlanStyle);
        if(hasFinish > 0) {
            throw new ApiNotFoundException("已经完成这个杂志任务！");
        }
        WeekPlanFinishMagazine weekPlanFinishMagazine = this.addWeekPlanFinishMagazine(magPlanId, userId, weekPlanMagazine.getWordNum(), weekPlanStyle);
        List<WeekPlanMagazine> weekPlanMagazineList = weekPlanMagazineService.getWeekPlanMagazineByDate(weekPlanMagazine.getDate(), userId, weekPlanStyle);
        this.checkIsNeedChangeWeekPlanFinishStatus(weekPlanMagazineList, userId);
        weekPlanReadRecordService.createWeekPlanReadRecord(magPlanId, userId, weekPlanFinishMagazine.getWordNum(), weekPlanStyle, LabelTargetStyle.Magazine, ReadRecordTypeStyle.MAGAZINE, 0);
        return weekPlanFinishMagazine;
    }

    @Override
    public void checkIsNeedChangeWeekPlanFinishStatus(List<WeekPlanMagazine> weekPlanMagazineList, Integer userId) {
        Integer status = 1;
        if(weekPlanMagazineList == null || weekPlanMagazineList.size() == 0) {
            return ;
        }
        Integer date = weekPlanMagazineList.get(0).getDate();
        for(WeekPlanMagazine item : weekPlanMagazineList) {
            if(item.getIsFinish().equals(0)) {
                status = 0;
            }
        }
        if(status.equals(1)) {
            weekPlanUserRecordService.setWeekPlanUserRecordFinishStatusByMagTaskId(date, userId);
        }
    }

}
