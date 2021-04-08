package com.ifenghui.storybookapi.app.wallet.dao;

import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by jia on 2016/12/22.
 */

@Transactional
public interface PayRechargeOrderDao extends JpaRepository<PayRechargeOrder, Long>, JpaSpecificationExecutor {
   List<PayRechargeOrder> getPayRechargeOrderByUserIdAndOrderCodeAndBuyType(Long userId, String orderCode, Integer status, Integer buyType);

   @Query("from PayRechargeOrder p where p.status =0 and type =2 and p.createTime<:fifTime")
   List<PayRechargeOrder> getNoPayByBeforeTime(@Param("fifTime") Date fifTime);//15分钟之前的所有未支付的订阅订单
   PayRechargeOrder getByTradeNo(String tradeNo);

}