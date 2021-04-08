package com.ifenghui.storybookapi.app.studyplan.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanIntro;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

import java.util.Date;
import java.util.List;

public class GetWeekPlanIntroPageResponse extends ApiPageResponse {

    List<WeekPlanIntro> weekPlanIntroList;

    /**是否达到参加计划的下周*/
    Integer isAchieve;

    Integer planChangeCount;

    Integer isHasSecond;

    Integer isNeedShow;

    WeekPlanStyle weekPlanStyle;

    Date weekPlanEndTime;

    /**已完成周数*/
    Integer finishWeekNum;

    Integer allWeekNum;

    /**周计划描述*/
    String content;

    /** 是否领取全部免费课程 2.10.0 */
    Integer isGetFreeAll;

    public Date getWeekPlanEndTime() {
        return weekPlanEndTime;
    }

    public void setWeekPlanEndTime(Date weekPlanEndTime) {
        this.weekPlanEndTime = weekPlanEndTime;
    }

    public Integer getIsHasSecond() {
        return isHasSecond;
    }

    public void setIsHasSecond(Integer isHasSecond) {
        this.isHasSecond = isHasSecond;
    }

    public Integer getPlanChangeCount() {
        return planChangeCount;
    }

    public void setPlanChangeCount(Integer planChangeCount) {
        this.planChangeCount = planChangeCount;
    }

    public WeekPlanStyle getWeekPlanStyle() {
        return weekPlanStyle;
    }

    public void setWeekPlanStyle(WeekPlanStyle weekPlanStyle) {
        this.weekPlanStyle = weekPlanStyle;
    }

    public List<WeekPlanIntro> getWeekPlanIntroList() {
        return weekPlanIntroList;
    }

    public void setWeekPlanIntroList(List<WeekPlanIntro> weekPlanIntroList) {
        this.weekPlanIntroList = weekPlanIntroList;
    }

    public Integer getIsAchieve() {
        return isAchieve;
    }

    public void setIsAchieve(Integer isAchieve) {
        this.isAchieve = isAchieve;
    }

    public Integer getIsNeedShow() {
        return isNeedShow;
    }

    public void setIsNeedShow(Integer isNeedShow) {
        this.isNeedShow = isNeedShow;
    }

    public Integer getFinishWeekNum() {
        return finishWeekNum;
    }

    public void setFinishWeekNum(Integer finishWeekNum) {
        this.finishWeekNum = finishWeekNum;
    }

    public Integer getAllWeekNum() {
        return allWeekNum;
    }

    public void setAllWeekNum(Integer allWeekNum) {
        this.allWeekNum = allWeekNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsGetFreeAll() {
        return isGetFreeAll;
    }

    public void setIsGetFreeAll(Integer isGetFreeAll) {
        this.isGetFreeAll = isGetFreeAll;
    }

    @JsonProperty("planIntroUrl")
    public String getPlanIntroUrl() {
        return "http://storybook.oss-cn-hangzhou.aliyuncs.com/web_project/younengjihua/index.html";
    }
}
