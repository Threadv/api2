package com.ifenghui.storybookapi.app.presale.response;

/**
 * Created by jia on 2016/12/23.
 */



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;
import com.ifenghui.storybookapi.app.presale.entity.StoryPicture;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;

import java.util.List;

public class YiZhiActivityUserReadRecordsResponse extends ApiPageResponse {

    Integer couponStatus;

    Integer backStatus;

    Integer caseStatus;

    Integer payId;

    Integer goodsId;

    Integer readRecordSize;

    PreSaleGift backGift;

    PreSaleGift caseGift;

    StoryPicture storyPicture;

    List<UserReadRecord> readRecordList;

    public Integer getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }

    public Integer getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(Integer backStatus) {
        this.backStatus = backStatus;
    }

    public Integer getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(Integer caseStatus) {
        this.caseStatus = caseStatus;
    }

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public List<UserReadRecord> getReadRecordList() {
        return readRecordList;
    }

    public void setReadRecordList(List<UserReadRecord> readRecordList) {
        this.readRecordList = readRecordList;
    }

    public StoryPicture getStoryPicture() {
        return storyPicture;
    }

    public void setStoryPicture(StoryPicture storyPicture) {
        this.storyPicture = storyPicture;
    }

    public Integer getReadRecordSize() {
        return readRecordSize;
    }

    public void setReadRecordSize(Integer readRecordSize) {
        this.readRecordSize = readRecordSize;
    }

    public PreSaleGift getBackGift() {
        return backGift;
    }

    public void setBackGift(PreSaleGift backGift) {
        this.backGift = backGift;
    }

    public PreSaleGift getCaseGift() {
        return caseGift;
    }

    public void setCaseGift(PreSaleGift caseGift) {
        this.caseGift = caseGift;
    }
}

