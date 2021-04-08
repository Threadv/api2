package com.ifenghui.storybookapi.app.transaction.service.order;

import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.OrderPayActivity;

/**
 * 子订单需要实现的方法
 *
 * 除了创建订单，需要实现，
 * 取消订单，
 * 删除订单，
 * 修改订单状态
 */
public interface OrderPayProcess {

    /**
     * 取消订单

     * @param orderId
     */
    void cancelOrder(Integer orderId) throws Exception;

    /**
     * 设置数据到总订单上
     * @param orderMix
     */
    void setDataToOrderMix(OrderMix orderMix);

//    /**
//     * 修改订单状态，比如已完成
//     * @param orderId
//     * @param orderId
//     */
//    void setOrderStatus(Integer orderId, Integer status);

    void deleteOrder(Integer orderId);

    /**
     * 返回用于分享好友产生订单的总额
     * 如果使用过优惠券或者使用过兑换码那么返回0，否则返回订单金额
     * 如果这个类型的订单不参与抽成返回0
     * @param orderId 订单id
     * @return
     */
    Integer getShareTradeAmount(Integer orderId);

}
