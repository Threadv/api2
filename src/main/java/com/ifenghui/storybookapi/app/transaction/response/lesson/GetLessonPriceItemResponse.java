package com.ifenghui.storybookapi.app.transaction.response.lesson;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonPrice;

public class GetLessonPriceItemResponse extends ApiResponse {

    PayLessonPrice payLessonPrice;

    public PayLessonPrice getPayLessonPrice() {
        return payLessonPrice;
    }

    public void setPayLessonPrice(PayLessonPrice payLessonPrice) {
        this.payLessonPrice = payLessonPrice;
    }
}
