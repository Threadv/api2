package com.ifenghui.storybookapi.app.story.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.IpLabel;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;

import java.util.List;

/**
 * 2.12版本增加
 * ip专区单条的合集
 */
public class GetIpLabelAndSeriesResponse extends ApiResponse {

    String name;
    String banner;

    List<SerialStory> serialStories;

    List<IpLabel> ipLabelList;

    List<Story> firstLabelStories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public List<IpLabel> getIpLabelList() {
        return ipLabelList;
    }

    public void setIpLabelList(List<IpLabel> ipLabelList) {
        this.ipLabelList = ipLabelList;
    }

    public List<Story> getFirstLabelStories() {
        return firstLabelStories;
    }

    public void setFirstLabelStories(List<Story> firstLabelStories) {
        this.firstLabelStories = firstLabelStories;
    }

    public List<SerialStory> getSerialStories() {
        return serialStories;
    }

    public void setSerialStories(List<SerialStory> serialStories) {
        this.serialStories = serialStories;
    }
}
