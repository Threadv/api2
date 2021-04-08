package com.ifenghui.storybookapi.app.presale.service;


import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;

public interface SubscribeService {


    /**
     * 查询未过期的订阅用户
     * @param userId
     * @return
     */
    SubscriptionRecord getStatus(Integer userId);
}
