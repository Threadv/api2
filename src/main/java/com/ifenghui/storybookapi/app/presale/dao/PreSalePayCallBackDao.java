package com.ifenghui.storybookapi.app.presale.dao;


import com.ifenghui.storybookapi.app.presale.entity.PreSalePayCallBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PreSalePayCallBackDao extends JpaRepository<PreSalePayCallBack, Integer> {

    @Query("select c from PreSalePayCallBack  as c where c.payId =:payId")
    PreSalePayCallBack findOneByPayId(@Param("payId") Integer payId);
}
