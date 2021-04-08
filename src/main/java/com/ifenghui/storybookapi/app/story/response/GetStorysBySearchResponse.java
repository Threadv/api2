package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.story.entity.Story;
import org.elasticsearch.client.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by wml on 2016/12/27.
 */
public class GetStorysBySearchResponse extends ApiPageResponse {
    List<Story> storys;

    public List<Story> getStorys() {
        return storys;
    }

    public void setStorys(List<Story> storys) {
        this.storys = storys;
    }
}
