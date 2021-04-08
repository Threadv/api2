package com.ifenghui.storybookapi.app.shop.dao;

import com.ifenghui.storybookapi.app.shop.entity.ShopPayCallBackRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ShopPayCallBackDao extends JpaRepository<ShopPayCallBackRecord, Integer> {


    @Query("select r from ShopPayCallBackRecord  as r where r.orderId =:orderId")
    ShopPayCallBackRecord findByOrderId(@Param("orderId") Integer orderId);
}
