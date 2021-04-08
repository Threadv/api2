package com.ifenghui.storybookapi.util;

import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by narella on 2017/7/4.
 */
public class AlipayNotify {

    Map<String, String> m;
    String callbackMsg;
    String orderIdstr;
    Long orderId;
    String trade_no;
    String sign;
    String tradeStatus;
    String outTradeNo;

    public AlipayNotify(HttpServletRequest request, String orderPrefix) {
        Map<String, String[]> params = request.getParameterMap();
        sign = request.getParameter("sign");
        m = new HashMap<>();

        if (params.get("body") != null) {
            m.put("body", params.get("body")[0]);
        }
        if (params.get("buyer_id") != null) {
            m.put("buyer_id", params.get("buyer_id")[0]);
        }
        if (params.get("gmt_create") != null) {
            m.put("gmt_create", request.getParameter("gmt_create"));
        }
        if (params.get("gmt_payment") != null) {
            m.put("gmt_payment", request.getParameter("gmt_payment"));
        }
        if (params.get("notify_id") != null) {
            m.put("notify_id", params.get("notify_id")[0]);
        }
        if (params.get("notify_time") != null) {
            m.put("notify_time", params.get("notify_time")[0]);
        }
        if (params.get("notify_type") != null) {
            m.put("notify_type", params.get("notify_type")[0]);
        }
        if (params.get("out_trade_no") != null) {
            m.put("out_trade_no", params.get("out_trade_no")[0]);
        }
        if (params.get("seller_email") != null) {
            m.put("seller_email", params.get("seller_email")[0]);
        }
        if (params.get("seller_id") != null) {
            m.put("seller_id", params.get("seller_id")[0]);
        }
        if (params.get("subject") != null) {
            m.put("subject", params.get("subject")[0]);
        }
        if (params.get("trade_status") != null) {
            m.put("trade_status", params.get("trade_status")[0]);
        }
        if (params.get("out_trade_no") != null) {
            m.put("out_trade_no", params.get("out_trade_no")[0]);
        }
        if (params.get("trade_no") != null) {
            m.put("trade_no", params.get("trade_no")[0]);
        }
        if (params.get("sign") != null) {
            m.put("sign", params.get("sign")[0]);
        }

        //属性参数
        if (params.get("trade_no") != null) {
            this.trade_no = params.get("trade_no")[0];
        }
        if (params.get("out_trade_no") != null) {
            this.outTradeNo = params.get("out_trade_no")[0];
        }
        if (params.get("trade_status") != null) {
            this.tradeStatus = params.get("trade_status")[0];
        }
        this.callbackMsg = Map2StringUtil.mapToString(m);
        orderIdstr = params.get("out_trade_no")[0];
        if (this.orderIdstr.contains("_")) {
            if (!this.orderIdstr.split("_")[0].equals(orderPrefix)) {
                throw new RuntimeException("not find prefix" + orderPrefix);
            }
            orderId = Long.parseLong(this.orderIdstr.split("_")[1]);
        }
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    /**
     * 获取订单是否支付成功的状态
     *
     * @return
     */
    public boolean getTradeSuccess() {
        if (this.tradeStatus.equals("TRADE_SUCCESS") || this.tradeStatus.equals("TRADE_FINISHED")) {
            return true;
        } else {
            return false;
        }
    }

    public Map<String, String> getRequestMap() {
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

    public static boolean rsaAliPayCheckV1(String alipayPublicKey, HttpServletRequest request) throws Exception {
        /**
         * 获取支付宝POST过来反馈信息
         */
        Map<String, String> params = VersionUtil.getParams(request);
        String charset = "utf-8";
        boolean verify_result = AlipaySignature.rsaCheckV1(params, alipayPublicKey, charset, "RSA");
        if (!verify_result) {
            return false;
        } else {
            return true;
        }
    }
}
