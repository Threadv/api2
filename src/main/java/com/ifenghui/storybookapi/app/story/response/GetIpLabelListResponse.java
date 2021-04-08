package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.IpLabel;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;

import java.io.Serializable;
import java.util.List;

public class GetIpLabelListResponse extends ApiResponse {


    String name;
    String banner;
    List<IpLabel> ipLabelList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public List<IpLabel> getIpLabelList() {
        return ipLabelList;
    }

    public void setIpLabelList(List<IpLabel> ipLabelList) {
        this.ipLabelList = ipLabelList;
    }
}
