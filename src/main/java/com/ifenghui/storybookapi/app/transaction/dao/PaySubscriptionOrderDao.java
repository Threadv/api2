package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2017/2/16.
 */
@Transactional
public interface PaySubscriptionOrderDao extends JpaRepository<PaySubscriptionOrder, Long> {

    @Query("from PaySubscriptionOrder p where p.status =1  and p.user=:user and month = 12 and p.createTime >'2017-08-10 00:00:00'")
    List<PaySubscriptionOrder> isUserSubscribed(@Param("user") User user);

    @Query(value = "select count(*) from story_pay_subscription_order where status=1 and type !=4 and month=:month and user_id=:userId", nativeQuery = true)
    Integer getPaySubscriptionOrderTimes(
        @Param("month") Integer month,
        @Param("userId") Long userId
    );

}