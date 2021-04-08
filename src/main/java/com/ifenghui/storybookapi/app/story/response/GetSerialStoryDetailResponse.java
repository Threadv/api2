package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;

public class GetSerialStoryDetailResponse extends ApiResponse {

    SerialStory serialStory;

    public SerialStory getSerialStory() {
        return serialStory;
    }

    public void setSerialStory(SerialStory serialStory) {
        this.serialStory = serialStory;
    }
}
