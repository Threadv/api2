package com.ifenghui.storybookapi.app.wallet.dao;

import com.ifenghui.storybookapi.app.wallet.entity.PayCallbackRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by jia on 2016/12/22.
 */

@Transactional
public interface PayCallbackRecordDao extends JpaRepository<PayCallbackRecord, Long> {
    /**
     * 通过订单编code查询
     * @param orderCode
     * @return
     */
    PayCallbackRecord getOneByOrderCode(String orderCode);

    /**
     * 通过订单id查询
     * @param orderId
     * @return
     */
    PayCallbackRecord findOneByOrderId(Long orderId);

    /**
     * 通过回调信息查询
     * @param callbackMsg
     * @return
     */
    PayCallbackRecord findOneByCallbackMsg(String callbackMsg);

    @Query("select item from PayCallbackRecord as item where item.callbackMsg=:callBackMsg")
    Page<PayCallbackRecord> getPreSalePayCallBacksByCallbackMsg(
            @Param("callBackMsg") String callBackMsg,
            Pageable pageable
    );
}
