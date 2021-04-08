package com.ifenghui.storybookapi.app.transaction.service.order;

import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.OrderPayStyle;

import javax.transaction.Transactional;

public interface PayStoryOrderService extends OrderPayProcess{

    /**
     * 获得用户单本购买次数
     * @param price
     * @param userId
     * @return
     */
    Integer getStoryOrderTimes(Integer price, Long userId);

    void cancelPayStoryOrder(PayStoryOrder payStoryOrder, Long orderId);

    @Transactional
    PayStoryOrder addPayStoryOrder(User user, Integer originalPrice, Integer couponAmount, Integer amount, Integer num, OrderPayStyle payStyle, Integer userDiscount, String channel);

    PayStoryOrder getPayStoryOrderById(Long Id);
}
