package com.ifenghui.storybookapi.exception;

import com.ifenghui.storybookapi.app.transaction.entity.OrderStory;

import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.VPayOrder;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonOrder;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.response.StandardOrder;


/**
 * 有重复订单故事
 * Created by wml on 2017/5/23
 */
public class ApiStoryOrderRepeatException extends ApiException {

    VPayOrder vPayOrder;

    StandardOrder standardOrder;

    public ApiStoryOrderRepeatException(String apimsg){
        super(ExceptionStyle.ORDER_DUPLICATE_EXCEPTION,apimsg);

    }
//    public ApiStoryOrderRepeatException(String apimsg, int code){
//        this.apimsg=apimsg;
//        this.apicode=code;
//        this.vPayOrder=vPayOrder;
//    }
    public ApiStoryOrderRepeatException(PayStoryOrder findPayStoryOrder){
        super(ExceptionStyle.ORDER_DUPLICATE_EXCEPTION,"");
        vPayOrder=new VPayOrder(findPayStoryOrder);
        standardOrder = new StandardOrder(findPayStoryOrder);

    }
    public ApiStoryOrderRepeatException(OrderStory orderStory){
        super(ExceptionStyle.ORDER_DUPLICATE_EXCEPTION,"");
        vPayOrder=new VPayOrder(orderStory);


    }

    public ApiStoryOrderRepeatException(PaySerialStoryOrder paySerialStoryOrder, String apimsg){
        super(ExceptionStyle.ORDER_DUPLICATE_EXCEPTION,apimsg);
        vPayOrder = new VPayOrder(paySerialStoryOrder);
        standardOrder = new StandardOrder(paySerialStoryOrder);
    }

    public ApiStoryOrderRepeatException(PayLessonOrder payLessonOrder, String apimsg){
        super(ExceptionStyle.ORDER_DUPLICATE_EXCEPTION,apimsg);
        vPayOrder = new VPayOrder(payLessonOrder);
        standardOrder = new StandardOrder(payLessonOrder);
    }



    public VPayOrder getvPayOrder() {
        return vPayOrder;
    }

    public void setvPayOrder(VPayOrder vPayOrder) {
        this.vPayOrder = vPayOrder;
    }

    public StandardOrder getStandardOrder() {
        return standardOrder;
    }

    public void setStandardOrder(StandardOrder standardOrder) {
        this.standardOrder = standardOrder;
    }
}
