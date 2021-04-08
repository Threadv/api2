package com.ifenghui.storybookapi.app.story.response.lesson;

import com.ifenghui.storybookapi.app.story.entity.Lesson;

import java.util.List;

public class LessonIndex {

    String name;

    String content;

    String intro;

    Integer targetType;

    List<Lesson> lessonList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }
}
