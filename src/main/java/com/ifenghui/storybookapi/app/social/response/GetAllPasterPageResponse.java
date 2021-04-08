package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.story.entity.Paster;

import java.util.List;

public class GetAllPasterPageResponse extends ApiPageResponse {

    List<Paster> pasterList;

    public List<Paster> getPasterList() {
        return pasterList;
    }

    public void setPasterList(List<Paster> pasterList) {
        this.pasterList = pasterList;
    }
}
