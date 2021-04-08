package com.ifenghui.storybookapi.app.wallet.service;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByBalanceResponse;

import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargePrice;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PayService {

//    PayRechargeOrder updateRechargePayOrder(PayRechargeOrder payRechargeOrder);


//    /**
//     * 充值订单定时取消
//     */
//    void configCancelSubscriptionOrder();


    /**
     * 余额处理订单
     * @param orderId 创建故事或订阅的订单号
     * @param userId
     * @param orderPayStyle
     * @throws ApiException
     */
    SubscribeByBalanceResponse subscribeByBalance(Long orderId, Long userId, OrderPayStyle orderPayStyle, WalletStyle walletStyle)throws ApiException;

//    /**
//     * 充值回调处理
//     * @param afterOrder
//     * @param userId
//     * @param buySourceType
//     * @return
//     * @throws ApiException
//     */
//    SubscribeByBalanceResponse subscribeByBalance(PayAfterOrder afterOrder, Long userId, Integer buySourceType)throws ApiException;

    String subscribeByCode(Long userId,String code,Integer month)throws ApiException;
//
//
//    String buySerialStoryByCode(Long userId,String code,Integer serialStoryId)throws ApiException;


//    String buyStoryByCode(Long userId,String code)throws ApiException;

//    /**
//     * 生成故事订单
//     * @param userId 用户id
//     * @param storyIdsStr 故事id列表
//     * @return
//     * @throws ApiException
//     */
//    PayStoryOrder createPayStoryOrder(Long userId, String storyIdsStr)throws ApiException;

//    @Transactional
//    BuyStorysResponse buyStorysByBalance(Long userId, String orderCode,WalletStyle walletStyle)throws ApiException;
    /**
     * 余额购买故事集
     * @param userId
     * @param orderCode
     * @return
     * @throws ApiException
     */
//    @Transactional
//    BuySerialStoryResponse buySerialStoryByBalance(Long userId, String orderCode,Integer buyType,WalletStyle walletStyle)throws ApiException;

    /**
     * 创建充值订单，代替getPayRechargeOrder
     * @param userId
     * @param payOrderId
     * @param type
     * @param priceId
     * @param channel
     * @return
     * @throws ApiException
     */
    PayRechargeOrder addPayRechargeOrder(Long userId, Long payOrderId,RechargeStyle type,Long priceId,String channel,String app_id,String private_key,String notify_url,String appName)throws ApiException;

    PayRechargeOrder getPayRechargeOrderByTradeNo(String tradeNo);

    /**
     * 充值定单id
     * @param rechargeOrderId
     * @return
     */
    PayRechargeOrder getPayRechargeOrder(Long rechargeOrderId);

    Page<PayRechargePrice> getRechargePrice(WalletStyle walletStyle);
    PayRechargePrice getPayRechargePriceById(Long id);

    /**
     * 通过ios product id获得购买信息
     * @param iap
     * @return
     */
    PayRechargePrice getPayRechargePriceByIap(String iap);
}
