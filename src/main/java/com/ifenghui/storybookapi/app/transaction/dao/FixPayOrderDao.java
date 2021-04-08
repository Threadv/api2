package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.FixPayOrder;
import com.ifenghui.storybookapi.app.transaction.entity.VPayOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FixPayOrderDao extends JpaRepository<FixPayOrder, Integer> {

    @Query("select o from FixPayOrder as o where o.id >=:beginId and o.id <=:endId")
    List<FixPayOrder> getVPayOrdersByIdBetween(@Param("beginId") Long beginId, @Param("endId")Long endId);

}
