package com.ifenghui.storybookapi.app.transaction.dao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.VPayOrder;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * 混合总订单
 */
@Transactional
public interface OrderMixDao extends JpaRepository<OrderMix, Integer>  {

    @Query("select o from OrderMix  o where o.userId =:userId and o.status =:status and o.isDel =0 and o.orderType = :orderType")
    List<OrderMix> findListByUserIdAndStatusAndOrderType(@Param("userId") Integer userId,@Param("status") Integer status,@Param("orderType") Integer orderType);

    @Query("select o from OrderMix o where o.status=0 and o.isDel=0")
    Page<OrderMix> findAllOrderMix(Pageable pageable);

    @Query("from OrderMix c where c.userId =:userId and c.isDel=0")
    Page<OrderMix> findByUserId(@Param("userId") Integer userId, Pageable pageable);//

    @Query("from OrderMix c where c.userId =:userId and c.status=:status and c.isDel=0")
    Page<OrderMix> findByUserIdAndStatus(@Param("userId") Integer userId,@Param("status") Integer status, Pageable pageable);//

    @Query("select o from OrderMix o where o.orderId =:orderId and  o.orderType =:orderType")
    OrderMix findOneByOrderIdAndType(@Param("orderId") Integer orderId, @Param("orderType") Integer orderType);

    @Override
    OrderMix save(OrderMix orderMix);

    @Modifying(clearAutomatically = true)
    @Query("update OrderMix as m set m.status=:status where orderType=:orderType and orderId=:orderId ")
    OrderMix updateStatus(@Param("orderId") Integer orderId, @Param("orderType") Integer orderType,@Param("status") Integer status);


}