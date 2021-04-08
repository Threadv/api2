package com.ifenghui.storybookapi.app.presale.dao;


import com.ifenghui.storybookapi.app.presale.entity.PreSaleLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreSaleLogDao extends JpaRepository<PreSaleLog, Integer> {
//    PreSaleLog add(PreSaleLog preSaleLog);
}
