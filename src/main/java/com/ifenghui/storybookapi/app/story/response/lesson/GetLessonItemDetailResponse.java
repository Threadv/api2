package com.ifenghui.storybookapi.app.story.response.lesson;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.LessonItemRelate;

import java.util.List;

public class GetLessonItemDetailResponse extends ApiResponse {

    List<LessonItemRelate> lessonItemRelateList;

    public List<LessonItemRelate> getLessonItemRelateList() {
        return lessonItemRelateList;
    }

    public void setLessonItemRelateList(List<LessonItemRelate> lessonItemRelateList) {
        this.lessonItemRelateList = lessonItemRelateList;
    }
}
