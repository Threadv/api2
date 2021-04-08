package com.ifenghui.storybookapi.app.social.response;


import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

public class GetUserStarRuleResponse extends ApiResponse {

    Integer totalStar;

    Integer todayStar;

    List<StarRule> starRuleList;

    Integer continueCheckIn;

    Integer hasCheckIn;

    public Integer getTotalStar() {
        return totalStar;
    }

    public void setTotalStar(Integer totalStar) {
        this.totalStar = totalStar;
    }

    public Integer getTodayStar() {
        return todayStar;
    }

    public void setTodayStar(Integer todayStar) {
        this.todayStar = todayStar;
    }

    public List<StarRule> getStarRuleList() {
        return starRuleList;
    }

    public void setStarRuleList(List<StarRule> starRuleList) {
        this.starRuleList = starRuleList;
    }

    public Integer getContinueCheckIn() {
        return continueCheckIn;
    }

    public void setContinueCheckIn(Integer continueCheckIn) {
        this.continueCheckIn = continueCheckIn;
    }

    public Integer getHasCheckIn() {
        return hasCheckIn;
    }

    public void setHasCheckIn(Integer hasCheckIn) {
        this.hasCheckIn = hasCheckIn;
    }
}
