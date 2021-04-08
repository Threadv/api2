package com.ifenghui.storybookapi.app.presale.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.service.AdService;
import com.ifenghui.storybookapi.app.presale.dao.ActivityDao;
import com.ifenghui.storybookapi.app.presale.dao.OrderPayActivityDao;
import com.ifenghui.storybookapi.app.presale.dao.PreSalePayDao;
import com.ifenghui.storybookapi.app.presale.entity.Activity;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;
import com.ifenghui.storybookapi.app.presale.service.PreSalePayService;
import com.ifenghui.storybookapi.app.transaction.dao.OrderMixDao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.OrderPayActivity;


import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.util.DateCheckUtil;
import com.ifenghui.storybookapi.util.IpUtil;
import com.ifenghui.storybookapi.util.JS_SDK.WXConfig;
import com.ifenghui.storybookapi.util.PreSaleMD5Util;
import com.ifenghui.storybookapi.util.weixin.WXPay;
import com.ifenghui.storybookapi.util.weixin.WXPayConfigImpl;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PreSalePayServiceImpl implements PreSalePayService {




    @Autowired
    AdService adService;



    private static Logger logger = Logger.getLogger(PreSalePayServiceImpl.class);
    private WXPay wxpay;
    private WXPayConfigImpl config;
    private String out_trade_no;
    private String total_fee;

    @Autowired
    private Environment env;

//    public static String certpath;

    @Autowired
    PreSalePayDao preSalePayDao;

    @Autowired
    ActivityDao activityDao;

    @Autowired
    OrderPayActivityDao orderPayActivityDao;

    @Autowired
    OrderMixDao orderMixDao;

    @Override
    public Ads isGetMagazinePreSalePay(Integer userId) {
        if(userId != null && userId != 0){
            List<PreSalePay> preSalePayList = preSalePayDao.getPreSalePayByGoodsIdAndUserId(5, userId);
            if(preSalePayList == null || preSalePayList.size() == 0){
                return adService.findOneAds(45L);
            } else {
                return null;
            }
        } else {
            return adService.findOneAds(45L);
        }
    }

    @Override
    public Page<PreSalePay> findAll(PreSalePay preSalePay, PageRequest pageRequest) {
        return preSalePayDao.findAll(Example.of(preSalePay),pageRequest);
    }

    @Override
    public Integer sumPriceAll(PreSalePay preSalePay) {
        return 0;
    }

    @Override
    public PreSalePay getPayByUserIdAndGoodsId(Integer userId, Integer goodsId, Integer activityId) {
        List<PreSalePay> pays = preSalePayDao.getPayByUserIdAndGoodsId(userId, goodsId, activityId);
        if(pays.size()>0){
            return pays.get(pays.size()-1);
        }
        return null;
    }

    /**
     * 获得orderString
     * @param amount
     * @param orderId
     * @param app_id
     * @param private_key
     * @param notify_url
     * @return
     */
    @Override
    public String getAlipayStr(int amount, int orderId, String app_id, String private_key, String notify_url) {
        String prefix=env.getProperty("pre_sale_out_trade");
        String orderStr = prefix + "_" + orderId;
        String charset="utf-8";
        String sign_type="RSA";

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        String timestamp = dateFormat.format( now );
        String method = "alipay.trade.app.pay";//?
        String version = "1.0";

        float size = (float)amount/100;
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        String total_amount = df.format(size);//返回的是String类型的

        String out_trade_no = prefix+"_"+orderId;
        String biz_content = "{\"timeout_express\":\"30m\",\"seller_id\":\"\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":"+total_amount+",\"subject\":\"支付宝支付\",\"body\":\"交易的具体描述信息\",\"out_trade_no\":"+"\""+out_trade_no+"\""+"}";
        String formatStr = "json";
        String originalStr;//原始字符串
        originalStr = "app_id="+app_id+"&biz_content="+biz_content+"&charset="+charset+"&format="+formatStr+"&method="+method+"&notify_url="+notify_url+"&sign_type="+sign_type+"&timestamp="+timestamp+"&version="+version;

        logger.info("--------------------getPayRechargeOrder----biz_content-----"+biz_content);
        logger.info("--------------------getPayRechargeOrder----originalStr-----"+originalStr);

        String sign = null;//签名
        try {
            sign = AlipaySignature.rsaSign(originalStr,private_key,charset,sign_type);
            app_id = URLEncoder.encode(app_id,charset);
            biz_content = URLEncoder.encode(biz_content,charset);

            formatStr = URLEncoder.encode(formatStr,charset);
            method = URLEncoder.encode(method,charset);
            notify_url = URLEncoder.encode(notify_url,charset);
            sign_type = URLEncoder.encode(sign_type,charset);
            timestamp = URLEncoder.encode(timestamp,charset);
            version = URLEncoder.encode(version,charset);
            sign = URLEncoder.encode(sign,charset);
            charset = URLEncoder.encode(charset,charset);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String orderString = "app_id="+app_id+"&biz_content="+biz_content+"&charset="+charset+"&format="+formatStr+"&method="+method+"&notify_url="+notify_url+"&sign_type="+sign_type+"&timestamp="+timestamp+"&version="+version+"&sign="+sign;
        logger.info("--------------------getPayRechargeOrder----orderString-----"+orderString);
        return orderString;
    }


    /**
     * 添加订单混合表
     * @param payId
     * @param order_type
     * @param user_id
     * @param status
     * @param is_del
     * @return
     */
    @Override
    public OrderMix addOrderMix(Integer payId, Integer order_type, Integer user_id, Integer status, Integer is_del) {
        OrderMix orderMix = new OrderMix();
        orderMix.setOrderType(5);
        orderMix.setOrderId(payId);
        orderMix.setUserId(user_id);
        orderMix.setStatus(1);
        orderMix.setCreateTime(new Date());
        orderMix.setIsDel(is_del);
        orderMixDao.save(orderMix);
        return orderMix;
    }

    /**
     * 成功回调添加订单冗余表
     * @param userId
     * @param price
     * @param goodsId
     * @param status
     * @param content
     * @param icon
     * @return
     */
    @Override
    public OrderPayActivity addOrderPayActivity(Integer userId, Integer price, Integer goodsId, Integer status, String content, String icon, OrderPayStyle orderPayStyle) {

        OrderPayActivity orderPayActivity = new OrderPayActivity();
        orderPayActivity.setUserId(userId);
        orderPayActivity.setAmount(price);
        orderPayActivity.setActivityGoodsId(goodsId);
        orderPayActivity.setCreateTime(new Date());
        orderPayActivity.setStatus(status);
        orderPayActivity.setContent(content);
        orderPayActivity.setIcon(icon);
        orderPayActivity.setPayType(orderPayStyle.getId());
        orderPayActivityDao.save(orderPayActivity);
        return orderPayActivity;
    }

    /**
     * 添加app订单 生成out_trade_no
     * @param userId
     * @param goodsId
     * @param price
     * @return
     */
    @Override
    public PreSalePay addOrder(Integer userId, Integer userType, Integer goodsId, Integer price, Integer activityId, Integer status, OrderPayStyle payStyle, String channel) {

        Activity activity = activityDao.findOne(activityId);
        PreSalePay preSalePay = new PreSalePay();
        preSalePay.setUserId(userId);
        preSalePay.setGoodsId(goodsId);
        preSalePay.setActivityId(activityId);
        preSalePay.setPrice(price);
        preSalePay.setUserType(userType);
        preSalePay.setCreateTime(new Date());
        preSalePay.setStatus(status);
        preSalePay.setPayStyle(payStyle);
        preSalePay.setChannel(channel);
        preSalePay.setSuccessTime(new Date());
        preSalePay.setOutTradeNo("");

        if (DateCheckUtil.isEffectiveDate(new Date(), activity.getStartTime(), activity.getEndTime())) {
            PreSalePay newPreSalePay = preSalePayDao.save(preSalePay);
            preSalePay.setOutTradeNo(WXConfig.pre_sale_out_trade + newPreSalePay.getId());
            preSalePayDao.save(preSalePay);
            return newPreSalePay;
        }

        return null;
    }

    /**
     * 商品id获取成功订单列表
     * @param goodsId
     * @param activityId
     * @return
     */
    @Override
    public List<PreSalePay> getPayList(Integer goodsId, Integer activityId) {

        List<PreSalePay> payList = preSalePayDao.findPayList(goodsId,activityId);
        return payList;
    }

    /**
     * 从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip
     *
     * @param request
     * @return ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 添加订单
     *
     * @return
     */
    @Override
    public PreSalePay addPay(Integer userId,Integer userType, Integer goodsId, Integer price, Integer activityId, Integer status, OrderPayStyle payStyle, String channel) {

        Activity activity = activityDao.findOne(activityId);
        PreSalePay preSalePay = new PreSalePay();
        preSalePay.setUserId(userId);
        preSalePay.setGoodsId(goodsId);
        preSalePay.setActivityId(activityId);
        preSalePay.setPrice(price);
        preSalePay.setUserType(userType);
        preSalePay.setCreateTime(new Date());
        preSalePay.setStatus(status);
        preSalePay.setOutTradeNo("");
        preSalePay.setPayStyle(payStyle);
        preSalePay.setChannel(channel);
        preSalePay.setSuccessTime(new Date());
        preSalePay.setIsActive(0);
        preSalePay.setActiveTime(null);

        preSalePay.setIsExpress(0);
        preSalePay.setIsExpressCenter(0);

        if (DateCheckUtil.isEffectiveDate(new Date(), activity.getStartTime(), activity.getEndTime())) {
            PreSalePay newPreSalePay = preSalePayDao.save(preSalePay);
            String outTradeNo = WXConfig.pre_sale_out_trade + newPreSalePay.getId();
            preSalePay.setOutTradeNo(outTradeNo);
            return newPreSalePay;
        }


        return null;
    }

    /**
     * 添加订单
     *
     * @return
     */
    @Override
    public PreSalePay add(Integer userId, Integer goodsId, Integer price, Integer activityId, Integer status, OrderPayStyle payStyle) {

        Activity activity = activityDao.findOne(activityId);
        PreSalePay preSalePay = new PreSalePay();
        preSalePay.setUserId(userId);
        preSalePay.setGoodsId(goodsId);
        preSalePay.setActivityId(activityId);
        preSalePay.setPrice(price);
        preSalePay.setCreateTime(new Date());
        preSalePay.setStatus(status);
        preSalePay.setUserType(1);
        preSalePay.setOutTradeNo("");
        preSalePay.setPayStyle(payStyle);
        preSalePay.setChannel("无");
        preSalePay.setSuccessTime(new Date());

        if (DateCheckUtil.isEffectiveDate(new Date(), activity.getStartTime(), activity.getEndTime())) {
            PreSalePay newPreSalePay = preSalePayDao.save(preSalePay);
            String outTradeNo = WXConfig.pre_sale_out_trade + newPreSalePay.getId();
            preSalePay.setOutTradeNo(outTradeNo);
            return newPreSalePay;
        }
        return null;
    }

    /**
     * 查找修改订单
     *
     * @param payId
     * @return
     */
    @Override
    public PreSalePay setPayStatusSuccess(Integer payId,OrderPayStyle payStyle) {

        PreSalePay preSalePay = preSalePayDao.findOne(payId);
        preSalePay.setStatus(1);
        preSalePay.setPayStyle(payStyle);
        preSalePay.setSuccessTime(new Date());
        PreSalePay savePreSalePay = preSalePayDao.save(preSalePay);
        return savePreSalePay;
    }


    /**
     * 查找订单
     *
     * @param payId
     * @return
     */
    @Override
    public PreSalePay findPayById(Integer payId) {
        PreSalePay preSalePay = preSalePayDao.findOne(payId);
        return preSalePay;
    }

    /**
     * 扫码支付  下单返回信息
     */
    @Override
    public JSONObject doUnifiedOrder(String outTradeNo, String goodsName, Integer goodsPrice, Integer goodsId, HttpServletRequest request, String openId, String wxkey) {


        String ip = IpUtil.getIpAddr(request);
        if (ip.contains(",")){
            String[] ips = ip.split(",");
            ip = ips[0];
        }
        try {
            PreSalePayServiceImpl dodo = new PreSalePayServiceImpl();
            JSONObject jsonObject = dodo.doUnifiedOrderWx(outTradeNo, goodsName, goodsPrice, goodsId, ip, openId, wxkey);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject doUnifiedOrderWx(String outTradeNo, String goodsName, Integer goodsPrice, Integer goodsId, String ip, String openId, String wxkey) {

        try {

            String appid = MyEnv.env.getProperty("fwappid");
//            String wxkey = MyEnv.env.getProperty("fwwxkey");
            String mch_id = MyEnv.env.getProperty("fwmch_id");

            config = WXPayConfigImpl.getInstance(MyEnv.env.getProperty("certPath"),appid,mch_id);
//            config = WXPayConfigImpl.getInstance(WXConfig.certpath);
            wxpay = new WXPay(config);
            total_fee = "1";
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
        data.put("notify_url", WXConfig.wxcallurl);
        data.put("trade_type", "JSAPI");
        data.put("product_id", goodsId.toString());
        data.put("openid", openId);
        // data.put("time_expire", "20170112104120");

        try {
            long timeMillis = System.currentTimeMillis();
            Long timeStamp = (timeMillis / 1000);
            Map<String, String> map = wxpay.unifiedOrder(data);

            String appid = map.get("appid");
            String nonce_str = map.get("nonce_str");
            String prepay_id = map.get("prepay_id");

            String sign = "appId=" + appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=MD5" + "&timeStamp=" + timeStamp + "&key=" + wxkey;//gushifeichuan1988abcdefg12345678
            System.out.println(sign);
            String jsJign = PreSaleMD5Util.MD5Encode(sign, "utf-8").toUpperCase();

            map.put("timeStamp", timeStamp.toString());//当前时间戳
            map.put("jsSign", jsJign);
            System.out.println(map);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public PreSalePay update(PreSalePay preSalePay) {
        return preSalePayDao.save(preSalePay);
    }

    @Override
    public PreSalePay updateActiveDate(PreSalePay preSalePay, String sign, Integer type, Date vipTime) {
        preSalePay.setSign(sign);
        preSalePay.setType(type);
        preSalePay.setVipTime(vipTime);
        preSalePay.setIsActive(1);
        preSalePay.setActiveTime(new Date());
        return preSalePayDao.save(preSalePay);
    }
}
