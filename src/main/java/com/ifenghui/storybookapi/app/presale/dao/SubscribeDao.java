package com.ifenghui.storybookapi.app.presale.dao;



import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Deprecated
public interface SubscribeDao extends JpaRepository<SubscriptionRecord, Integer> {

    /**
     * 查询订阅用户
     * @param userId
     * @return
     */
    @Query("from SubscriptionRecord  as s where s.userId = :userId")
    List<SubscriptionRecord> findListByUserId(@Param("userId") Integer userId);

}
