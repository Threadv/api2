package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.transaction.dao.CouponDeferredDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponDeferredUserDao;
import com.ifenghui.storybookapi.app.transaction.dao.PaySubscriptionPriceDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferred;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredUser;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionPrice;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.app.transaction.service.CouponDeferredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by wml on 2017/06/22
 */
@Transactional
@Component
public class CouponDeferredServiceImpl implements CouponDeferredService{

    @Autowired
    CouponDeferredUserDao couponDeferredUserDao;
    @Autowired
    CouponDeferredDao couponDeferredDao;
    @Autowired
    PaySubscriptionPriceDao paySubscriptionPriceDao;

    @Autowired
    UserDao userDao;
    @Override
    public Page<CouponDeferredUser> getUserDeferredCoupons(Long userId, Integer type, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Date nowTime = new  Date();
        Page<CouponDeferredUser> couponDeferredUserPage;
        if(type==1){
            //未过期
            couponDeferredUserPage = couponDeferredUserDao.getUserDeferredCoupons(userId,nowTime,pageable);
        }else{
            //过期
            couponDeferredUserPage = couponDeferredUserDao.getExpiredUserDeferredCoupons(userId,nowTime,pageable);
        }
        CouponDeferred couponDeferred;
        for (CouponDeferredUser item:couponDeferredUserPage.getContent()){
            couponDeferred = couponDeferredDao.findOne(item.getCouponId());
            item.setName(couponDeferred.getName());
            item.setContent(couponDeferred.getContent());
            item.setValidTime(couponDeferred.getValidTime());
            item.setIsUsable(1);
        }
        return couponDeferredUserPage;
    }
    @Override
    public Integer getUserValidityDeferredCouponsCount(Long userId) {
        Date nowTime = new Date();
        Integer count = couponDeferredUserDao.getUserValidityDeferredCouponsCount(userId,nowTime);
        return count;
    }
    @Override
    public Integer getUserDeferredCouponsCount(Long userId) {
        Date nowTime = new Date();
        Integer count = couponDeferredUserDao.getUserDeferredCouponsCount(userId,nowTime);
        return count;
    }
//    @Override
//    public Page<CouponUser> getUserUnReacCoupons(Long userId, Integer pageNo, Integer pageSize) {
//        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
//        Date nowTime = new  Date();
//        Page<CouponUser> couponUserPage;
//
//        //未读
//        couponUserPage = couponUserDao.getUserUnReadCoupons(userId,pageable);
//
//        Coupon coupon;
//        for (CouponUser item:couponUserPage.getContent()){
//            item.setIsView(1);
//            couponUserDao.save(item);//改为已读
//            //获取优惠券数据
//            coupon = couponDao.findOne(item.getCouponUserId());
//            item.setName(coupon.getName());
//            item.setContent(coupon.getContent());
//            item.setAmount(coupon.getAmount());
//        }
//        return couponUserPage;
//    }
//    @Override
//    public Page<CouponUser> getUserValidityCoupons(Long userId, Integer pageNo, Integer pageSize) {
//        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
//        Date nowTime = new  Date();
//
//        //未过期,且未使用
//        Page<CouponUser> couponUserPage = couponUserDao.getUserValidityCoupons(userId,nowTime,pageable);
//
//        Coupon coupon;
//        for (CouponUser item:couponUserPage.getContent()){
//            coupon = couponDao.findOne(item.getCouponUserId());
//            item.setName(coupon.getName());
//            item.setContent(coupon.getContent());
//            item.setAmount(coupon.getAmount());
//        }
//        return couponUserPage;
//    }
//    @Override
//    public Integer getUserValidityCouponsCount(Long userId) {
//        Date nowTime = new Date();
//        Integer count = couponUserDao.getUserValidityCouponsCount(userId,nowTime);
//        return count;
//    }
//    public Integer getUserCouponCount(Long userId) {
//        Date nowTime = new Date();
//        Integer count = couponUserDao.getUserCouponsCount(userId,nowTime);
//        return count;
//    }
//    @Override
//    public CouponUser getByUserIdAndCouponId(Long userId, Long couponId) {
//        CouponUser couponUser = couponUserDao.getByUserIdAndCouponId(userId,couponId);//2,代表分享要领取的优惠券
//        return couponUser;
//    }
//
//    @Override
//    public CouponUser getUserCouponById(Long id) {
//        CouponUser couponUser = couponUserDao.findOne(id);
//        return couponUser;
//    }
//    @Override
//    public Coupon getCouponById(Long id) {
//        Coupon coupon = couponDao.findOne(id);
//        return coupon;
//    }
    public Date getCouponEndTime(Date nowTime,CouponDeferred coupon) {
        Date endTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, coupon.getValidTime());//几个月
        Date time = calendar.getTime();
        Long endTimeStr = time.getTime();
        endTime = new Date(endTimeStr);

        return endTime;
    }
    @Override
    public Void collectDeferredCoupon(Long userId,Long couponId,String channel) throws ApiException{
        //判断这个用户是否领取过此赠阅券
        CouponDeferredUser couponDeferredUserFind=new CouponDeferredUser();
        couponDeferredUserFind.setUserId(userId);
        couponDeferredUserFind.setCouponId(couponId);

//        CouponDeferredUser couponDeferredUser = couponDeferredUserDao.getByUserIdAndCouponId(userId,couponId);
        List<CouponDeferredUser> couponDeferredUserList = couponDeferredUserDao.findAll(Example.of(couponDeferredUserFind));

        //获取优惠券数据
        CouponDeferred couponDeferred = couponDeferredDao.findOne(couponId);

        if(couponDeferredUserList.size() == 0 || couponDeferred.getGetCount().intValue() == 0) {
            Date nowTime = new Date();

            //获取结束时间
            Date endTime = this.getCouponEndTime(nowTime,couponDeferred);
            //添加领取记录
            CouponDeferredUser couponDeferredUser = new CouponDeferredUser();
            couponDeferredUser.setCouponId(couponId);
            couponDeferredUser.setUserId(userId);
            couponDeferredUser.setStatus(0);
            couponDeferredUser.setEndTime(endTime);
            couponDeferredUser.setCreateTime(nowTime);
            couponDeferredUser.setChannel(channel);
            couponDeferredUserDao.save(couponDeferredUser);
        }else{
            //提示已领取
            throw new ApiNotFoundException("已经领取",2);
        }
        return null;
    }
    @Override
    public Page<CouponDeferredUser> getUserValidityDeferredCoupons(Long userId, Integer pageNo, Integer pageSize,Long priceId) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Date nowTime = new  Date();

        //未过期,且未使用
        Page<CouponDeferredUser> couponDeferredUserPage = couponDeferredUserDao.getUserValidityDeferredCoupons(userId,nowTime,pageable);
        //获取订阅价格数据
        PaySubscriptionPrice paySubscriptionPrice = paySubscriptionPriceDao.findOne(priceId);

        CouponDeferred couponDeferred;
        for (CouponDeferredUser item:couponDeferredUserPage.getContent()){
            couponDeferred = couponDeferredDao.findOne(item.getCouponId());
            item.setName(couponDeferred.getName());
            item.setContent(couponDeferred.getContent());
            item.setValidTime(couponDeferred.getValidTime());
            if(couponDeferred.getSubscibeTime() <= paySubscriptionPrice.getMonth()){
                item.setIsUsable(1);
            }else{
                item.setIsUsable(0);
            }
        }
        return couponDeferredUserPage;
    }
}

