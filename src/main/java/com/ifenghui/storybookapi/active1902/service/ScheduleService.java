package com.ifenghui.storybookapi.active1902.service;


import com.ifenghui.storybookapi.active1902.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Date: 2019/2/19 19:07
 * @Description:
 */
public interface ScheduleService {


    /**
     * 获得最后一期
     * @return
     */
    Schedule getLastSchedule();

    Page<Schedule> findAll(Schedule schedule, Pageable pageable);

    List<Schedule> findAll(Schedule schedule);

    Schedule save(Schedule schedule);

    Schedule update(Schedule schedule);

    void delete(Integer id);

    Schedule findOne(Integer id);
}
