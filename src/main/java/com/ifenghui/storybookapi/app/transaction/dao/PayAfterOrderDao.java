package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.PayAfterOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by jia on 2016/12/22.
 */

@Transactional
public interface PayAfterOrderDao extends JpaRepository<PayAfterOrder, Long> {
   PayAfterOrder getPayAfterOrderByOrderId(Long orderId);

   @Query("select p from PayAfterOrder as p where p.type =:orderType and p.payOrderId =:payOrderId")
   Page<PayAfterOrder> getPayAfterOrderByTypeAndPayOrderId(
           @Param("orderType") Integer orderType,
           @Param("payOrderId") Long payOrderId,
           Pageable pageable
   );
   
}
