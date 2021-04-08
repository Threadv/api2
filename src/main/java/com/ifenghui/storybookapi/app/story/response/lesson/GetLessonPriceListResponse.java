package com.ifenghui.storybookapi.app.story.response.lesson;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonPrice;

import java.util.List;

public class GetLessonPriceListResponse extends ApiResponse {

    List<PayLessonPrice> payLessonPriceList;

    Integer  isBuyAbilityPlan;

    public Integer getIsBuyAbilityPlan() {
        return isBuyAbilityPlan;
    }

    public void setIsBuyAbilityPlan(Integer isBuyAbilityPlan) {
        this.isBuyAbilityPlan = isBuyAbilityPlan;
    }

    public List<PayLessonPrice> getPayLessonPriceList() {
        return payLessonPriceList;
    }

    public void setPayLessonPriceList(List<PayLessonPrice> payLessonPriceList) {
        this.payLessonPriceList = payLessonPriceList;
    }
}
