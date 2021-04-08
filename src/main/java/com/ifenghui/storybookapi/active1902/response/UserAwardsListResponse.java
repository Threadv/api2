package com.ifenghui.storybookapi.active1902.response;

import com.ifenghui.storybookapi.active1902.entity.Schedule;
import com.ifenghui.storybookapi.active1902.entity.UserAwards;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

/**
 * @Date: 2019/2/19 15:21
 * @Description:
 */
public class UserAwardsListResponse extends ApiResponse {

    List<UserAwards> awardsList;
    List<Schedule> scheduleList;

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List<UserAwards> getAwardsList() {
        return awardsList;
    }

    public void setAwardsList(List<UserAwards> awardsList) {
        this.awardsList = awardsList;
    }
}
