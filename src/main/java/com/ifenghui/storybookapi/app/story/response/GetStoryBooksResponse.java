package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.analysis.entity.GroupRelevance;

import java.util.List;

/**
 * Created by wml on 2017/1/6.
 */
public class GetStoryBooksResponse extends ApiPageResponse {
    List<GroupRelevance> storys;

    public List<GroupRelevance> getStorys() {
        return storys;
    }

    public void setStorys(List<GroupRelevance> storys) {
        this.storys = storys;
    }
}
