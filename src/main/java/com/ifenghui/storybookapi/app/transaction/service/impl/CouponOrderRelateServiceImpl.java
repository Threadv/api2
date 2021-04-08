package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.transaction.dao.CouponOrderRelateDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponUserDao;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponOrderRelate;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.CouponLessonOrder;
import com.ifenghui.storybookapi.app.transaction.service.CouponOrderRelateService;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CouponOrderRelateServiceImpl implements CouponOrderRelateService {

    @Autowired
    CouponOrderRelateDao couponOrderRelateDao;

    @Autowired
    CouponUserDao couponUserDao;

    @Override
    public CouponOrderRelate addCouponOrderRelate(Integer userId, Integer orderId, Integer couponId, OrderStyle orderStyle) {
        CouponOrderRelate couponOrderRelate = new CouponOrderRelate();
        couponOrderRelate.setCouponId(couponId);
        couponOrderRelate.setCreateTime(new Date());
        couponOrderRelate.setOrderId(orderId);
        couponOrderRelate.setUserId(userId);
        couponOrderRelate.setOrderType(orderStyle);
        return couponOrderRelateDao.save(couponOrderRelate);
    }

    @Override
    public void addCouponOrderByCouponIdsStr(List<Integer> couponIds, Integer userId, Integer orderId, OrderStyle orderStyle) {
        if(couponIds==null){
            return;
        }

//            String[] couponIdsStrArray = couponIdsStr.split(",");
            for (int i = 0; i < couponIds.size(); i++) {
                Long couponUserId = couponIds.get(i).longValue();
                this.addCouponOrderRelate(userId,orderId, couponUserId.intValue(),orderStyle);
                //修改用户优惠券状态
                CouponUser couponUser = couponUserDao.findOne(couponUserId);
                couponUser.setStatus(1);
                couponUserDao.save(couponUser);
            }

    }

    @Override
    public void deleteCouponOrderByUserIdAndOrderId(Integer userId, Integer orderId, OrderStyle orderStyle) {
        //获取订单和优惠券关联
        List<CouponOrderRelate> couponOrderRelateList = this.getCouponOrderRelatesByUserIdAndAndOrderIdAndOrderType(userId, orderId, orderStyle);
        if(couponOrderRelateList.size()>0){
            CouponUser couponUser;
            for (CouponOrderRelate item:couponOrderRelateList){
                couponUser = couponUserDao.findOne(item.getCouponId().longValue());
                couponUser.setStatus(0);//改为未使用状态
                couponUserDao.save(couponUser);
                //删除此数据
                couponOrderRelateDao.delete(item.getId());
            }
        }
    }

    @Override
    public List<CouponOrderRelate> getCouponOrderRelatesByUserIdAndAndOrderIdAndOrderType(Integer userId, Integer orderId, OrderStyle orderStyle) {
        return couponOrderRelateDao.getCouponOrderRelatesByUserIdAndAndOrderIdAndOrderType(userId, orderId, orderStyle.getId());
    }
}
