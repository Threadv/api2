package com.ifenghui.storybookapi.app.transaction.service.lesson.impl;

import com.ifenghui.storybookapi.app.transaction.dao.CouponDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponUserDao;
import com.ifenghui.storybookapi.app.transaction.dao.lesson.CouponLessonOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.Coupon;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponSerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.CouponLessonOrder;
import com.ifenghui.storybookapi.app.transaction.service.lesson.CouponLessonOrderService;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.exception.ApiCouponPastDueException;
import com.ifenghui.storybookapi.exception.ApiCouponUsedException;
import com.ifenghui.storybookapi.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.Date;
import java.util.List;

@Component
public class CouponLessonOrderServiceImpl implements CouponLessonOrderService {

    @Autowired
    CouponUserDao couponUserDao;

    @Autowired
    CouponDao couponDao;

    @Autowired
    CouponLessonOrderDao couponLessonOrderDao;

    @Override
    public CouponLessonOrder addCouponLessonOrder(Integer userId, Integer orderId, Integer couponId) {
        CouponLessonOrder couponLessonOrder = new CouponLessonOrder();
        couponLessonOrder.setCouponId(couponId);
        couponLessonOrder.setCreateTime(new Date());
        couponLessonOrder.setOrderId(orderId);
        couponLessonOrder.setUserId(userId);
        couponLessonOrder = couponLessonOrderDao.save(couponLessonOrder);
        return couponLessonOrder;
    }

    @Transactional
    @Override
    public void addCouponOrderByCouponIdsStr(List<Integer> couponIds, Integer userId, Integer orderId) {
        couponIds=ListUtil.removeNull(couponIds);
        if(couponIds!=null) {
//            String[] couponIdsStrArray = couponIdsStr.split(",");
            for (int i = 0; i < couponIds.size(); i++) {
                Long couponUserId = couponIds.get(i).longValue();
                this.addCouponLessonOrder(userId,orderId, couponUserId.intValue());
                //修改用户优惠券状态
                CouponUser couponUser = couponUserDao.findOne(couponUserId);
                couponUser.setStatus(1);
                couponUserDao.save(couponUser);
            }
        }
    }

    @Override
    public void deleteCouponOrderByUserIdAndOrderId(Integer userId, Integer orderId) {
        //获取订单和优惠券关联
        List<CouponLessonOrder> couponLessonOrderList = couponLessonOrderDao.getCouponLessonOrdersByUserIdAndAndOrderId(userId, orderId);
        if(couponLessonOrderList.size()>0){
            CouponUser couponUser;
            for (CouponLessonOrder item:couponLessonOrderList){
                couponUser = couponUserDao.findOne(item.getCouponId().longValue());
                couponUser.setStatus(0);//改为未使用状态
                couponUserDao.save(couponUser);
                //删除此数据
                couponLessonOrderDao.delete(item.getId());
            }
        }
    }

    @Override
    public List<CouponLessonOrder> getCouponLessonOrderListByOrderId(Integer userId, Integer orderId) {
        return couponLessonOrderDao.getCouponLessonOrdersByUserIdAndAndOrderId(userId, orderId);
    }
}
