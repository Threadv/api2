package com.ifenghui.storybookapi.app.studyplan.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.LessonStudyVideo;

public class StudyVideoDetailResponse extends ApiResponse {

    LessonStudyVideo lessonStudyVideo;

    public LessonStudyVideo getLessonStudyVideo() {
        return lessonStudyVideo;
    }

    public void setLessonStudyVideo(LessonStudyVideo lessonStudyVideo) {
        this.lessonStudyVideo = lessonStudyVideo;
    }
}
