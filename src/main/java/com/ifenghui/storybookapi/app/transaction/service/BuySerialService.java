package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.response.BuyOrderByBalanceResponse;
import com.ifenghui.storybookapi.app.wallet.response.BuySerialStoryResponse;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 单本故事购买服务
 */
public interface BuySerialService {


    /**
     * 券购买系列
     * @param userId
     * @param code
     * @param serialStoryId
     * @return
     * @throws ApiException
     */
    String buySerialStoryByCode(Long userId, String code,Integer serialStoryId)throws ApiException;

    /**
     * 余额购买故事集
     * @param userId
     * @param orderId
     * @return
     * @throws ApiException
     */
    @Transactional
    BuyOrderByBalanceResponse buySerialStoryByBalance(Long userId, Long orderId, OrderPayStyle payStyle, WalletStyle walletStyle)throws ApiException;

    /**
     * 故事兑换券购买故事集
     * @param userId
     * @param serialStoryId
     * @return
     * @throws ApiException
     */
    @Transactional
    BuyOrderByBalanceResponse buySerialStoryByStoryCoupon(Integer userId, Integer serialStoryId) throws ApiException;

    /**
     * 获取购买故事集订单
     * @param serialStoryId,userId,couponIdsStr,
     * @return
     * @throws ApiException
     */
    PaySerialStoryOrder getBuySerialStoryOrder(Long serialStoryId, Long userId, List<Integer> couponIds)throws ApiException;

    /**
     * 取消系列订单
     * @param paySerialStoryOrder
     * @param orderId
     */
    void cancelBuySerialStoryOrder(PaySerialStoryOrder paySerialStoryOrder,Long orderId);
}
