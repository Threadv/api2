package com.ifenghui.storybookapi.active1902.service.impl;



import com.ifenghui.storybookapi.active1902.dao.UserAwardsDao;
import com.ifenghui.storybookapi.active1902.entity.Schedule;
import com.ifenghui.storybookapi.active1902.entity.UserAwards;
import com.ifenghui.storybookapi.active1902.service.ScheduleService;
import com.ifenghui.storybookapi.active1902.service.UserAwardsService;
import com.ifenghui.storybookapi.active1902.style.AwardsStyle;
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Date: 2019/2/19 14:52
 * @Description:
 */
@Component
public class UserAwardsServiceImpl implements UserAwardsService {

    @Autowired
    UserAwardsDao userAwardsDao;
    @Autowired
    StoryDao storyDao;
    @Autowired
    SerialStoryDao serialStoryDao;
    @Autowired
    ScheduleService scheduleService;


    @Override
    public UserAwards addRecord(Integer userId, Integer scheduleId, Integer type, Integer storyId, Integer serialStoryId) {
        UserAwards awards = userAwardsDao.findUserAwardsByUserIdAndScheduleId(userId, scheduleId);
        if(awards != null){
            return awards;
        }
        UserAwards userAwards = new UserAwards();
        userAwards.setUserId(userId);
        userAwards.setScheduleId(scheduleId);
        userAwards.setType(type);
        userAwards.setStoryId(storyId);
        userAwards.setSerialStoryId(serialStoryId);
        userAwards.setCreateTime(new Date());
        userAwardsDao.save(userAwards);
        return userAwards;
    }

    @Override
    public List<Schedule> findUnGetAwardsByUserId(Integer userId) {

        List<UserAwards> awardsList = this.findAwardsByUserId(userId);
        Schedule schedule=new Schedule();
        schedule.setStatus(1);
        List<Schedule> schedules = scheduleService.findAll(schedule);
        List<Schedule> scheduleList = new ArrayList<>();
        for (Schedule s:schedules) {
            for (UserAwards us:awardsList) {
                if(s.getId().intValue() == us.getScheduleId()){
                    scheduleList.add(s);
                }
            }
        }
        if(schedules!=null&&schedules.size()>0){
            schedules.removeAll(scheduleList);
        }

        return schedules;
    }

    @Override
    public UserAwards getAwardsByUserIdAndScheduleId(Integer userId, Integer scheduleId) {

        UserAwards userAwards = userAwardsDao.findUserAwardsByUserIdAndScheduleId(userId, scheduleId);

        return userAwards;
    }

    @Override
    public boolean isGetAllAwards(Integer userId, List<Schedule> schedules) {

        boolean flag = false;
        for (Schedule s : schedules) {
            UserAwards userAwards = userAwardsDao.findUserAwardsByUserIdAndScheduleId(userId, s.getId());
            if (userAwards != null) {
                flag = true;
            }else {
                flag =false;
                break;
            }
        }
        return flag;
    }

    @Override
    public List<UserAwards> findAwardsByUserId(Integer userId) {

        List<UserAwards> awardsList = userAwardsDao.findUserAwardsByUserId(userId);
        for (UserAwards a : awardsList) {
            if (a.getStoryId() > 0) {
                Story story = storyDao.findOne(a.getStoryId().longValue());
                a.setStory(story);
            } else if (a.getSerialStoryId() > 0) {
                SerialStory serialStory = serialStoryDao.findOne(a.getSerialStoryId().longValue());
                a.setSerialStory(serialStory);
            }
        }
        return awardsList;
    }

    @Override
    public Integer countAwardsByType(Integer scheduleId, Integer userId, AwardsStyle awardsStyle) {

        int type = awardsStyle.getId();
        return userAwardsDao.countUserAwardsByScheduleIdAndUserIdAndType(scheduleId, userId, type);
    }

    @Override
    public Integer countAwardsByTypeAndUserId(Integer userId, AwardsStyle awardsStyle) {


        return userAwardsDao.countUserAwardsByUserIdAndType(userId,awardsStyle.getId());
    }

    @Override
    public Integer countAwardsByUserId( Integer userId) {

        Integer c = userAwardsDao.countUserAwardsByUserId(userId);
        return c;
    }
}
