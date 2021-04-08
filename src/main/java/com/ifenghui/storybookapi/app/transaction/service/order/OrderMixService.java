package com.ifenghui.storybookapi.app.transaction.service.order;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.VPayOrder;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderStatusStyle;
import com.ifenghui.storybookapi.style.OrderStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderMixService{

    /**
     * 定时处理订单
     */
    void timingCancelOrder();
    /**
     * 创建订单
     * @param orderStyle
     * @param orderId
     * @param userId
     * @return
     */
    OrderMix addOrderMix(OrderStyle orderStyle,Integer orderId,Integer userId);

    /**
     * 修改订单状态
     * @param orderStyle
     * @param orderId
     * @param status
     * @return
     */
    OrderMix updateOrderMixStatus(OrderStyle orderStyle,Integer orderId,OrderStatusStyle status);

    /**
     * 获得订单列表
     * @param userId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<OrderMix> getListByStatus(Integer userId, Integer status,Integer pageNo,Integer pageSize);


    /**
     * 取消订单
     * @param userId
     * @param orderId
     * @param type
     * @throws ApiException
     */
    public void cancelOrder(Integer userId,Integer orderId,RechargeStyle type)throws ApiException;


    public void addDataToMix(OrderMix orderMix,RechargeStyle type);

    public OrderMix getUserOrderMixDetail(OrderStyle orderStyle, Integer orderId);

    public void deleteOrder(Integer userId,Integer orderId,RechargeStyle type) throws ApiException;

    void checkNeedGiveShareTradeMoney(OrderMix orderMix, OrderStyle orderStyle);

    OrderMix getOrderMixById(Integer id);

}
