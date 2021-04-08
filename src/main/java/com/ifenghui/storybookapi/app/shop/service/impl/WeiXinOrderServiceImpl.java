package com.ifenghui.storybookapi.app.shop.service.impl;

import com.ifenghui.storybookapi.app.shop.service.WeiXinOrderService;
import com.ifenghui.storybookapi.util.HttpRequest;
import com.ifenghui.storybookapi.util.weixin.WXPay;
import com.ifenghui.storybookapi.util.weixin.WXPayConfigImpl;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import weixin.Utils.MD5Util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class WeiXinOrderServiceImpl implements WeiXinOrderService {

    private WXPay wxpay;
    private WXPayConfigImpl config;
    private String out_trade_no;
    private String total_fee;

    Logger logger = Logger.getLogger(WeiXinOrderServiceImpl.class);

    @Autowired
    private Environment env;

    public static String certpath;

    /**
     * 扫码支付  下单返回信息
     */
    @Override
    public JSONObject doUnifiedOrder(String outTradeNo, String goodsName, Integer goodsPrice, String goodsId, HttpServletRequest request, String openId, String wxkey, String callBackUrl, String tradeType, String sceneInfo, String appId, String mchId) {

        String ip = HttpRequest.getIpAddr(request);
        try {
            JSONObject jsonObject = this.doUnifiedOrderWx(outTradeNo, goodsName, goodsPrice, goodsId,ip,openId,wxkey, callBackUrl, tradeType, sceneInfo, appId, mchId);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject doUnifiedOrderWx(String outTradeNo, String goodsName, Integer goodsPrice, String goodsId,String ip,String openId,String wxkey, String callBackUrl, String tradeType, String sceneInfo, String appId, String mchId) {

        try {
            String certpath = env.getProperty("certPath");
            config = WXPayConfigImpl.getInstance(certpath, appId, mchId);
            wxpay = new WXPay(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", goodsName);
        data.put("out_trade_no", outTradeNo);
        data.put("fee_type", "CNY");
        data.put("device_info", "");
        data.put("total_fee", goodsPrice.toString());
        data.put("spbill_create_ip", ip);
        data.put("notify_url", callBackUrl);
        data.put("trade_type", tradeType);
        data.put("product_id", goodsId);
        if(openId != null){
            data.put("openid", openId);
        }
        data.put("scene_info", sceneInfo);

        try {
            long timeMillis = System.currentTimeMillis();
            Long timeStamp = (timeMillis/1000);
            Map<String, String> map = wxpay.unifiedOrder(data);
            logger.info(map);
            String appid = map.get("appid");
            String nonce_str = map.get("nonce_str");
            String prepay_id = map.get("prepay_id");

            String sign= "appId="+appid+"&nonceStr="+nonce_str+"&package=prepay_id="+prepay_id+"&signType=MD5"+"&timeStamp="+timeStamp+"&key="+ wxkey;
            System.out.println(sign);
            String jsSign= MD5Util.MD5Encode(sign,"utf-8").toUpperCase();

            map.put("jsSign",jsSign);
            map.put("timeStamp",timeStamp.toString());//当前时间戳
            System.out.println(map);
            return JSONObject.fromObject(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
