package com.ifenghui.storybookapi.app.social.service;

import java.util.List;

public class MonthDateSelect {

    Integer id;

    Integer age;

    Integer type;

    Integer isShow;

    List<GrowthDiaryMonthDate> growthDiaryMonthDateList;

    public MonthDateSelect(Integer id, Integer age, List<GrowthDiaryMonthDate> growthDiaryMonthDateList, Integer type){
        this.id = id;
        this.age = age;
        this.type = type;
        this.growthDiaryMonthDateList = growthDiaryMonthDateList;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<GrowthDiaryMonthDate> getGrowthDiaryMonthDateList() {
        return growthDiaryMonthDateList;
    }

    public void setGrowthDiaryMonthDateList(List<GrowthDiaryMonthDate> growthDiaryMonthDateList) {
        this.growthDiaryMonthDateList = growthDiaryMonthDateList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsShow() {
        Integer isShow = 0;
        for(GrowthDiaryMonthDate item : this.growthDiaryMonthDateList){
            if(item.getIsHasData().equals(1)){
                isShow = 1;
                break;
            }
        }
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }
}
