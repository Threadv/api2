package com.ifenghui.storybookapi.app.social.service;

import io.swagger.models.auth.In;

public class GrowthDiaryMonthDate {

    Integer month;

    Integer id;

    Integer isHasData;

    Integer type;

    Integer lastMonth;

    public GrowthDiaryMonthDate(Integer month, Integer monthDate, Integer isHasData, Integer type, Integer lastMonth){
        this.month = month;
        this.id = monthDate;
        this.type = type;
        this.isHasData = isHasData;
        this.lastMonth = lastMonth;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsHasData() {
        return isHasData;
    }

    public void setIsHasData(Integer isHasData) {
        this.isHasData = isHasData;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(Integer lastMonth) {
        this.lastMonth = lastMonth;
    }
}
