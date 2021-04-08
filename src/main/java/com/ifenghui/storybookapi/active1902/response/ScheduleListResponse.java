package com.ifenghui.storybookapi.active1902.response;


import com.ifenghui.storybookapi.active1902.entity.Schedule;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;

import java.util.List;

/**
 * @Date: 2019/2/19 15:21
 * @Description:
 */
public class ScheduleListResponse extends ApiPageResponse {

    List<Schedule> schedules;

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
