package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.social.service.MonthDateSelect;

import java.util.List;

public class GetMonthDataSelectListResponse extends ApiResponse {

    List<MonthDateSelect> monthDateSelectList;

    public List<MonthDateSelect> getMonthDateSelectList() {
        return monthDateSelectList;
    }

    public void setMonthDateSelectList(List<MonthDateSelect> monthDateSelectList) {
        this.monthDateSelectList = monthDateSelectList;
    }
}
