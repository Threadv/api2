package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.transaction.response.BuyOrderByBalanceResponse;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.springframework.transaction.annotation.Transactional;

/**
 * 单本故事购买服务
 */
public interface BuyActivityGoodsService {


    /**
     * 余额购买故事，用户直接余额购买和后置充值操作
     * @param userId
     * @param orderId
     * @param walletStyle
     * @return
     * @throws ApiException
     */
    @Transactional
    BuyOrderByBalanceResponse buyActivityGoodsByBalance(Long userId, Long orderId, OrderPayStyle payStyle, WalletStyle walletStyle)throws ApiException;


}
