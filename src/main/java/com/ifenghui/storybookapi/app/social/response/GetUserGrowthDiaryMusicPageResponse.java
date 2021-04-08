package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryMusic;

import java.util.List;

public class GetUserGrowthDiaryMusicPageResponse extends ApiPageResponse {

    List<UserGrowthDiaryMusic> userGrowthDiaryMusicList;

    public List<UserGrowthDiaryMusic> getUserGrowthDiaryMusicList() {
        return userGrowthDiaryMusicList;
    }

    public void setUserGrowthDiaryMusicList(List<UserGrowthDiaryMusic> userGrowthDiaryMusicList) {
        this.userGrowthDiaryMusicList = userGrowthDiaryMusicList;
    }
}
