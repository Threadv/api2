package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class CheckInResponse extends ApiResponse {

    Integer todayStar;

    Integer continueStar;

    Integer allStar;

    public Integer getTodayStar() {
        return todayStar;
    }

    public void setTodayStar(Integer todayStar) {
        this.todayStar = todayStar;
    }

    public Integer getContinueStar() {
        return continueStar;
    }

    public void setContinueStar(Integer continueStar) {
        this.continueStar = continueStar;
    }

    public Integer getAllStar() {
        return allStar;
    }

    public void setAllStar(Integer allStar) {
        this.allStar = allStar;
    }
}
