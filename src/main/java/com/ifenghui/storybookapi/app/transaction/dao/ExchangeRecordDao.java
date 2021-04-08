package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.goods.ExchangeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
@Deprecated
public interface ExchangeRecordDao extends JpaRepository<ExchangeRecord, Long> {

}