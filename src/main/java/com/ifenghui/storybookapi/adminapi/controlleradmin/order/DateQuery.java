package com.ifenghui.storybookapi.adminapi.controlleradmin.order;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DateQuery {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date successStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date successEndTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date createStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date createEndTime;


    public Date getSuccessStartTime() {
        return successStartTime;
    }

    public void setSuccessStartTime(Date successStartTime) {
        this.successStartTime = successStartTime;
    }

    public Date getSuccessEndTime() {
        return successEndTime;
    }

    public void setSuccessEndTime(Date successEndTime) {
        this.successEndTime = successEndTime;
    }

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

}
