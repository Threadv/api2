package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.transaction.dao.CouponDeferredSubscriptionDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponDeferredUserDao;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferred;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredSubscription;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredUser;
import com.ifenghui.storybookapi.app.transaction.service.CouponDeferredSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by narella on 2017/7/3.
 */
@Transactional
@Component
public class CouponDeferredSubscriptionServiceImpl implements CouponDeferredSubscriptionService {

    @Autowired
    CouponDeferredSubscriptionDao couponDeferredSubscriptionDao;

    @Autowired
    CouponDeferredUserDao couponDeferredUserDao;

    @Override
    public Page<CouponDeferredSubscription> getCouponDeferredSubscriptions(
            CouponDeferredSubscription couponDeferredSubscription, PageRequest pageRequest) {

        return couponDeferredSubscriptionDao.findAll(
                Example.of(couponDeferredSubscription),pageRequest);
    }

    @Override
    public void deleteCouponDeferredSubscriptionByOrderId(Long orderId) {
        //获取订阅订单和赠阅券关联
        CouponDeferredSubscription couponDeferredSubscription = couponDeferredSubscriptionDao.getByOrderId(orderId);
        if(couponDeferredSubscription!=null){
            CouponDeferredUser couponDeferredUser;
//            for (CouponSubscriptionService item:couponSubscriptions){
            couponDeferredUser = couponDeferredUserDao.findOne(couponDeferredSubscription.getCouponUserId());
            couponDeferredUser.setStatus(0);//改为未使用状态
            couponDeferredUserDao.save(couponDeferredUser);
            couponDeferredSubscriptionDao.delete(couponDeferredSubscription.getId());
        }

    }
}
