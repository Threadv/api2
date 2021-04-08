package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.api.response.base.ApiPage;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryImg;

import java.util.Date;
import java.util.List;

public class GrowthDiaryWeek {

    Integer weekNum;

    List<UserGrowthDiaryImg> userGrowthDiaryImgList;

    ApiPage page;

    Integer crossYear;

    Date beginTime;

    Date endTime;

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public List<UserGrowthDiaryImg> getUserGrowthDiaryImgList() {
        return userGrowthDiaryImgList;
    }

    public void setUserGrowthDiaryImgList(List<UserGrowthDiaryImg> userGrowthDiaryImgList) {
        this.userGrowthDiaryImgList = userGrowthDiaryImgList;
    }

    public ApiPage getPage() {
        return page;
    }

    public void setPage(ApiPage page) {
        this.page = page;
    }

    public Integer getCrossYear() {
        return crossYear;
    }

    public void setCrossYear(Integer crossYear) {
        this.crossYear = crossYear;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
