package com.ifenghui.storybookapi.app.story.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.story.entity.Story;

import java.util.List;

public class GetIpLabelStoryPageResponse extends ApiPageResponse {

    @JsonProperty("storys")
    List<Story> storyList;

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }
}
