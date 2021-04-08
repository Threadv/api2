package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.service.ScheduleSmallImg;

import java.util.List;

public class ScheduleSmallImgJsonDataResponse extends ApiResponse {

    List<ScheduleSmallImg> scheduleImgSmallList;

    public List<ScheduleSmallImg> getScheduleImgSmallList() {
        return scheduleImgSmallList;
    }

    public void setScheduleImgSmallList(List<ScheduleSmallImg> scheduleImgSmallList) {
        this.scheduleImgSmallList = scheduleImgSmallList;
    }
}
