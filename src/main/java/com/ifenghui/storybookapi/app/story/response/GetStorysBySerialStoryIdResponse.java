package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.story.entity.Story;

import java.util.List;

/**
 * Created by wml on 2016/12/23.
 */
public class GetStorysBySerialStoryIdResponse extends ApiPageResponse {
    List<Story> storys;

    public List<Story> getStorys() {
        return storys;
    }

    public void setStorys(List<Story> storys) {
        this.storys = storys;
    }


}
