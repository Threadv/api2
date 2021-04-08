package com.ifenghui.storybookapi.app.story.response;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;

import java.util.List;

/**
 * Created by jia on 2016/12/22.
 */
public class GetStoryGroupResponse extends ApiPageResponse {
    DisplayGroup group;

    public DisplayGroup getGroup() {
        return group;
    }

    public void setGroup(DisplayGroup group) {
        this.group = group;
    }
}
