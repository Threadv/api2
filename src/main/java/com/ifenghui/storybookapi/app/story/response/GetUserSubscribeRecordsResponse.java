package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;

import java.util.List;

/**
 * Created by wml on 2016/12/30.
 */
public class GetUserSubscribeRecordsResponse extends ApiResponse {

    List<SubscriptionRecord> currentSubscription;
    List<SubscriptionRecord> historySubscription;

    public List<SubscriptionRecord> getCurrentSubscription() {
        return currentSubscription;
    }

    public void setCurrentSubscription(List<SubscriptionRecord> currentSubscription) {
        this.currentSubscription = currentSubscription;
    }

    public List<SubscriptionRecord> getHistorySubscription() {
        return historySubscription;
    }

    public void setHistorySubscription(List<SubscriptionRecord> historySubscription) {
        this.historySubscription = historySubscription;
    }
}
