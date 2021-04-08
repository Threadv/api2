package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

public class AddUserGrowthDiaryResponse extends ApiResponse {

    List<TaskFinish> taskFinishInfo;

    public List<TaskFinish> getTaskFinishInfo() {
        return taskFinishInfo;
    }

    public void setTaskFinishInfo(List<TaskFinish> taskFinishInfo) {
        this.taskFinishInfo = taskFinishInfo;
    }
}
