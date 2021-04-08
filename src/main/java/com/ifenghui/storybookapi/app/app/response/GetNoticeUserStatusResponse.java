package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class GetNoticeUserStatusResponse extends ApiResponse {

    Integer readStatus;

    Integer count;

    Integer feedbackCount;

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFeedbackCount() {
        return feedbackCount;
    }

    public void setFeedbackCount(Integer feedbackCount) {
        this.feedbackCount = feedbackCount;
    }
}
