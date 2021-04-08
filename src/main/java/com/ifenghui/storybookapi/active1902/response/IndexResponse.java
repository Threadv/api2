package com.ifenghui.storybookapi.active1902.response;


import com.ifenghui.storybookapi.active1902.entity.Schedule;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

/**
 * @Date: 2019/2/19 15:08
 * @Description:
 */
public class IndexResponse extends ApiResponse {


    Schedule schedule;
    Integer isGetStory;
    Integer isGetSerialStory;
    //1 故事 2 故事集
    Integer awardsStyle;

    Integer isAbil;

    public Integer getIsAbil() {
        return isAbil;
    }

    public void setIsAbil(Integer isAbil) {
        this.isAbil = isAbil;
    }

    public Integer getAwardsStyle() {
        return awardsStyle;
    }

    public void setAwardsStyle(Integer awardsStyle) {
        this.awardsStyle = awardsStyle;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Integer getIsGetStory() {
        return isGetStory;
    }

    public void setIsGetStory(Integer isGetStory) {
        this.isGetStory = isGetStory;
    }

    public Integer getIsGetSerialStory() {
        return isGetSerialStory;
    }

    public void setIsGetSerialStory(Integer isGetSerialStory) {
        this.isGetSerialStory = isGetSerialStory;
    }
}
