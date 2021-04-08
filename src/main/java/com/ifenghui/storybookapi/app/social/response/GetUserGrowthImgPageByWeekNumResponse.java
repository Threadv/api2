package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryImg;

import java.util.List;

public class GetUserGrowthImgPageByWeekNumResponse extends ApiPageResponse {

    Integer weekNum;

    List<UserGrowthDiaryImg> userGrowthDiaryImgList;

    public List<UserGrowthDiaryImg> getUserGrowthDiaryImgList() {
        return userGrowthDiaryImgList;
    }

    public void setUserGrowthDiaryImgList(List<UserGrowthDiaryImg> userGrowthDiaryImgList) {
        this.userGrowthDiaryImgList = userGrowthDiaryImgList;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }
}
