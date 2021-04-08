package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.transaction.dao.CouponStoryExchangeOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponStoryExchangeUserDao;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeOrder;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeUser;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeOrderService;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class CouponStoryExchangeOrderServiceImpl implements CouponStoryExchangeOrderService {

    @Autowired
    CouponStoryExchangeOrderDao couponStoryExchangeOrderDao;

    @Autowired
    CouponStoryExchangeUserDao couponStoryExchangeUserDao;

    @Transactional
    @Override
    public CouponStoryExchangeOrder addCouponStoryExchangeOrder(Integer userId, Integer orderId, Integer couponId, OrderStyle orderStyle) {
        CouponStoryExchangeOrder couponStoryExchangeOrder = new CouponStoryExchangeOrder();
        couponStoryExchangeOrder.setUserId(userId);
        couponStoryExchangeOrder.setCouponId(couponId);
        couponStoryExchangeOrder.setOrderId(orderId);
        couponStoryExchangeOrder.setOrderStyle(orderStyle);
        couponStoryExchangeOrder.setCreateTime(new Date());
        couponStoryExchangeOrder = couponStoryExchangeOrderDao.save(couponStoryExchangeOrder);
        return couponStoryExchangeOrder;
    }

    @Transactional
    @Override
    public void addCouponStoryExchangeOrderByList(List<CouponStoryExchangeUser> couponStoryExchangeUserList, Integer orderId, OrderStyle orderStyle){
        if(couponStoryExchangeUserList != null && couponStoryExchangeUserList.size() > 0){
            for (CouponStoryExchangeUser item : couponStoryExchangeUserList){
                this.addCouponStoryExchangeOrder(item.getUserId(), orderId, item.getId(), orderStyle);
                CouponStoryExchangeUser couponStoryExchangeUser = couponStoryExchangeUserDao.findOne(item.getId());
                couponStoryExchangeUser.setStatus(1);
                couponStoryExchangeUserDao.save(couponStoryExchangeUser);
            }
        }
    }
}
