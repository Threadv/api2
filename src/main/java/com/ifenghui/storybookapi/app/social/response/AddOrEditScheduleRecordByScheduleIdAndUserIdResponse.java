package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.service.ContinueDay;

public class AddOrEditScheduleRecordByScheduleIdAndUserIdResponse extends ApiResponse {
    ContinueDay continueDay;

    TaskFinish taskFinish;

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
