package com.ifenghui.storybookapi.app.social.response;

import java.util.Date;

/**
 * Created by jia on 2016/12/23.
 */


public class SubscriptionSchedule  {
    Date time;
    Integer status;
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
