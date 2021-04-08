package com.ifenghui.storybookapi.app.wallet.dao;

import com.ifenghui.storybookapi.app.wallet.entity.CashAccountCashApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CashAccountCashApplyDao extends JpaRepository<CashAccountCashApply, Integer> {

    @Query("select sum(c) from CashAccountCashApply as c where c.userId=:userId and c.status=:status and c.createTime >=:beginTime and c.createTime<=:endTime")
    public Integer getSumCashAccountCashApplyByPeriodTime(
            @Param("userId") Integer userId,
            @Param("status") Integer status,
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime
    );

    @Query("select c from CashAccountCashApply as c where c.status=:status")
    public List<CashAccountCashApply> getAllByStatus(
            @Param("status") Integer status
    );

}
