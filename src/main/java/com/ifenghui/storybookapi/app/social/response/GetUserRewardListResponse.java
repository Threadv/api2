package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.entity.Award;

import java.util.List;

public class GetUserRewardListResponse extends ApiResponse {

    /**
     * 奖励
     */
    List<Award> awards;

    public List<Award> getAwards() {
        return awards;
    }

    public void setAwards(List<Award> awards) {
        this.awards = awards;
    }
}
