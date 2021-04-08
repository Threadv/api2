package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.app.entity.Config;

import java.util.List;

/**
 * Created by w
 */
public class GetConfigListResponse extends ApiPageResponse {
    List<Config> configs;

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }
}
