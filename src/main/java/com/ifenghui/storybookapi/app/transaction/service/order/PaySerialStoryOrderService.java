package com.ifenghui.storybookapi.app.transaction.service.order;

import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.user.entity.User;

public interface PaySerialStoryOrderService  extends OrderPayProcess{

    PaySerialStoryOrder addPaySerialStoryOrder(
            User user,
            Integer originalPrice,
            Integer amount,
            Integer couponAmount,
            Integer serialStoryId,
            Integer userDiscount,
            String code,
            String channel,
            Integer couponNum
    );

    void isCanCreatePaySerialStoryOrder(Integer userId, Integer serialStoryId);

    PaySerialStoryOrder getPaySerialStoryOrderById(Integer orderId);

}
