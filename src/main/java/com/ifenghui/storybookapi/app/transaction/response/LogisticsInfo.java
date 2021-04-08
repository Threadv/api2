package com.ifenghui.storybookapi.app.transaction.response;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by wml on 2017/11/10.
 */
public class LogisticsInfo {

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date time;

    String status;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
