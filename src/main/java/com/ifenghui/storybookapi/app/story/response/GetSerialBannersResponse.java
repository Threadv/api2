package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialBanner;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;

import java.util.List;

public class GetSerialBannersResponse extends ApiPageResponse {

    List<SerialBanner> serialBanners;

    public List<SerialBanner> getSerialBanners() {
        return serialBanners;
    }

    public void setSerialBanners(List<SerialBanner> serialBanners) {
        this.serialBanners = serialBanners;
    }
}
