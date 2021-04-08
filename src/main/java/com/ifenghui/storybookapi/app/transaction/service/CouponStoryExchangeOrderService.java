package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeOrder;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeUser;
import com.ifenghui.storybookapi.style.OrderStyle;

import java.util.List;

public interface CouponStoryExchangeOrderService {


    /**
     * 添加故事兑换券与订单关联
     * @param userId
     * @param orderId
     * @param couponId
     * @param orderStyle
     * @return
     */
    CouponStoryExchangeOrder addCouponStoryExchangeOrder(Integer userId, Integer orderId, Integer couponId, OrderStyle orderStyle);

    /**
     * 添加故事兑换券与订单关联通过兑换券列表
     * @param couponStoryExchangeUserList
     * @param orderId
     * @param orderStyle
     */
    void addCouponStoryExchangeOrderByList(List<CouponStoryExchangeUser> couponStoryExchangeUserList, Integer orderId, OrderStyle orderStyle);
}
