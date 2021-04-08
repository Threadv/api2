package com.ifenghui.storybookapi.app.social.response;

/**
 * Created by jia on 2016/12/22.
 */

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.story.entity.Medal;

import java.util.List;

public class GetAchievementHandbookResponse extends ApiPageResponse {
    Integer finishCount;
    String title;
    String intro;
    List<Medal> medals;

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<Medal> getMedals() {
        return medals;
    }

    public void setMedals(List<Medal> medals) {
        this.medals = medals;
    }
}
