package com.ifenghui.storybookapi.active1902.service.impl;


import com.ifenghui.storybookapi.active1902.dao.ScheduleDao;
import com.ifenghui.storybookapi.active1902.entity.Schedule;
import com.ifenghui.storybookapi.active1902.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date: 2019/2/19 19:07
 * @Description:
 */
@Component
public class ScheduleServiceImpl implements ScheduleService {


    @Autowired
    ScheduleDao scheduleDao;

    @Override
    public Schedule getLastSchedule() {

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Schedule schedule=new Schedule();
        schedule.setStatus(1);
        List<Schedule> scheduleList = scheduleDao.findAll(Example.of(schedule), sort);
        return scheduleList.get(0);
    }

    @Override
    public Page<Schedule> findAll(Schedule schedule, Pageable pageable) {
        return scheduleDao.findAll(Example.of(schedule),pageable);
    }

    @Override
    public List<Schedule> findAll(Schedule schedule) {
        return scheduleDao.findAll(Example.of(schedule),new Sort(Sort.Direction.DESC,"id"));
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleDao.save(schedule);
    }

    @Override
    public Schedule update(Schedule schedule) {
        return scheduleDao.save(schedule);
    }

    @Override
    public void delete(Integer id) {
        scheduleDao.delete(id);
    }

    @Override
    public Schedule findOne(Integer id) {
        return null;
    }
}
