package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


/**
 * Created by wml on 2017/2/16.
 */
@Transactional
public interface PayStoryOrderDao extends JpaRepository<PayStoryOrder, Long> {


//        Page<PaySubscriptionPrice> getPaySubscriptionPriceByStatus(Integer status, Pageable pageable);

    @Query(value = "select count(*) from story_pay_story_order where status=1 and buy_type=1 and original_price=:price and user_id=:userId", nativeQuery = true)
    Integer getPayStoryOrderTimes(
            @Param("price") Integer price,
            @Param("userId") Long userId
    );

    @Override
    PayStoryOrder save(PayStoryOrder payStoryOrder);


}