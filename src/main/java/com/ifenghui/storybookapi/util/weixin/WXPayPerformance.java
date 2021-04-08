package com.ifenghui.storybookapi.util.weixin;

public class WXPayPerformance {


    private WXPay wxpay;
    private WXPayConfigImpl config;
    private String out_trade_no;
    private String total_fee;

    public WXPayPerformance() throws Exception {
//        config = WXPayConfigImpl.getInstance();
        wxpay = new WXPay(config);
        total_fee = "1";
        out_trade_no = "201701017496748980290321";
    }

    /**
     * 扫码支付  下单
     */
    public void doUnifiedOrder() {
        try{
            WXPayPerformance dodo = new WXPayPerformance();
            dodo.doUnifiedOrder();
        }catch (Exception e){

        }


//        HashMap<String, String> data = new HashMap<String, String>();
//        data.put("body", "腾讯充值中心-QQ会员充值");
//        data.put("out_trade_no", out_trade_no);
//        data.put("device_info", "");
//        data.put("fee_type", "CNY");
//        data.put("total_fee", total_fee);
//        data.put("spbill_create_ip", "123.12.12.123");
//        data.put("notify_url", "http://test.letiantian.me/wxpay/notify");
//        data.put("trade_type", "NATIVE");
//        data.put("product_id", "12");
//        // data.put("time_expire", "20170112104120");
//
//        try {
//            Map<String, String> r = wxpay.unifiedOrder(data);
//            System.out.println(r);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }




    public static void main(String[] args) throws Exception {
        System.out.println("--------------->");
        WXPayPerformance dodo = new WXPayPerformance();

        dodo.doUnifiedOrder();
//        dodo.doRefund();

        // dodo.doMicropayWithPos();

        // dodo.testUnifiedOrderSpeed();
        // dodo.testUnifiedOrderSpeedWithMultiThread();
        // dodo.testRefundSpeedWithMultiThread();
        // dodo.testRefundSpeed();
        // dodo.testRefundSpeed();
        // dodo.testUnifiedOrderSpeed();
        // dodo.testRefundSpeed();
        // dodo.testHelloWorld();
        System.out.println("<---------------"); // wx2016112510573077
        Thread.sleep(5000);
    }
}

