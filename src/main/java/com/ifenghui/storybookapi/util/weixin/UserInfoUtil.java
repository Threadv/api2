package com.ifenghui.storybookapi.util.weixin;


import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xsx
 */

public class UserInfoUtil {
    public static Map<String, String> wxUserInfo(String code) {

//        String  appid = "wx31edd56a45a12c7f";
//        String secret = "6c683afad5338b3e54189a2a48091401";

        Map<String, String> map = new HashMap<>();
        String appid = "wx31edd56a45a12c7f";
        String appsecret = "6c683afad5338b3e54189a2a48091401";

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
        String loadJson = LoadJsonUtil.getJSON(url);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(loadJson);
        //用户的唯一标识（openid）
        String openid = String.valueOf(json.get("openid"));
        String access_token = String.valueOf(json.get("access_token"));

        map.put("openid", openid);
        map.put("access_token", access_token);

        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        String requestUrl ="https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&scope=snsapi_userinfo"+"&lang=zh_CN";
        //请求
        String userInfoJson =LoadJsonUtil.getJSON(requestUrl);

        JSONObject userInfo = JSONObject.fromObject(userInfoJson);
        //nick,phone,type,unionId,openId,icon
        String nickname = String.valueOf(userInfo.get("nickname"));
        String unionid = String.valueOf(userInfo.get("unionid"));
        //icon 图标
        String headimgurl = String.valueOf(userInfo.get("headimgurl"));
        String sex = String.valueOf(userInfo.get("sex"));
        String country = String.valueOf(userInfo.get("country"));

        map.put("nickname", nickname);
        map.put("unionid", unionid);
        map.put("headimgurl", headimgurl);
        map.put("sex", sex);
        map.put("country", country);
        return map;
    }
}