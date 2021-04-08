package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.Medal;

public class GetMedalByStoryIdResponse extends ApiResponse {

    Medal medal;

    public Medal getMedal() {
        return medal;
    }

    public void setMedal(Medal medal) {
        this.medal = medal;
    }
}
