package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialBanner;

import java.util.List;

public class GetSerialBannerResponse extends ApiResponse {

    SerialBanner serialBanner;

    public SerialBanner getSerialBanner() {
        return serialBanner;
    }

    public void setSerialBanner(SerialBanner serialBanner) {
        this.serialBanner = serialBanner;
    }
}
