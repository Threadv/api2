package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.service.ContinueDay;

public class AddScheduleUserAnswerStatusResponse extends ApiResponse {
    Integer userAnswerStatus;

    TaskFinish taskFinish;

    public Integer getUserAnswerStatus() {
        return userAnswerStatus;
    }

    public void setUserAnswerStatus(Integer userAnswerStatus) {
        this.userAnswerStatus = userAnswerStatus;
    }

    ContinueDay continueDay;

    public ContinueDay getContinueDay() {
        return continueDay;
    }

    public void setContinueDay(ContinueDay continueDay) {
        this.continueDay = continueDay;
    }

    public TaskFinish getTaskFinish() {
        return taskFinish;
    }

    public void setTaskFinish(TaskFinish taskFinish) {
        this.taskFinish = taskFinish;
    }
}
