package com.ifenghui.storybookapi.app.transaction.dao;
import com.ifenghui.storybookapi.app.transaction.entity.VPayOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2017/6/29.
 */
@Transactional
public interface VPayOrderDao extends JpaRepository<VPayOrder, Long>  {

    @Query("from VPayOrder c where c.userId =:userId ")
    Page<VPayOrder> getUserPayOrder(@Param("userId") Long userId, Pageable pageable);//

    @Query("select  o from VPayOrder  as o where o.orderId = :orderId and  o.type = :type")
    VPayOrder getOrderByOrderIdAndType(@Param("orderId") Long orderId,@Param("type")Integer type);

    @Query("select o from VPayOrder as o where o.id >=:beginId and o.id <=:endId")
    List<VPayOrder> getVPayOrdersByIdBetween(@Param("beginId") Long beginId,@Param("endId")Long endId);
}