package com.ifenghui.storybookapi.app.story.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.app.social.response.SubscriptionSchedule;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

public class GetSubscriptionSchedulesResponse extends ApiResponse {
        List<SubscriptionSchedule> subscriptionSchedules;

    public List<SubscriptionSchedule> getSubscriptionSchedules() {
        return subscriptionSchedules;
    }

    public void setSubscriptionSchedules(List<SubscriptionSchedule> subscriptionSchedules) {
        this.subscriptionSchedules = subscriptionSchedules;
    }
}
