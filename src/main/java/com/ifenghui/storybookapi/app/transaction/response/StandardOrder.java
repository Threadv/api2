package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.app.transaction.dao.PayStoryOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonOrder;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.vip.PayVipOrder;
import com.ifenghui.storybookapi.style.OrderStyle;

public class StandardOrder {

    Integer orderId;

    Integer amount;

    OrderStyle orderStyle;

    Integer type;

    Integer mixOrderId;

    public Integer getMixOrderId() {
        return mixOrderId;
    }

    public void setMixOrderId(Integer mixOrderId) {
        this.mixOrderId = mixOrderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public OrderStyle getOrderStyle() {
        return orderStyle;
    }

    public void setOrderStyle(OrderStyle orderStyle) {
        this.orderStyle = orderStyle;
        this.type = orderStyle.getId();
    }

    public StandardOrder(AbilityPlanOrder order){
        this.orderStyle = OrderStyle.ABILITY_PLAN_ORDER;
        this.amount = order.getAmount();
        this.orderId = order.getId();
        this.mixOrderId = order.getMixOrderId();
    }

    public StandardOrder(PayLessonOrder order){
        this.orderStyle = OrderStyle.LESSON_ORDER;
        this.amount = order.getAmount();
        this.orderId = order.getId();
        this.mixOrderId = order.getMixOrderId();
    }

    public StandardOrder(PayVipOrder order){
        this.orderStyle = OrderStyle.VIP_ORDER;
        this.amount = order.getAmount();
        this.orderId = order.getId();
        this.mixOrderId = order.getMixOrderId();
    }

    public StandardOrder(PayStoryOrder order){
        this.orderStyle = OrderStyle.STORY_ORDER;
        this.amount = order.getAmount();
        this.orderId = order.getId().intValue();
        this.mixOrderId = order.getMixOrderId();
    }

    public StandardOrder(PaySerialStoryOrder order){
        this.orderStyle = OrderStyle.SERIAL_ORDER;
        this.amount = order.getAmount();
        this.orderId = order.getId();
        this.mixOrderId = order.getMixOrderId();
    }

    public Integer getType() {
        this.type = this.orderStyle.getId();
        return type;
    }
}
