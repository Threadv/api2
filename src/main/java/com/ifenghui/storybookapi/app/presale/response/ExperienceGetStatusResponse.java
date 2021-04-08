package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;

public class ExperienceGetStatusResponse extends ApiPageResponse {

    //分享礼物状态
    Integer shareStatus;
    //分享礼物信息
    String shareMsg;
    
    //互动课礼物状态
    Integer huDongStatus;
    //互动课阅读数量
    Integer huDongSize;
    //互动课礼物信息
    String huDongMsg;

    //体验课礼物杂志
    Integer tastStatus;
    //体验课礼物信息
    String tastMsg;

    PreSaleGift gift;


    public Integer getHuDongSize() {
        return huDongSize;
    }

    public void setHuDongSize(Integer huDongSize) {
        this.huDongSize = huDongSize;
    }

    public Integer getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    public Integer getHuDongStatus() {
        return huDongStatus;
    }

    public void setHuDongStatus(Integer huDongStatus) {
        this.huDongStatus = huDongStatus;
    }

    public Integer getTastStatus() {
        return tastStatus;
    }

    public void setTastStatus(Integer tastStatus) {
        this.tastStatus = tastStatus;
    }

    public String getShareMsg() {
        return shareMsg;
    }

    public void setShareMsg(String shareMsg) {
        this.shareMsg = shareMsg;
    }

    public String getHuDongMsg() {
        return huDongMsg;
    }

    public void setHuDongMsg(String huDongMsg) {
        this.huDongMsg = huDongMsg;
    }

    public String getTastMsg() {
        return tastMsg;
    }

    public void setTastMsg(String tastMsg) {
        this.tastMsg = tastMsg;
    }

    public PreSaleGift getGift() {
        return gift;
    }

    public void setGift(PreSaleGift gift) {
        this.gift = gift;
    }
}
