package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.dao.CouponDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponUserDao;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.Coupon;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.exception.ApiCouponPastDueException;
import com.ifenghui.storybookapi.exception.ApiCouponUsedException;
import com.ifenghui.storybookapi.util.ListUtil;
import io.swagger.annotations.Api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wslhk on 2018/4/8.
 */
public class CouponsResult {

    List<Integer> couponIds =null;

    int couponAmount=0;

    public List<Integer> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List couponIds) {
        this.couponIds = couponIds;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public void checkCoupons(List<Integer> couponIds, CouponUserDao couponUserDao, CouponDao couponDao, Integer originalPrice)throws ApiCouponPastDueException {
        if(couponIds!=null){
            couponIds= ListUtil.removeNull(couponIds);
            this.couponIds = couponIds;
            CouponUser couponUser;
            Coupon coupon;

            if(couponIds.size() > 1) {
                throw new ApiCouponUsedException("不可使用多张优惠券！");
            }

            Date nowDate = new Date();
            long  nowTimeStemp = nowDate.getTime();//当前时间戳
            for (int i = 0; i < couponIds.size(); i++) {
                Long couponUserId = couponIds.get(i).longValue();
                //判断此优惠券是否使用过
                couponUser = couponUserDao.findOne(couponUserId);
                if(couponUser.getStatus()==1){
                    //已使用
                    throw new ApiCouponUsedException(MyEnv.getMessage("coupon.used"));
                }
                //获取优惠券数据
                coupon = couponDao.findOne(couponUser.getCouponId());
                couponAmount = couponAmount + coupon.getAmount();
                //判断是否过期，过期抛异常
                long endTimeStemp = couponUser.getEndTime().getTime();//上一条记录结束时间戳
                long dv = nowTimeStemp-endTimeStemp;
                if(dv>0){//过期
                    throw new ApiCouponPastDueException(MyEnv.getMessage("coupon.timeout"));
                }

                if (coupon.getMaxCount() > originalPrice){
                    throw new ApiCouponUsedException("订单金额不满足优惠券使用最低金额");
                }
            }

        }
    }
}
