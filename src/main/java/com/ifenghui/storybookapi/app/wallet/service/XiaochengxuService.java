package com.ifenghui.storybookapi.app.wallet.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface XiaochengxuService {

    public JSONObject doUnifiedOrder(String outTradeNo, String goodsName, Integer goodsPrice, String goodsId, HttpServletRequest request, String openId, String wxkey, String callBackUrl, String appId, String mchId);

    public JSONObject doUnifiedOrderWx(String outTradeNo, String goodsName, Integer goodsPrice, String goodsId,String ip,String openId,String wxkey, String callBackUrl, String appId, String mchId);

}
