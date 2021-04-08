package com.ifenghui.storybookapi.app.story.response.lesson;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.LessonStudyVideo;

public class GetLessonStudyVideoResponse extends ApiResponse {

    LessonStudyVideo lessonStudyVideo;

    public LessonStudyVideo getLessonStudyVideo() {
        return lessonStudyVideo;
    }

    public void setLessonStudyVideo(LessonStudyVideo lessonStudyVideo) {
        this.lessonStudyVideo = lessonStudyVideo;
    }
}
