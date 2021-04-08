package com.ifenghui.storybookapi.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.wallet.response.IosPayResponse;
import com.ifenghui.storybookapi.util.ios.IosNotify;
import org.json.JSONObject;

import java.io.IOException;

import static com.ifenghui.storybookapi.util.SendPostUtil.sendPost;

/**
 * 支付回调验证工具
 */
public class IosNotifyHttpUtil {

    public static IosNotify getNotify(String appStoreMsg) throws IOException {
        String appStorePassword="c863a98d40454d9a8e1e66ab74c4ebfd";
        IosPayResponse response = new IosPayResponse();
        Integer isTest = 0;
        String jsonMsg = "{'receipt-data':'" + appStoreMsg + "','password':'"+appStorePassword+"'}";
//        String url;
//        String url = "https://sandbox.itunes.apple.com/verifyReceipt";
        String url = "https://buy.itunes.apple.com/verifyReceipt";
//        if (isSandbox) {
//            url = urlSanbox;
//            isTest = 1;//测试数据
//        } else {
//            url = "https://buy.itunes.apple.com/verifyReceipt";
//        }

        String flag = "";
        String iap = "";
        flag = "_";


        JSONObject jsonObj = new JSONObject(jsonMsg);
        int status = -1;
        String resp = sendPost(url, jsonObj);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        IosNotify iosNotify=mapper.readValue(resp,IosNotify.class);
        iosNotify.setIsSandBox(0);
        if(iosNotify.getStatus()==21007){
            url = "https://sandbox.itunes.apple.com/verifyReceipt";
            resp=sendPost(url,jsonObj);
            iosNotify=mapper.readValue(resp,IosNotify.class);
            iosNotify.setIsSandBox(1);
            isTest = 1;
        }

        return iosNotify;
    }



}
