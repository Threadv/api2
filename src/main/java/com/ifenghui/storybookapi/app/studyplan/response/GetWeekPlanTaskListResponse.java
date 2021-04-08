package com.ifenghui.storybookapi.app.studyplan.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanMagazine;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskRelate;

import java.util.List;

public class GetWeekPlanTaskListResponse extends ApiPageResponse {

    Integer finishNum;

    Integer isNeedShowStar;

    List<WeekPlanTaskRelate> weekPlanTaskRelateList;

    List<WeekPlanMagazine> weekPlanMagazineList;

    public List<WeekPlanTaskRelate> getWeekPlanTaskRelateList() {
        return weekPlanTaskRelateList;
    }

    public void setWeekPlanTaskRelateList(List<WeekPlanTaskRelate> weekPlanTaskRelateList) {
        this.weekPlanTaskRelateList = weekPlanTaskRelateList;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public List<WeekPlanMagazine> getWeekPlanMagazineList() {
        return weekPlanMagazineList;
    }

    public void setWeekPlanMagazineList(List<WeekPlanMagazine> weekPlanMagazineList) {
        this.weekPlanMagazineList = weekPlanMagazineList;
    }

    public Integer getIsNeedShowStar() {
        return isNeedShowStar;
    }

    public void setIsNeedShowStar(Integer isNeedShowStar) {
        this.isNeedShowStar = isNeedShowStar;
    }
}
