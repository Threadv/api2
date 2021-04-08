package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.goods.ExpressRecord;
import org.springframework.data.jpa.repository.JpaRepository;
@Deprecated
public interface ExpressRecordDao extends JpaRepository<ExpressRecord, Long> {
}
