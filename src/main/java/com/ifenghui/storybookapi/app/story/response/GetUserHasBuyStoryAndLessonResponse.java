package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.Lesson;
import com.ifenghui.storybookapi.app.story.entity.Story;

import java.util.List;

public class GetUserHasBuyStoryAndLessonResponse extends ApiResponse {

    List<Story> storyList;

    List<Lesson> lessonList;

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }
}
