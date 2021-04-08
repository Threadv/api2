package com.ifenghui.storybookapi.app.app.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.app.entity.Feedback;

import java.util.List;

public class GetFeedbacksByUserIdResponse extends ApiPageResponse {

    List<Feedback> feedbacks;

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
