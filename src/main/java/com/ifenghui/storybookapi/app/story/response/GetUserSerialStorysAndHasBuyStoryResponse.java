package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;

import java.util.List;

public class GetUserSerialStorysAndHasBuyStoryResponse extends GetUserStoryListResponse {

    List<SerialStory> serialStoryList;

    List<BuyStoryRecord> buyStoryRecords;

    @Override
    public List<BuyStoryRecord> getBuyStoryRecords() {
        return buyStoryRecords;
    }

    public void setBuyStoryRecords(List<BuyStoryRecord> buyStoryRecords) {
        this.buyStoryRecords = buyStoryRecords;
    }

    public List<SerialStory> getSerialStoryList() {
        return serialStoryList;
    }

    public void setSerialStoryList(List<SerialStory> serialStoryList) {
        this.serialStoryList = serialStoryList;
    }
}
