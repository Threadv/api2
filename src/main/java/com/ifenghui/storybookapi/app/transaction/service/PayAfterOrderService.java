package com.ifenghui.storybookapi.app.transaction.service;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.app.transaction.entity.PayAfterOrder;
import com.ifenghui.storybookapi.style.RechargeStyle;

public interface PayAfterOrderService {

    PayAfterOrder getPayAfterOrderByOrderId(Long orderId);//根据充值订单获取后置订单数据

    PayAfterOrder getPayAfterOrderByTypeAndPayOrderId(Integer type,Long payOrderId);//根据充值订单获取后置订单数据

    void cancelPayRechargeOrder(Integer orderId, RechargeStyle rechargeStyle);

    PayAfterOrder addPayAfterOrder(Long userId, Long orderId, RechargeStyle rechargeStyle, Long payOrderId);
}
