package com.ifenghui.storybookapi.app.story.response.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.story.entity.LessonItem;

import java.util.List;

public class GetLessonItemListResponse extends ApiResponse {

    String name;

    Integer hasReadNum;

    List<LessonItem> lessonItemList;

    Integer isAllBuy;

    Integer hasNeedRead;

    Integer allLessonCount;

    Integer finishLessonNum;

    @JsonProperty("shareGetMagazine")
    Ads ads;

    public List<LessonItem> getLessonItemList() {
        return lessonItemList;
    }

    public void setLessonItemList(List<LessonItem> lessonItemList) {
        this.lessonItemList = lessonItemList;
    }

    public Integer getHasReadNum() {
        return hasReadNum;
    }

    public void setHasReadNum(Integer hasReadNum) {
        this.hasReadNum = hasReadNum;
    }

    public Integer getIsAllBuy() {
        return isAllBuy;
    }

    public void setIsAllBuy(Integer isAllBuy) {
        this.isAllBuy = isAllBuy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHasNeedRead() {
        return hasNeedRead;
    }

    public void setHasNeedRead(Integer hasNeedRead) {
        this.hasNeedRead = hasNeedRead;
    }

    public Integer getAllLessonCount() {
        return allLessonCount;
    }

    public void setAllLessonCount(Integer allLessonCount) {
        this.allLessonCount = allLessonCount;
    }

    public Integer getFinishLessonNum() {
        return finishLessonNum;
    }

    public void setFinishLessonNum(Integer finishLessonNum) {
        this.finishLessonNum = finishLessonNum;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }
}
