package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponSerialStoryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2017/5/19.
 */
@Transactional
public interface CouponSerialStoryOrderDao extends JpaRepository<CouponSerialStoryOrder, Long> {

    List<CouponSerialStoryOrder> getByOrderId(Long orderId);

}