package com.ifenghui.storybookapi.app.transaction.response.lesson;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class GetShareMagazineStatusResponse extends ApiResponse {

    Integer buyStatus;

    Integer growthLessonCanBuy;

    Integer enlightenLessonCanBuy;

    public Integer getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }

    public Integer getGrowthLessonCanBuy() {
        return growthLessonCanBuy;
    }

    public void setGrowthLessonCanBuy(Integer growthLessonCanBuy) {
        this.growthLessonCanBuy = growthLessonCanBuy;
    }

    public Integer getEnlightenLessonCanBuy() {
        return enlightenLessonCanBuy;
    }

    public void setEnlightenLessonCanBuy(Integer enlightenLessonCanBuy) {
        this.enlightenLessonCanBuy = enlightenLessonCanBuy;
    }
}
