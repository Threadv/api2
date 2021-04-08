package com.ifenghui.storybookapi.adminapi.controlleradmin.code.dao;

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessageDetail;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2016/12/27.
 */
@Transactional
public interface SmsMessageDetailDao extends JpaRepository<SmsMessageDetail, Integer>,JpaSpecificationExecutor {

    @Query("select s from SmsMessageDetail as s where s.code =:code")
    SmsMessageDetail findOneByCode(
            @Param("code") String code );

    @Query("select s from SmsMessageDetail as s where s.messageId =:messageId")
    List<SmsMessageDetail> findAllByMessaID(@Param("messageId") Integer messageId);

}