package com.ifenghui.storybookapi.app.transaction.response.lesson;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonOrder;

public class GetPayLessonOrderResponse extends ApiResponse {

    PayLessonOrder payLessonOrder;

    public PayLessonOrder getPayLessonOrder() {
        return payLessonOrder;
    }

    public void setPayLessonOrder(PayLessonOrder payLessonOrder) {
        this.payLessonOrder = payLessonOrder;
    }
}
