package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiary;

import java.util.List;

public class GetUserGrowthDiaryPageResponse extends ApiPageResponse {

    List<UserGrowthDiary> userGrowthDiaryList;

    public List<UserGrowthDiary> getUserGrowthDiaryList() {
        return userGrowthDiaryList;
    }

    public void setUserGrowthDiaryList(List<UserGrowthDiary> userGrowthDiaryList) {
        this.userGrowthDiaryList = userGrowthDiaryList;
    }
}
