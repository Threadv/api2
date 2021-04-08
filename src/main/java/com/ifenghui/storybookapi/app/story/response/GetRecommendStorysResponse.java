package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.story.entity.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wml on 2017/2/10.
 */
public class GetRecommendStorysResponse extends ApiPageResponse {
    List<Story> storys;

    public List<Story> getStorys() {
        if(storys==null){storys=new ArrayList();}
        return storys;
    }

    public void setStorys(List<Story> storys) {
        this.storys = storys;
    }
}
