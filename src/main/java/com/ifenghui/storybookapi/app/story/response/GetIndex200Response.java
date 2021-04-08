package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.CustomGroup;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.app.story.response.lesson.LessonIndex;
import com.ifenghui.storybookapi.style.PromptStyle;

import java.util.List;

public class GetIndex200Response extends ApiResponse {

    List<Ads> ads;

    List<CustomGroup> groups;

    List<DisplayGroup> groupList;

    LessonIndex lessonIndex;

    Integer isSvip;

    Prompt prompt;

    public List<Ads> getAds() {
        return ads;
    }

    public void setAds(List<Ads> ads) {
        this.ads = ads;
    }

    public List<CustomGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<CustomGroup> groups) {
        this.groups = groups;
    }

    public List<DisplayGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<DisplayGroup> groupList) {
        this.groupList = groupList;
    }

    public LessonIndex getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(LessonIndex lessonIndex) {
        this.lessonIndex = lessonIndex;
    }

    public Integer getIsSvip() {
        return isSvip;
    }

    public void setIsSvip(Integer isSvip) {
        this.isSvip = isSvip;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }
}
