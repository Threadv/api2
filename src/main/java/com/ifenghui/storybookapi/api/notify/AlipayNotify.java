package com.ifenghui.storybookapi.api.notify;

import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.ifenghui.storybookapi.util.Map2StringUtil.transMapToString;

/**
 * Created by narella on 2017/7/4.
 */
public class AlipayNotify {
    Map<String,String> m;
    String callbackMsg;
    String orderIdstr;
    Long orderId;
    String trade_no;
    String sign;
    public AlipayNotify(HttpServletRequest request) throws ApiNotFoundException {
        Map<String, String[]> params = request.getParameterMap();
//        logger.info("--------------------alipayNotify------11111----params-"+params);

//        System.out.println("*****"+request.toString());
//        System.out.println("*****"+request.getParameter("gmt_create"));
        sign=request.getParameter("sign");
//        logger.info("--------------------alipayNotify----sign----"+sign);
        m=new HashMap();
//        Map m=new TreeMap();
        m.put("body",params.get("body")[0]);
//        m.put("buyer_email",params.get("buyer_email")[0]);
        m.put("buyer_id",params.get("buyer_id")[0]);
//        m.put("discount",params.get("discount")[0]);
        m.put("gmt_create",request.getParameter("gmt_create"));
        m.put("gmt_payment",request.getParameter("gmt_payment"));
//        m.put("is_total_fee_adjust",params.get("is_total_fee_adjust")[0]);
        m.put("notify_id",params.get("notify_id")[0]);
        m.put("notify_time",params.get("notify_time")[0]);
        m.put("notify_type",params.get("notify_type")[0]);
        m.put("out_trade_no",params.get("out_trade_no")[0]);
//        m.put("payment_type",params.get("payment_type")[0]);
//        m.put("price",params.get("price")[0]);
//        m.put("quantity",params.get("quantity")[0]);
        m.put("seller_email",params.get("seller_email")[0]);
        m.put("seller_id",params.get("seller_id")[0]);
        m.put("subject",params.get("subject")[0]);
//        m.put("total_fee",params.get("total_fee")[0]);
        m.put("trade_no",params.get("trade_no")[0]);
        this.trade_no=params.get("trade_no")[0];
        m.put("trade_status",params.get("trade_status")[0]);
//        m.put("use_coupon",params.get("use_coupon")[0]);
//sign type
        m.put("sign",params.get("sign")[0]);

//        logger.info("--------------------alipayNotify----aaaaa----");
        callbackMsg=transMapToString(m);
//        logger.info("--------------------alipayNotify----bbbb----");
//            String prefix=env.getProperty("order.prefix");
//        prefix = "order";
//        logger.info("--------------------alipayNotify----prefix----"+prefix);
        orderIdstr= params.get("out_trade_no")[0];

        String prefix= MyEnv.env.getProperty("order.prefix");
        if(this.orderIdstr.contains("_")) {
            orderId = Long.parseLong(this.orderIdstr.split("_")[1]);
            if (!this.orderIdstr.split("_")[0].equals(prefix)) {
                throw new ApiNotFoundException("order format error;not find orderId:"+this.orderIdstr);
            }
        }
    }

    public Map<String,String> getRequestMap() {
        return m;
    }

    public String getCallbackMsg() {
        return callbackMsg;
    }

    public String getOrderIdstr() {
        return orderIdstr;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public String getSign() {
        return sign;
    }
}
