package com.ifenghui.storybookapi.app.app.response;

/**
 * Created by jia on 2016/12/22.
 */

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.entity.Feedback;
public class AddFeedbackResponse extends ApiResponse {
    Feedback feedback;

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
