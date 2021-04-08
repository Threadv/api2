package com.ifenghui.storybookapi.app.shop.service.impl;

import com.ifenghui.storybookapi.app.shop.dao.ShopExpressDao;
import com.ifenghui.storybookapi.app.shop.entity.ShopExpress;
import com.ifenghui.storybookapi.app.shop.response.GetExpressResponse;
import com.ifenghui.storybookapi.app.shop.service.ShopExpressService;
import com.ifenghui.storybookapi.app.shop.service.ShopGoodsService;
import com.ifenghui.storybookapi.app.shop.service.ShopOrderService;
import com.ifenghui.storybookapi.app.transaction.response.LogisticsNew;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.ExpressStatusStyle;
import com.ifenghui.storybookapi.style.ExpressStyle;
import com.ifenghui.storybookapi.style.OrderStyle;
import com.ifenghui.storybookapi.util.ExpressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional
public class ShopExpressServiceImpl implements ShopExpressService {


    @Autowired
    ShopExpressDao shopExpressDao;

    @Autowired
    ShopGoodsService shopGoodsService;

    @Autowired
    ShopOrderService shopOrderService;


    /**
     * 添加物流信息
     *
     * @param orderId
     * @param receiver
     * @param phone
     * @param address
     * @param area
     * @param expressStyle
     * @param expressCode
     * @param expressStatus
     * @return
     */
    @Transactional
    @Override
    public ShopExpress addExpress(Integer orderId, String receiver, String phone, String address, String area, ExpressStyle expressStyle, String expressCode, ExpressStatusStyle expressStatus, OrderStyle orderStyle) {

        ShopExpress shopExpress = new ShopExpress();

        ShopExpress express = shopExpressDao.findExpressByOrderId(orderId, orderStyle.getId());
        if (express != null) {
            throw new ApiNotFoundException("该订单物流已存在！");
        }

        shopExpress.setOrderId(orderId);
        shopExpress.setReceiver(receiver);
        shopExpress.setPhone(phone);
        shopExpress.setAddress(area + address);
        shopExpress.setArea(area);
        shopExpress.setExpressCompany(expressStyle);
        shopExpress.setExpressCode(expressCode);
        shopExpress.setExpressStatus(expressStatus);
        shopExpress.setCreateTime(new Date());
        shopExpress.setOrderType(orderStyle);
        shopExpressDao.save(shopExpress);
        return shopExpress;
    }

    /**
     * 订单id查询物流
     *
     * @param orderId
     * @return
     */
    @Override
    public GetExpressResponse findExpressByOrderId(Integer orderId, OrderStyle orderStyle) {

        GetExpressResponse response = new GetExpressResponse();
        //查询物流信息
        ShopExpress express = shopExpressDao.findExpressByOrderId(orderId, orderStyle.getId());
        LogisticsNew logisticsNew = ExpressUtil.getExpress(express.getExpressCompany(), express.getExpressCode(), express.getAddress());
        response.setLogistics(logisticsNew);
        response.setLogo(ExpressStyle.getById(express.getExpressCompany()).getLogo());
        return response;
    }


    @Override
    public ShopExpress findShopExpressByOrderId(Integer orderId, OrderStyle orderStyle) {
        ShopExpress express = shopExpressDao.findExpressByOrderId(orderId, orderStyle.getId());
        return express;
    }
}
