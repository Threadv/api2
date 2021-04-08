package com.ifenghui.storybookapi.app.wallet.dao;

import com.ifenghui.storybookapi.app.wallet.entity.PayRechargePrice;
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
public interface PayRechargePriceDao extends JpaRepository<PayRechargePrice, Long> {

    Page<PayRechargePrice> getPayRechargePriceByStatusAndType(Integer status, Integer type, Pageable pageable);

    PayRechargePrice findOneByIap(String iap);

    @Query("select item from PayRechargePrice as item where item.status=:status and item.type=:walletType")
    Page<PayRechargePrice> getPayRechargePricesByStatusAndType(
            @Param("status") Integer status,
            @Param("walletType") Integer walletType,
            Pageable pageable
    );
}
