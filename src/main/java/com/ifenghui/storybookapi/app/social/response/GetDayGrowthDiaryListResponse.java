package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.service.GrowthDiaryDay;

import java.util.Date;
import java.util.List;

public class GetDayGrowthDiaryListResponse extends ApiResponse {

    Date beginTime;

    Date endTime;

    List<GrowthDiaryDay> growthDiaryDayList;

    public List<GrowthDiaryDay> getGrowthDiaryDayList() {
        return growthDiaryDayList;
    }

    public void setGrowthDiaryDayList(List<GrowthDiaryDay> growthDiaryDayList) {
        this.growthDiaryDayList = growthDiaryDayList;
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

    Integer rsCount;

    public Integer getRsCount() {
        return rsCount;
    }

    public void setRsCount(Integer rsCount) {
        this.rsCount = rsCount;
    }
}
