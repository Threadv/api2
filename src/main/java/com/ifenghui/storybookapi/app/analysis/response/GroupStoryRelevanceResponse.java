package com.ifenghui.storybookapi.app.analysis.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.analysis.entity.GroupRelevance;
import com.ifenghui.storybookapi.app.app.entity.Feedback;

import java.util.List;

public class GroupStoryRelevanceResponse extends ApiPageResponse {
    List<GroupRelevance> groupRelevances;

    public List<GroupRelevance> getGroupRelevances() {
        return groupRelevances;
    }

    public void setGroupRelevances(List<GroupRelevance> groupRelevances) {
        this.groupRelevances = groupRelevances;
    }
}
