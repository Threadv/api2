package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.service.GrowthDiaryWeek;

import java.util.List;

public class GetAllUserGrowthDiaryImgByWeekResponse extends ApiResponse{

    List<GrowthDiaryWeek> growthDiaryWeekList;

    public List<GrowthDiaryWeek> getGrowthDiaryWeekList() {
        return growthDiaryWeekList;
    }

    public void setGrowthDiaryWeekList(List<GrowthDiaryWeek> growthDiaryWeekList) {
        this.growthDiaryWeekList = growthDiaryWeekList;
    }
}
