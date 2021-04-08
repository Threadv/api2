package com.ifenghui.storybookapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.transaction.response.LogisticsNew;
import com.ifenghui.storybookapi.app.transaction.response.SetLogisticsData;
import com.ifenghui.storybookapi.style.ExpressStyle;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class ExpressUtil {

    public static LogisticsNew getExpress(Integer expressCompany, String expressCode, String address) {

        //orderId,获取物流信息
        String type = "";
        String type2 = "";
        if (expressCompany == ExpressStyle.YUAN_TONG.getId()) {
            type = "YTO";
            type2 = "圆通速递";
        } else if (expressCompany == ExpressStyle.EMS.getId()) {
            type = "EMS";
            type2 = "EMS";
        } else if (expressCompany == ExpressStyle.SHUN_FENG.getId()) {
            type = "SFEXPRESS";
            type2 = "顺丰快递";
        } else if (expressCompany== ExpressStyle.YUN_DA.getId()) {
            type = "YUNDA";
            type2 = "韵达快递";
        } else if (expressCompany == ExpressStyle.ZHONG_TONG.getId()) {
            type = "ZTO";
            type2 = "中通快递";
        }else if(expressCompany==ExpressStyle.BSHT.getId()){
            type = "HTKY";
            type2 = "百世快递";
        } else {
            type = "auto";
        }

        String host = "http://jisukdcx.market.alicloudapi.com";
        String path = "/express/query";
        String method = "GET";
        String appcode = "075e3442a30745969e73e6f2de67c889";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        //订单号
        querys.put("number", expressCode);
        querys.put("type", type);
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String a = response.toString();
            System.out.println(response.toString());
            //获取response的body
            String resString = EntityUtils.toString(response.getEntity());
//            System.out.println(EntityUtils.toString(response.getEntity()));

            //jackson json转换工具
            ObjectMapper objectMapper = new ObjectMapper();
            SetLogisticsData resp = objectMapper.readValue(resString, SetLogisticsData.class);

            LogisticsNew logisticsNew = new LogisticsNew();
            logisticsNew.setNumber(resp.getResult().getNumber());
            logisticsNew.setType(type2);
            logisticsNew.setAddress(address);
            logisticsNew.setList(resp.getResult().getList());
            logisticsNew.setIssign(resp.getResult().getIssign());
            logisticsNew.setDeliverystatus(resp.getResult().getDeliverystatus());

            return logisticsNew;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
