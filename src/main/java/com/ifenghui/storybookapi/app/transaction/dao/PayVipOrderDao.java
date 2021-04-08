package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.vip.PayVipOrder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PayVipOrderDao extends JpaRepository<PayVipOrder, Integer> {


    @Query("from PayVipOrder s where s.userId=:userId and s.status=1 and s.isDel =0")
    List<PayVipOrder> findPayVipOrdersByUserId(@Param("userId")Integer userId, Sort sort);
}
