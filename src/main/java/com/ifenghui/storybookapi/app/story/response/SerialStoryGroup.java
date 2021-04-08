package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.app.story.entity.Story;

import java.util.List;

public class SerialStoryGroup {

    String name;

    List<Story> storyList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }
}
