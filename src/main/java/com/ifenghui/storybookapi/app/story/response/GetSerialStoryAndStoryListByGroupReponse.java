package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;

import java.util.List;

public class GetSerialStoryAndStoryListByGroupReponse extends ApiResponse {

    SerialStory serialStory;

    List<SerialStoryGroup> serialStoryGroupList;

    public SerialStory getSerialStory() {
        return serialStory;
    }

    public void setSerialStory(SerialStory serialStory) {
        this.serialStory = serialStory;
    }

    public List<SerialStoryGroup> getSerialStoryGroupList() {
        return serialStoryGroupList;
    }

    public void setSerialStoryGroupList(List<SerialStoryGroup> serialStoryGroupList) {
        this.serialStoryGroupList = serialStoryGroupList;
    }
}
