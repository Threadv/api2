package com.ifenghui.storybookapi.app.shop.service;

import com.ifenghui.storybookapi.app.shop.entity.ShopExpress;
import com.ifenghui.storybookapi.app.shop.response.GetExpressResponse;
import com.ifenghui.storybookapi.style.ExpressStatusStyle;
import com.ifenghui.storybookapi.style.ExpressStyle;
import com.ifenghui.storybookapi.style.OrderStyle;

public interface ShopExpressService {


    /**
     * 添加物流信息
     * @param orderId
     * @param receiver
     * @param phone
     * @param address
     * @param area
     * @param expressStyle
     * @param expressCode
     * @param expressStatus
     * @return
     */
    ShopExpress addExpress(Integer orderId, String receiver, String phone, String address, String  area, ExpressStyle expressStyle, String expressCode, ExpressStatusStyle expressStatus, OrderStyle orderStyle);

    /**
     * 订单id查询物流
     * @param orderId
     * @return
     */
    GetExpressResponse findExpressByOrderId(Integer orderId, OrderStyle orderStyle);


    ShopExpress findShopExpressByOrderId(Integer orderId, OrderStyle orderStyle);
}
