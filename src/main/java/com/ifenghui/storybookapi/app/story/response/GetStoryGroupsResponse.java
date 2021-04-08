package com.ifenghui.storybookapi.app.story.response;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;

import java.util.List;

/**
 * Created by jia on 2016/12/22.
 */
public class GetStoryGroupsResponse extends ApiPageResponse {
    List<DisplayGroup> groups;

    public List<DisplayGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<DisplayGroup> groups) {
        this.groups = groups;
    }
}
