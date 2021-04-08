package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.transaction.dao.CouponSerialStoryOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponUserDao;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponSerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import com.ifenghui.storybookapi.app.transaction.service.CouponSerialStoryOrderService;
import com.ifenghui.storybookapi.app.transaction.service.CouponsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CouponSerialStoryOrderServiceImpl implements CouponSerialStoryOrderService {

    @Autowired
    CouponUserDao couponUserDao;

    @Autowired
    CouponSerialStoryOrderDao couponSerialStoryOrderDao;
    @Override
    public void addCouponSerialOrderByCouponsResult(CouponsResult couponsResult, Integer userId, Integer orderId){
        if(couponsResult.getCouponIds() != null){
            for (int i = 0; i < couponsResult.getCouponIds().size(); i++) {
                Long couponUserId = couponsResult.getCouponIds().get(i).longValue();
                CouponSerialStoryOrder couponSerialStoryOrder = new CouponSerialStoryOrder();
                couponSerialStoryOrder.setUserId(userId.longValue());
                couponSerialStoryOrder.setCouponUserId(couponUserId);
                couponSerialStoryOrder.setOrderId(orderId.longValue());
                couponSerialStoryOrder.setCreateTime(new Date());
                couponSerialStoryOrderDao.save(couponSerialStoryOrder);
                //修改用户优惠券状态
                CouponUser cu = couponUserDao.findOne(couponUserId);
                cu.setStatus(1);
                couponUserDao.save(cu);
            }
        }
    }

    @Override
    public void deleteCouponSerialOrderByOrderId(Long orderId) {
        List<CouponSerialStoryOrder> couponSerialStoryOrders = couponSerialStoryOrderDao.getByOrderId(orderId);
        if(couponSerialStoryOrders.size()>0){
            CouponUser couponUser;
            for (CouponSerialStoryOrder item:couponSerialStoryOrders){
                couponUser = couponUserDao.findOne(item.getCouponUserId());
                couponUser.setStatus(0);//改为未使用状态
                couponUserDao.save(couponUser);
                //删除此数据
                couponSerialStoryOrderDao.delete(item.getId());
            }
        }
    }

    @Override
    public List<CouponSerialStoryOrder> getCouponSerialStoryOrderListByOrderId(Long orderId) {
        return couponSerialStoryOrderDao.getByOrderId(orderId);
    }
}
