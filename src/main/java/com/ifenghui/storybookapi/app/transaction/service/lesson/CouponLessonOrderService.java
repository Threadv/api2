package com.ifenghui.storybookapi.app.transaction.service.lesson;

import com.ifenghui.storybookapi.app.transaction.entity.lesson.CouponLessonOrder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CouponLessonOrderService {

    /**
     * 增加购买课程使用优惠券
     * @param userId
     * @param orderId
     * @param couponId
     * @return
     */
    CouponLessonOrder addCouponLessonOrder(Integer userId, Integer orderId, Integer couponId);

    /**
     * 增加购买课程使用优惠券
     * @param couponIds
     * @param userId
     * @param orderId
     */
    void addCouponOrderByCouponIdsStr(List<Integer> couponIds, Integer userId, Integer orderId);

    /**
     * 删除已使用优惠券记录
     */
    void deleteCouponOrderByUserIdAndOrderId(Integer userId, Integer orderId);

    /**
     * 获取 课程订单 与 优惠券 关联
     * @param userId
     * @param orderId
     * @return
     */
    List<CouponLessonOrder> getCouponLessonOrderListByOrderId(Integer userId, Integer orderId);
}
