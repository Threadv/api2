package com.ifenghui.storybookapi.app.transaction.service.order;

import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.RechargeStyle;

import java.util.List;

public interface PaySubscriptionOrderService  extends OrderPayProcess{

    /**
     * 获得订阅数量
     * @param month
     * @param userId
     * @return
     */
    Integer getPaySubscriptionOrderTimes(Integer month, Long userId);

    /**
     * 取消订阅订单
     * @param paySubscriptionOrder
     * @param orderId
     * @param rechargeStyle
     */
    void cancelPaySubscriptionOrder(PaySubscriptionOrder paySubscriptionOrder, Long orderId, RechargeStyle rechargeStyle);


    List<SubscriptionRecord> getSubscriptionRecordByUserId(User user);

}
