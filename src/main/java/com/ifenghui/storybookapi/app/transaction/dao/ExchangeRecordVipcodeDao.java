package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.goods.ExchangeRecordVipcode;
import org.springframework.data.jpa.repository.JpaRepository;
@Deprecated
public interface ExchangeRecordVipcodeDao extends JpaRepository<ExchangeRecordVipcode, Long> {

}