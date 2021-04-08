package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;

import java.util.List;

public class GetUserSerialStorysResponse extends ApiPageResponse {

    List<SerialStory> serialStoryList;

    public List<SerialStory> getSerialStoryList() {
        return serialStoryList;
    }

    public void setSerialStoryList(List<SerialStory> serialStoryList) {
        this.serialStoryList = serialStoryList;
    }
}
