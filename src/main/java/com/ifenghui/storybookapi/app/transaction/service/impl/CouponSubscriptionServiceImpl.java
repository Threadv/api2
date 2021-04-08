package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.transaction.dao.CouponSubscriptionDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponUserDao;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponSubscription;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import com.ifenghui.storybookapi.app.transaction.service.CouponSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by narella on 2017/7/3.
 */
@Transactional
@Component
public class CouponSubscriptionServiceImpl implements CouponSubscriptionService {

    @Autowired
    CouponSubscriptionDao couponSubscriptionDao;

    @Autowired
    CouponUserDao couponUserDao;

    @Override
    public Page<CouponSubscription> getCouponSubscriptions(CouponSubscription couponSubscription, PageRequest pageRequest) {
        return couponSubscriptionDao.findAll(Example.of(couponSubscription),pageRequest);
    }

    @Override
    public void deleteCouponOrderByOrderId(Long orderId) {
        List<CouponSubscription> couponSubscriptions = couponSubscriptionDao.getByOrderId(orderId);
        if(couponSubscriptions.size()>0){
            CouponUser couponUser;
            for (CouponSubscription item:couponSubscriptions){
                couponUser = couponUserDao.findOne(item.getCouponUserId());
                couponUser.setStatus(0);//改为未使用状态
                couponUserDao.save(couponUser);
                //删除此数据
                couponSubscriptionDao.delete(item.getId());
            }
        }
    }
}
