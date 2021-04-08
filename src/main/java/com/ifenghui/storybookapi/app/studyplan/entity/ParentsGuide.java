package com.ifenghui.storybookapi.app.studyplan.entity;

/**
 * @program: api2
 * @description: 周计划家长导读相关
 * @author: wjs
 * @create: 2018-12-03 12:05
 **/
public class ParentsGuide {

    Integer weekPlanTaskRelateId;

    /**
     * 导读页面地址
     */
    String url;

    public ParentsGuide() {
    }

    public Integer getWeekPlanTaskRelateId() {
        return weekPlanTaskRelateId;
    }

    public void setWeekPlanTaskRelateId(Integer weekPlanTaskRelateId) {
        this.weekPlanTaskRelateId = weekPlanTaskRelateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
