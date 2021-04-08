package com.ifenghui.storybookapi.app.studyplan.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.studyplan.entity.ParentsGuide;

/**
 * @program: api2
 * @description:
 * @author: wjs
 * @create: 2018-12-03 12:12
 **/
public class GetParentsGuideResponse extends ApiResponse {

    ParentsGuide parentsGuide;

    public ParentsGuide getParentsGuide() {
        return parentsGuide;
    }

    public void setParentsGuide(ParentsGuide parentsGuide) {
        this.parentsGuide = parentsGuide;
    }
}
