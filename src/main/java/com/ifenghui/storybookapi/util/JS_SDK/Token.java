package com.ifenghui.storybookapi.util.JS_SDK;

import com.ifenghui.storybookapi.util.weixin.LoadJsonUtil;
import net.sf.json.JSONObject;

public class Token {


    private static String access_token = "";

    private static String jsapi_ticket = "";

    public static int time = 0;

    private static int expires_in = 7200;

    static {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    time++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
            }
        });
        t.start();
    }

    public static String getToken() {
        if ("".equals(access_token) || access_token == null) {
            send();
        } else if (time > expires_in) {
            //当前token已经失效，从新获取信息
            send();
        }
        return access_token;
    }

    public static String getTicket() {
        if ("".equals(jsapi_ticket) || jsapi_ticket == null) {
            send();
        } else if (time > expires_in) {
            //当前token已经失效，从新获取信息
            send();
        }
        return jsapi_ticket;
    }

    private static void send() {
//        String url = WXConfig.server_token_url + "&corpid=" + WXConfig.appid + "&corpsecret=" + WXConfig.appsecret;
        String url = WXConfig.server_token_url + "&appid=" + WXConfig.appid + "&secret=" + WXConfig.appsecret;
        String wxjson = LoadJsonUtil.getJSON(url);
        JSONObject json = JSONObject.fromObject(wxjson);

        System.out.println(json);
        access_token = json.getString("access_token");
        String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        String  jsapi = LoadJsonUtil.getJSON(ticket_url);
        JSONObject jsapi_tic_json = JSONObject.fromObject(jsapi);

        System.out.println(jsapi_tic_json);
        jsapi_ticket= jsapi_tic_json.getString("ticket");
        time = 0;
//        if(!"".equals(access_token)&&!"".equals(jsapi_ticket)){
//            new Thread
//        }

    }

}
