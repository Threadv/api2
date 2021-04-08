package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanMagazineDao;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanFinishMagazine;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanMagazine;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanFinishMagazineService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanMagazineService;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeekPlanMagazineServiceImpl implements WeekPlanMagazineService {

    @Autowired
    WeekPlanMagazineDao weekPlanMagazineDao;

    @Autowired
    WeekPlanFinishMagazineService weekPlanFinishMagazineService;

    @Override
    public List<WeekPlanMagazine> getWeekPlanMagazineByDate(Integer date, Integer userId, WeekPlanStyle weekPlanStyle) {
        List<WeekPlanMagazine> weekPlanMagazineList = weekPlanMagazineDao.getWeekPlanMagazinesByDate(date);
        for(WeekPlanMagazine item : weekPlanMagazineList) {
            Long hasFinish = weekPlanFinishMagazineService.countByMagPlanIdAndUserIdAndPlanType(item.getId(), userId, weekPlanStyle);
            if(hasFinish > 0) {
                item.setIsFinish(1);
            } else {
                item.setIsFinish(0);
            }
        }
        return weekPlanMagazineList;
    }

    @Override
    public WeekPlanMagazine findOne(Integer id) {
        return weekPlanMagazineDao.findOne(id);
    }

}
