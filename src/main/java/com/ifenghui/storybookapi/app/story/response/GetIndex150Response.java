package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;

import java.util.List;

/**
 * Created by wml on 2016/12/27.
 */
public class GetIndex150Response extends ApiResponse {
    List<Ads> ads;
    List<DisplayGroup> groups;

    List<DisplayGroup> groupList;

    public List<DisplayGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<DisplayGroup> groups) {
        this.groups = groups;
    }

    public List<Ads> getAds() {
        return ads;
    }

    public void setAds(List<Ads> ads) {
        this.ads = ads;
    }

    public List<DisplayGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<DisplayGroup> groupList) {
        this.groupList = groupList;
    }
}
