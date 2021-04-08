package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.Story;

/**
 * Created by wml on 2016/12/23.
 */
public class GetStoryDetailByIdResponse extends ApiResponse{

    Story story;

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
