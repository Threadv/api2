package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryImg;

import java.util.Date;
import java.util.List;

public class GrowthDiaryDay {

    Date recordDate;

    Integer recordKey;

    List<UserGrowthDiaryImg> userGrowthDiaryImgList;

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public List<UserGrowthDiaryImg> getUserGrowthDiaryImgList() {
        return userGrowthDiaryImgList;
    }

    public void setUserGrowthDiaryImgList(List<UserGrowthDiaryImg> userGrowthDiaryImgList) {
        this.userGrowthDiaryImgList = userGrowthDiaryImgList;
    }

    public Integer getRecordKey() {
        return recordKey;
    }

    public void setRecordKey(Integer recordKey) {
        this.recordKey = recordKey;
    }
}
