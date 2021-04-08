package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.entity.Config;

import java.io.Serializable;

/**
 * Created by wml on 2016/12/26.
 */
public class GetConfigByKeyResponse extends ApiResponse implements Serializable {
    Config config;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
