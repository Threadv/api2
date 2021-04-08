package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;

import java.util.List;

/**
 * Created by wml on 2016/12/30.
 */
public class GetMoreUserBuyStorysResponse extends ApiPageResponse {
    List<BuyStoryRecord> buyStoryRecords;

    public List<BuyStoryRecord> getBuyStoryRecords() {
        return buyStoryRecords;
    }

    public void setBuyStoryRecords(List<BuyStoryRecord> buyStoryRecords) {
        this.buyStoryRecords = buyStoryRecords;
    }
}
