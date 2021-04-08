package com.ifenghui.storybookapi.app.social.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

public class AddUserReadRecordResponse extends ApiResponse{
    List<TaskFinish> taskFinishInfo;

    Boolean isSchedule;

    public Boolean getSchedule() {
        return isSchedule;
    }

    public void setSchedule(Boolean schedule) {
        isSchedule = schedule;
    }

    public List<TaskFinish> getTaskFinishInfo() {
        return taskFinishInfo;
    }

    public void setTaskFinishInfo(List<TaskFinish> taskFinishInfo) {
        this.taskFinishInfo = taskFinishInfo;
    }
}
