package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;

public class GetExperienceResponse extends ApiPageResponse {

    Integer isRead;

    //体验课礼物杂志
    Integer tastStatus;
    //体验课礼物信息
    String tastMsg;

    //分享礼物状态
    Integer shareStatus;
    //分享礼物信息
    String shareMsg;

    Integer buyStatus;
    Integer growthLessonCanBuy;
    Integer enlightenLessonCanBuy;

    PreSaleGift gift;

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    public String getShareMsg() {
        return shareMsg;
    }

    public void setShareMsg(String shareMsg) {
        this.shareMsg = shareMsg;
    }

    public Integer getTastStatus() {
        return tastStatus;
    }

    public void setTastStatus(Integer tastStatus) {
        this.tastStatus = tastStatus;
    }


    public String getTastMsg() {
        return tastMsg;
    }

    public void setTastMsg(String tastMsg) {
        this.tastMsg = tastMsg;
    }

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

    public PreSaleGift getGift() {
        return gift;
    }

    public void setGift(PreSaleGift gift) {
        this.gift = gift;
    }
}
