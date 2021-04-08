package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.story.entity.Magazine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wml on 2016/12/30.
 */
public class GetUserSubscribeStorysResponse extends ApiPageResponse {
    List<Magazine> magazines;

    public List<Magazine> getMagazines() {
        if(magazines==null){magazines=new ArrayList();}
        return magazines;
    }

    public void setMagazines(List<Magazine> magazines) {
        this.magazines = magazines;
    }
}
