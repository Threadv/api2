package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.app.transaction.entity.BuyStorySubscriptionRecord;

import java.util.List;

public class GetUserSubscriptionStoryResponse extends GetUserStoryListResponse {

    List<BuyStorySubscriptionRecord> buyStoryRecords;

    @Override
    public List<BuyStorySubscriptionRecord> getBuyStoryRecords() {
        return buyStoryRecords;
    }

    public void setBuyStoryRecords(List<BuyStorySubscriptionRecord> buyStoryRecords) {
        this.buyStoryRecords = buyStoryRecords;
    }
}
