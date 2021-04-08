package com.ifenghui.storybookapi.active1902.response;


import com.ifenghui.storybookapi.active1902.entity.Awards;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;

import java.util.List;

/**
 * @Date: 2019/2/19 15:21
 * @Description:
 */
public class AwardsListResponse extends ApiResponse {

    List<Awards> awardsList;
    List<Story> storyList;
    SerialStory serialStory;

    public List<Awards> getAwardsList() {
        return awardsList;
    }

    public void setAwardsList(List<Awards> awardsList) {
        this.awardsList = awardsList;
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public SerialStory getSerialStory() {
        return serialStory;
    }

    public void setSerialStory(SerialStory serialStory) {
        this.serialStory = serialStory;
    }
}
