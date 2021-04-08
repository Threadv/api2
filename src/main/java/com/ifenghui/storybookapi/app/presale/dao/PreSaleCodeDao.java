package com.ifenghui.storybookapi.app.presale.dao;


import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PreSaleCodeDao extends JpaRepository<PreSaleCode, Integer> {

    @Query("select code from PreSaleCode as code where code.userId =:userId")
    Page<PreSaleCode> getPreSaleCodesByUserId(
        @Param("userId") Integer userId,
        Pageable pageable
    );

    @Query("select code from PreSaleCode as code where code.payId =:payId")
    PreSaleCode getPreSaleCodeByPayId(
        @Param("payId") Integer payId
    );

    @Query("select code from PreSaleCode as code where code.code =:code")
    PreSaleCode getPreSaleCodeByCode(
        @Param("code") String code
    );

    @Query("select code from PreSaleCode as code where code.codeType =:codeType and code.endTime <=:endTime and code.endTime>:nowTime and code.status = 0")
    List<PreSaleCode> getPreSaleCodeByTime(
            @Param("codeType") Integer codeType,
            @Param("endTime") Date endTime,
            @Param("nowTime") Date nowTime
    );
}
