package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.entity.LessonViewRecord;

import java.util.List;

public class GetLessonViewRecordListResponse extends ApiResponse {

    List<LessonViewRecord> lessonViewRecordList;

    public List<LessonViewRecord> getLessonViewRecordList() {
        return lessonViewRecordList;
    }

    public void setLessonViewRecordList(List<LessonViewRecord> lessonViewRecordList) {
        this.lessonViewRecordList = lessonViewRecordList;
    }
}
