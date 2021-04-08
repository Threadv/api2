package com.ifenghui.storybookapi.app.shop.dao;

import com.ifenghui.storybookapi.app.shop.entity.ShopOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ShopOrderDao extends JpaRepository<ShopOrder, Integer> {


    @Query("from ShopOrder as o where o.status =0 and o.isDel=0")
    List<ShopOrder> findAllOrder();

    //已完成定案包括取消的订单
    @Query("from ShopOrder as o where o.userId =:userId and (o.status =1 or o.status =2) and o.isDel=0")
    Page<ShopOrder> findSuccessOrderList(@Param("userId") Integer userId, Pageable pageable);

    @Query("from ShopOrder as o where o.userId =:userId and o.status =:status and o.isDel=0")
    Page<ShopOrder> findOrderListByStatus(@Param("userId") Integer userId, @Param("status") Integer status, Pageable pageable);

    @Query("select  o from ShopOrder as o where o.userId =:userId and o.isDel=0 ")
    Page<ShopOrder> findOrderListByUserId(@Param("userId") Integer userId, Pageable pageable);
}
