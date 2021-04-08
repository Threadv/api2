package com.ifenghui.storybookapi.app.app.response;

/**
 * Created by wang
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.app.entity.Help;

import java.util.List;

public class HelpsResponse extends ApiPageResponse {
    List<Help> helps;

    public List<Help> getHelps() {
        return helps;
    }

    public void setHelps(List<Help> helps) {
        this.helps = helps;
    }
}
