package com.ifenghui.storybookapi.app.shop.service;

import com.ifenghui.storybookapi.app.shop.entity.ShopPayCallBackRecord;
import com.ifenghui.storybookapi.style.RechargePayStyle;

public interface ShopPayCallBackService {


    /**
     * 添加回调记录
     * @return
     */
    ShopPayCallBackRecord addPayCallbackRecord(ShopPayCallBackRecord CallbackRecord);

    public ShopPayCallBackRecord addShopPayCallBackRecord(String callBackMsg, RechargePayStyle rechargePayStyle, Integer orderId);

    /**
     * 订单id查找回调记录
     * @param orderId
     * @return
     */
    ShopPayCallBackRecord getPayCallbackRecordByOrderId(Integer orderId);
}
