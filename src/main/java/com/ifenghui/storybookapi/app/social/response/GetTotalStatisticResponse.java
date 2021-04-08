package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.social.service.DayReadStatistic;
import com.ifenghui.storybookapi.app.social.service.TotalReadStatistic;

public class GetTotalStatisticResponse extends ApiResponse {

    DayReadStatistic dayReadStatistic;

    TotalReadStatistic totalReadStatistic;

    Story favoriteStory;

    Story firstStory;

    public DayReadStatistic getDayReadStatistic() {
        return dayReadStatistic;
    }

    public void setDayReadStatistic(DayReadStatistic dayReadStatistic) {
        this.dayReadStatistic = dayReadStatistic;
    }

    public TotalReadStatistic getTotalReadStatistic() {
        return totalReadStatistic;
    }

    public void setTotalReadStatistic(TotalReadStatistic totalReadStatistic) {
        this.totalReadStatistic = totalReadStatistic;
    }

    public Story getFavoriteStory() {
        return favoriteStory;
    }

    public void setFavoriteStory(Story favoriteStory) {
        this.favoriteStory = favoriteStory;
    }

    public Story getFirstStory() {
        return firstStory;
    }

    public void setFirstStory(Story firstStory) {
        this.firstStory = firstStory;
    }
}
