package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.transaction.response.BuyOrderByBalanceResponse;
import com.ifenghui.storybookapi.app.wallet.response.BuyStorysResponse;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.RechargePayStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 单本故事购买服务
 */
public interface BuyStoryService {

    /**
     * 使用兑换码兑换故事
     * @param userId
     * @param code
     * @return
     * @throws ApiException
     */
    Story buyStoryByCode(Long userId, String code)throws ApiException;

    /**
     * 生成故事订单
     * @param userId 用户id
     * @param storyIdsStr 故事id列表
     * @return
     * @throws ApiException
     */
    PayStoryOrder createBuyStorysOrder(Long userId, String storyIdsStr, List<Integer> couponIds)throws ApiException;

    /**
     * 余额购买故事，用户直接余额购买和后置充值操作
     * @param userId
     * @param orderId
     * @param walletStyle
     * @return
     * @throws ApiException
     */
    @Transactional
    BuyOrderByBalanceResponse buyStorysByBalance(Long userId, Long orderId, OrderPayStyle payStyle, WalletStyle walletStyle)throws ApiException;

    /**
     * 购买故事通过故事兑换券兑换
     * @param userId
     * @param storyIdsStr
     * @return
     */
    BuyOrderByBalanceResponse buyStorysByStoryCoupon(Long userId, String storyIdsStr);
}
