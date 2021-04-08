package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeUser;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface CouponStoryExchangeUserService {

    /**
     * 领取故事兑换券
     * @param couponId
     * @param userId
     * @return
     */
    CouponStoryExchangeUser addCouponStoryExchangeUser(Integer couponId, Integer userId);

    /**
     * 获取过期时间
     * @param couponId
     * @return
     */
    Date getEndTimeByCouponId(Integer couponId);

    /**
     * 获取用户故事兑换优惠券列表
     * @param userId
     * @param isExpire
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<CouponStoryExchangeUser> getCouponStoryExchangeUserPageByUserIdAndIsExpire(Integer userId, Integer isExpire, Integer pageNo, Integer pageSize);

    /**
     * 获取用户故事可用兑换优惠券列表
     * @param userId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<CouponStoryExchangeUser> getCouponStoryExchangeUserPageByUserIdAndStatus(Integer userId, Integer status, Integer pageNo, Integer pageSize);
    /**
     * 定时处理过期的故事兑换券
     * @return
     */
    void dayTaskCheckExpireStoryCoupon();

    /**
     * 兑换券兑换故事
     * @param couponStoryExchangeUserId
     * @param storyId
     * @param userId
     */
    void useCouponStoryExchangeUser(Integer couponStoryExchangeUserId, Integer storyId, Long userId);

    /**
     * 验证故事兑换券是否可以使用并且获得故事兑换券数量
     * @param userId
     * @return
     */
    List<CouponStoryExchangeUser> checkStoryCouponAndGetCouponNum(Integer userId, Integer couponNum);

    /**
     * 获取用户拥有的故事兑换券券数量
     * @param userId
     * @return
     */
    Integer getUserHasStoryCouponNumber(Integer userId);

    /**
     * 获取用户需要使用的故事兑换券数量
     * @param userId
     * @param orderStyle
     * @param targetValue
     * @return
     */
    Integer getUserNeedUseStoryCouponNumber(Integer userId, OrderStyle orderStyle, Integer targetValue);

    /**
     *  赠送注册用户故事兑换券
     * @param userId
     * @param phone
     */
    void sendRegisterUserStoryCoupon(Integer userId, String phone);

    /**
     * 赠送活动故事兑换券
     * @param userId
     * @param sendCouponNum
     */
    void sendActivityStoryCoupon(Integer userId, Integer sendCouponNum);
}
