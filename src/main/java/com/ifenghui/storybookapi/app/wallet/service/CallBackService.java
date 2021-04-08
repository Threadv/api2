package com.ifenghui.storybookapi.app.wallet.service;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.app.transaction.entity.*;
import com.ifenghui.storybookapi.app.wallet.entity.PayCallbackRecord;

/**
 * 支付回调记录
 */
public interface CallBackService {

    /**
     * 增加
     * @param payCallbackRecord
     * @return
     */
    PayCallbackRecord addPayCallbackRecord(PayCallbackRecord payCallbackRecord);

    /**
     * 查询
     * @param orderCode
     * @return
     */
    PayCallbackRecord getPayCallbackRecordByOrderCode(String orderCode);

    /**
     * 通过msg查询
     * @param callbackMsg
     * @return
     */
    PayCallbackRecord getPayCallbackRecordByCallBackMsg(String callbackMsg);

    /**
     * 通过订单id查询（推荐）
     * @param orderId
     * @return
     */
    PayCallbackRecord getPayCallbackRecordByOrderId(Long orderId);


}
