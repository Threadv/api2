package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.Coupon;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponGetRecord;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeUser;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import com.ifenghui.storybookapi.app.transaction.entity.VCouponMix;
import com.ifenghui.storybookapi.exception.ApiException;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by wml on 2017/05/18.
 */
public interface CouponService {

    /**
     * 获取用户优惠券列表
     * @param userId
     * @param type
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<CouponUser> getUserCoupons(Long userId,Integer type, Integer pageNo, Integer pageSize);

    /**
     * 获取用户已过期券列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<VCouponMix> getExpiredMixCoupons(Long userId,Integer pageNo, Integer pageSize);

    Page<VCouponMix> getExpiredMixCoupons(VCouponMix vCouponMix,Integer pageNo,Integer pageSize);

    /**
     * 获取用户未读优惠券列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<CouponUser> getUserUnReacCoupons(Long userId, Integer pageNo, Integer pageSize);

    /**
     * 获取用户可用优惠券列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @param priceId
     * @param type
     * @return
     */
    Page<CouponUser> getUserValidityCoupons(Long userId, Integer pageNo, Integer pageSize,Long priceId,Integer type);

    /**
     * 根据价格获取用户可用优惠券列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @param price
     * @return
     */
    Page<CouponUser> getUserValidCouponsByPrice(Long userId, Integer pageNo, Integer pageSize, Integer price);

    /**
     * 根据价格得到用户可用券的数量
     * @param userId
     * @param price
     * @return
     */
    Integer getCashCouponNumber(Long userId, Integer price);

    /**
     * 获得用户可用券的数量
     * @param userId
     * @return
     */
    Integer getUserValidityCouponsCount(Long userId);//可用数量

    /**
     * 获取未过期券数量
     * @param userId
     * @return
     */
    Integer getUserCouponCount(Long userId);//未过期数量

    /**
     * 获取券与用户关联
     * @param userId
     * @param couponId
     * @return
     */
    CouponUser getByUserIdAndCouponId(Long userId, Long couponId);

    CouponUser getUserCouponById(Long id);

    Coupon getCouponById(Long id);

    /**
     * 获取券的过期时间
     * @param nowTime
     * @param coupon
     * @return
     */
    Date getCouponEndTime(Date nowTime,Coupon coupon);

    /**
     * 分享获得优惠券
     * @param userId
     * @return
     * @throws ApiException
     */
    CouponUser getCouponByShare(Long userId)throws ApiException;

    /**
     * 分享获得故事兑换券
     * @param userId
     * @return
     */
    List<CouponStoryExchangeUser> getStoryCouponByShare(Long userId);

    /**
     * 领取优惠券,分享大使使用，用于记录被分享人的手机号
     * @param userId
     * @param phone
     * @return
     * @throws ApiException
     */
    Void acceptCoupon(Long userId,String phone) throws ApiException;

    /**
     * 注册送券
     * @param userId
     * @param phone
     * @return
     */
    Void getCouponByRegsiter(Long userId,String phone);

    /**
     * 领取代金券
     * @param userId
     * @param couponId
     * @param channel
     * @return
     * @throws ApiException
     */
    Void collectCoupon(Long userId, Long couponId,String channel) throws ApiException;

    /**
     * 获取用户券关联详情
     * @param couponUserId
     * @return
     */
    CouponUser getCouponUserById(Long couponUserId);

    /**
     * 普通用户注册送券
     * @param userId
     * @param phone
     */
    public void registerSendCoupon(Long userId, String phone);

    CouponUser addCouponUser(Integer couponId, Long userId, Integer status, Date endTime, Date createTime, String phone, Integer isView, String channel);

    /**
     * 查找用户优惠券领取记录
     * @param phone
     * @return
     */
    CouponGetRecord getCouponGetRecord(String phone);
}
