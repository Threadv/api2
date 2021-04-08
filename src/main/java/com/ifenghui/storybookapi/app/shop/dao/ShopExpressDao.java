package com.ifenghui.storybookapi.app.shop.dao;

import com.ifenghui.storybookapi.app.shop.entity.ShopExpress;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ShopExpressDao extends JpaRepository<ShopExpress, Integer> {


    @Query("select  express from ShopExpress as express where express.orderId =:orderId and express.orderType=:orderType")
    ShopExpress findExpressByOrderId(@Param("orderId") Integer orderId, @Param("orderType") Integer orderType);
}
