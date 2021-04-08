package com.ifenghui.storybookapi.app.shop.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface WeiXinOrderService {

    public JSONObject doUnifiedOrder(String outTradeNo, String goodsName, Integer goodsPrice, String goodsId, HttpServletRequest request, String openId, String wxkey, String callBackUrl, String tradeType, String sceneInfo, String appId, String mchId);

    public JSONObject doUnifiedOrderWx(String outTradeNo, String goodsName, Integer goodsPrice, String goodsId, String ip, String openId, String wxkey, String callBackUrl, String tradeType, String sceneInfo, String appId, String mchId);


}
