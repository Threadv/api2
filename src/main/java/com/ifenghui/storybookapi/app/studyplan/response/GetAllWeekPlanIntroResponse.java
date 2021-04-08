package com.ifenghui.storybookapi.app.studyplan.response;/**
 * @Date: 2018/12/13 14:54
 * @Description:
 */

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import java.util.List;

/**
 * @Date: 2018/12/13 14:54
 * @Description:
 */
public class GetAllWeekPlanIntroResponse extends ApiPageResponse {


    List<GetWeekPlanIntroPageResponse> weekPlanIntroPageResponseList;

    WeekPlanStyle weekPlanStyle;

    public List<GetWeekPlanIntroPageResponse> getWeekPlanIntroPageResponseList() {
        return weekPlanIntroPageResponseList;
    }

    public void setWeekPlanIntroPageResponseList(List<GetWeekPlanIntroPageResponse> weekPlanIntroPageResponseList) {
        this.weekPlanIntroPageResponseList = weekPlanIntroPageResponseList;
    }

    public WeekPlanStyle getWeekPlanStyle() {
        return weekPlanStyle;
    }

    public void setWeekPlanStyle(WeekPlanStyle weekPlanStyle) {
        this.weekPlanStyle = weekPlanStyle;
    }

}
