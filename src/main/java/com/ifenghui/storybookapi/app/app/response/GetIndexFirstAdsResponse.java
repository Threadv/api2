package com.ifenghui.storybookapi.app.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class GetIndexFirstAdsResponse extends ApiResponse {

    IndexAds indexAds;

    public IndexAds getIndexAds() {
        return indexAds;
    }

    public void setIndexAds(IndexAds indexAds) {
        this.indexAds = indexAds;
    }
}
