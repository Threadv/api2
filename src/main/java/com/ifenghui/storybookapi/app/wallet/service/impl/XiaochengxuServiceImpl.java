package com.ifenghui.storybookapi.app.wallet.service.impl;

import com.ifenghui.storybookapi.app.wallet.service.XiaochengxuService;
import com.ifenghui.storybookapi.util.HttpRequest;
import com.ifenghui.storybookapi.util.JS_SDK.WXConfig;
import com.ifenghui.storybookapi.util.weixin.WXPay;
import com.ifenghui.storybookapi.util.weixin.WXPayConfigImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import weixin.Utils.MD5Util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class XiaochengxuServiceImpl implements XiaochengxuService {

    private WXPay wxpay;
    private WXPayConfigImpl config;
    private String out_trade_no;
    private String total_fee;

    @Autowired
    private Environment env;

    public static String certpath;

    /**
     * 扫码支付  下单返回信息
     */
    @Override
    public JSONObject doUnifiedOrder(String outTradeNo, String goodsName, Integer goodsPrice, String goodsId, HttpServletRequest request, String openId, String wxkey, String callBackUrl, String appId, String mchId) {

        String ip = HttpRequest.getIpAddr(request);
        try {
            JSONObject jsonObject = this.doUnifiedOrderWx(outTradeNo, goodsName, goodsPrice, goodsId,ip,openId,wxkey, callBackUrl, appId, mchId);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject doUnifiedOrderWx(String outTradeNo, String goodsName, Integer goodsPrice, String goodsId,String ip,String openId,String wxkey, String callBackUrl, String appId, String mchId) {

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
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", goodsPrice.toString());
//        data.put("total_fee", "268");
        data.put("spbill_create_ip", ip);
        data.put("notify_url", callBackUrl);
        data.put("trade_type", "JSAPI");
        data.put("product_id", goodsId);
        data.put("openid", openId);
        // data.put("time_expire", "20170112104120");

        try {
            long timeMillis = System.currentTimeMillis();
            Long timeStamp = (timeMillis/1000);
            Map<String, String> map = wxpay.unifiedOrder(data);

            String appid = map.get("appid");
            String nonce_str = map.get("nonce_str");
            String prepay_id = map.get("prepay_id");

            String sign= "appId="+appid+"&nonceStr="+nonce_str+"&package=prepay_id="+prepay_id+"&signType=MD5"+"&timeStamp="+timeStamp+"&key="+ wxkey;//gushifeichuan1988abcdefg12345678
            System.out.println(sign);
            String jsJign= MD5Util.MD5Encode(sign,"utf-8").toUpperCase();

            map.put("timeStamp",timeStamp.toString());//当前时间戳
            map.put("jsSign",jsJign);
            System.out.println(map);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
