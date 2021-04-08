package com.ifenghui.storybookapi.app.shop.service.impl;

import com.ifenghui.storybookapi.app.presale.exception.PreSaleNotFoundException;
import com.ifenghui.storybookapi.app.shop.dao.ShopPayCallBackDao;
import com.ifenghui.storybookapi.app.shop.entity.ShopOrder;
import com.ifenghui.storybookapi.app.shop.entity.ShopPayCallBackRecord;
import com.ifenghui.storybookapi.app.shop.service.ShopOrderService;
import com.ifenghui.storybookapi.app.shop.service.ShopPayCallBackService;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.RechargePayStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional
public class ShopPayCallBackServiceImpl implements ShopPayCallBackService {

    @Autowired
    ShopPayCallBackDao shopPayCallBackDao;

    @Autowired
    ShopOrderService shopOrderService;

    /**
     * 添加回调记录
     *
     * @return
     */
    @Override
    public ShopPayCallBackRecord addPayCallbackRecord(ShopPayCallBackRecord callBackRecord) {
        return  shopPayCallBackDao.save(callBackRecord);
    }

    /**
     * 添加回调记录
     * @param callBackMsg
     * @param rechargePayStyle
     * @param orderId
     * @return
     */
    @Override
    public ShopPayCallBackRecord addShopPayCallBackRecord(String callBackMsg, RechargePayStyle rechargePayStyle, Integer orderId){
        ShopOrder shopOrder =  shopOrderService.findOrderById(orderId);
        if(shopOrder == null){
            throw new PreSaleNotFoundException("订单不存在！");
        }
        ShopPayCallBackRecord callbackRecord = new ShopPayCallBackRecord();
        callbackRecord.setOrderId(orderId);
        callbackRecord.setType(rechargePayStyle);
        callbackRecord.setCreateTime(new Date());
        callbackRecord.setCallbackMsg(callBackMsg);
        return shopPayCallBackDao.save(callbackRecord);
    }

    /**
     * 订单id查找回调记录
     *
     * @param orderId
     * @return
     */
    @Override
    public ShopPayCallBackRecord getPayCallbackRecordByOrderId(Integer orderId) {
        return  shopPayCallBackDao.findByOrderId(orderId);
    }
}
