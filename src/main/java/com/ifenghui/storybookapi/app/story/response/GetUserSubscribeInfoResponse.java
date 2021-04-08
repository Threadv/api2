package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.Date;

/**
 * Created by wml on 2016/12/30.
 */
public class GetUserSubscribeInfoResponse extends ApiResponse {
    Date startTime;
    Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
