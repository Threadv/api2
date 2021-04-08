package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import java.util.List;

/**
 * @Date: 2018/11/13 16:00
 * @Description:
 */
public class StudyPlanOptionResponse extends BaseResponse {

    Integer isOption;

    List<WeekPlanStyle> weekPlanStyles;

    public Integer getIsOption() {
        return isOption;
    }

    public void setIsOption(Integer isOption) {
        this.isOption = isOption;
    }

    public List<WeekPlanStyle> getWeekPlanStyles() {
        return weekPlanStyles;
    }

    public void setWeekPlanStyles(List<WeekPlanStyle> weekPlanStyles) {
        this.weekPlanStyles = weekPlanStyles;
    }
}
