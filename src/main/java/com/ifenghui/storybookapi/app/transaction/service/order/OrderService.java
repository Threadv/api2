package com.ifenghui.storybookapi.app.transaction.service.order;

/**
 * Created by jia on 2016/12/28.
 */
import com.ifenghui.storybookapi.app.transaction.entity.*;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;

import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

//    PayOrder addPayOrder(PayOrder payOrder);
    PayRechargeOrder addPayRechargeOrder(PayRechargeOrder payRechargeOrder);
//    PayOrder getPayOrder(Long id);
//    PayOrder getPayOrderByOrderCode(String orderCode);
//    PayOrder getPayOrderByUserIdAndPayTarget(Long userId ,Long payTarget);
//    PayOrder getPayOrderByUserIdAndPayTargetAndStatus(Long userId ,Long payTarget,Integer status);
    List<PayRechargeOrder> getPayRechargeOrderByUserIdAndOrderCodeAndBuyType(Long userId ,String orderCode, Integer status,Integer buyType);
//    PayOrder updatePayOrder(PayOrder payOrder);
//    Page<PayOrder> getPayOrdersByPage(Integer pageNo, Integer pageSize);
//    List<PayOrder> getPayOrdersByUser(Long userId, Integer pageNo, Integer pageSize);
//    List<PayOrder> getPayOrdersByWX(Long userId, Integer pageNo, Integer pageSize);


//    /**
//     * 获取购买故事集订单
//     * @param serialStoryId,userId,couponIdsStr,
//     * @return
//     * @throws ApiException
//     */
//    PaySerialStoryOrder getBuySerialStoryOrder(Long serialStoryId, Long userId, String couponIdsStr)throws ApiException;



    /**
     * 取消订单
     * @param userId
     * @param orderId
     * @param type
     * @return
     * @throws ApiException
     */
    void cancelUserPayOrder(Long userId,Long orderId, RechargeStyle type)throws ApiException;

//    void cancelBuySerialStoryOrder(PaySerialStoryOrder paySerialStoryOrder,Long orderId);


    Page<VPayOrder> getUserPayOrder(Long userId, Integer status, Integer pageNo, Integer pageSize);

    /**
     * 获得混合确认订单详情
     * @param orderId
     * @param type
     * @return
     */
    VPayOrder getUserPayOrderDetail(Long orderId,Integer type);

    void delUserPayOrder(Long userId,Long orderId,Integer type)throws ApiException;

    PaySubscriptionOrder getSubscriptionOrder(Long priceId, Long userId, List<Integer> couponIds, Long couponDeferredId)throws ApiException;

    List<PaySubscriptionOrder> isUserSubscribed(Long userId);

    public void checkIsCanCreateOrder(Integer userId, Integer targetValue, OrderStyle orderStyle);

}
