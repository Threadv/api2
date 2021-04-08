package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


/**
 * Created by wml on 2017/2/16.
 */
@Transactional
public interface PaySubscriptionPriceDao extends JpaRepository<PaySubscriptionPrice, Long> {


        Page<PaySubscriptionPrice> getPaySubscriptionPriceByStatus(Integer status, Pageable pageable);

}