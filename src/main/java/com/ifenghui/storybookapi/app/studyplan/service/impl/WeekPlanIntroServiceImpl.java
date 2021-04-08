package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanIntroDao;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanIntro;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanUserRecord;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanIntroService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanUserRecordService;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeekPlanIntroServiceImpl implements WeekPlanIntroService {

    @Autowired
    WeekPlanIntroDao weekPlanIntroDao;

    @Autowired
    WeekPlanUserRecordService weekPlanUserRecordService;

    @Autowired
    WeekPlanJoinService weekPlanJoinService;
    @Override
    public List<WeekPlanIntro> getWeekPlanIntroListByWeekNum(Integer weekNum, WeekPlanStyle weekPlanStyle) {
        return weekPlanIntroDao.getWeekPlanIntrosByWeekNumAndAndPlanType(weekNum, weekPlanStyle.getId());
    }

    @Override
    public WeekPlanIntro getWeekPlanIntroByWeekNum(Integer weekNum, WeekPlanStyle weekPlanStyle) {
        return weekPlanIntroDao.getDistinctByPlanTypeAndWeekNum(weekNum, weekPlanStyle.getId());
    }

    @Override
    public WeekPlanIntro findOne(Integer id) {
        return weekPlanIntroDao.findOne(id);
    }

    @Override
    public Page<WeekPlanIntro> getWeekPlanIntroPage(Integer pageNo, Integer pageSize, WeekPlanStyle weekPlanStyle, Integer userId) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.ASC,"weekNum"));
        Page<WeekPlanIntro> weekPlanIntroPage = weekPlanIntroDao.getWeekPlanIntrosByPlanType(weekPlanStyle.getId(), pageable);
        List<WeekPlanIntro> weekPlanIntroList = weekPlanIntroPage.getContent();
        for(WeekPlanIntro item : weekPlanIntroList) {
            this.setDataToWeekPlanIntro(item, userId);
        }
        return weekPlanIntroPage;
    }

    private void setDataToWeekPlanIntro(WeekPlanIntro weekPlanIntro, Integer userId) {
        WeekPlanUserRecord weekPlanUserRecord = weekPlanUserRecordService.isHasWeekPlanUserRecord(weekPlanIntro.getId(), userId);
        if(weekPlanUserRecord != null) {
            weekPlanIntro.setIsHasPlan(1);
            weekPlanIntro.setHasCandyNum(weekPlanUserRecord.getFinishNum());
        } else {
            weekPlanIntro.setIsHasPlan(0);
            weekPlanIntro.setHasCandyNum(0);
        }

        WeekPlanJoin weekPlanJoin = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId, weekPlanIntro.getWeekPlanStyle());
        if(weekPlanIntro.getWeekNum() <= weekPlanJoin.getBuyNum() ){
            weekPlanIntro.setIsBuy(1);
        }else {
            if(weekPlanIntro.getWeekNum() == 1){
                weekPlanIntro.setIsBuy(1);
            }else {
                weekPlanIntro.setIsBuy(0);
            }
        }
    }
}
