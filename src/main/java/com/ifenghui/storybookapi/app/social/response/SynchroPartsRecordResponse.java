package com.ifenghui.storybookapi.app.social.response;

/**
 * Created by wml on 2017/11/14.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

public class SynchroPartsRecordResponse extends ApiResponse{

    List<TaskFinish> taskFinishInfo;

    List<FormatPartsRecordResponse> parts;

    public List<FormatPartsRecordResponse> getParts() {
        return parts;
    }

    public void setParts(List<FormatPartsRecordResponse> parts) {
        this.parts = parts;
    }

    public List<TaskFinish> getTaskFinishInfo() {
        return taskFinishInfo;
    }

    public void setTaskFinishInfo(List<TaskFinish> taskFinishInfo) {
        this.taskFinishInfo = taskFinishInfo;
    }

}
