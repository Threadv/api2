package com.ifenghui.storybookapi.app.social.response;

/**
 * Created by jia on 2016/12/22.
 */

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

public class ShareTaskInviteResponse extends ApiResponse {
    List<TaskFinish> taskFinishInfo;

    public List<TaskFinish> getTaskFinishInfo() {
        return taskFinishInfo;
    }

    public void setTaskFinishInfo(List<TaskFinish> taskFinishInfo) {
        this.taskFinishInfo = taskFinishInfo;
    }
}
