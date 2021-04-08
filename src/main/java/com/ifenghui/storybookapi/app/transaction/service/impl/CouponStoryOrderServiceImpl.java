package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.transaction.dao.CouponDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponStoryOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponUserDao;
import com.ifenghui.storybookapi.app.transaction.dao.lesson.CouponLessonOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.CouponLessonOrder;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class CouponStoryOrderServiceImpl implements CouponStoryOrderService {

    @Autowired
    CouponUserDao couponUserDao;

    @Autowired
    CouponDao couponDao;

    @Autowired
    CouponStoryOrderDao couponStoryOrderDao;

    @Override
    public CouponStoryOrder addCouponStoryOrder(Integer userId, Integer orderId, Integer couponId) {
        CouponStoryOrder couponStoryOrder = new CouponStoryOrder();
        couponStoryOrder.setCouponId(couponId);
        couponStoryOrder.setCreateTime(new Date());
        couponStoryOrder.setOrderId(orderId);
        couponStoryOrder.setUserId(userId);
        couponStoryOrder = couponStoryOrderDao.save(couponStoryOrder);
        return couponStoryOrder;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void addCouponOrderByCouponIdsStr(List<Integer> couponIds, Integer userId, Integer orderId) {
        if(couponIds==null){
            return;
        }


            for (int i = 0; i < couponIds.size(); i++) {
                Long couponUserId =couponIds.get(i).longValue();
                this.addCouponStoryOrder(userId,orderId, couponIds.get(i));
                //修改用户优惠券状态
                CouponUser couponUser = couponUserDao.findOne(couponUserId);
                couponUser.setStatus(1);
                couponUserDao.save(couponUser);
            }

    }

    @Override
    public void deleteCouponOrderByUserIdAndOrderId(Integer userId, Integer orderId) {
        //获取订单和优惠券关联
        List<CouponStoryOrder> couponLessonOrderList = couponStoryOrderDao.getCouponStoryOrdersByUserIdAndAndOrderId(userId, orderId);
        if(couponLessonOrderList.size()>0){
            CouponUser couponUser;
            for (CouponStoryOrder item:couponLessonOrderList){
                couponUser = couponUserDao.findOne(item.getCouponId().longValue());
                couponUser.setStatus(0);//改为未使用状态
                couponUserDao.save(couponUser);
                //删除此数据
                couponStoryOrderDao.delete(item.getId());
            }
        }
    }
    @Override
    public List<CouponStoryOrder> getCouponStoryOrderListByOrderId(Integer orderId){
        return couponStoryOrderDao.getCouponStoryOrdersByOrderId(orderId);
    }

}
