package com.ifenghui.storybookapi.util;

import com.ifenghui.storybookapi.config.StoryConfig;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by narella on 2018/9/17.
 */
public class HeadManagerUtil {

    /**
     * 从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip
     * @param request
     * @return ip
     */
    public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if(forwarded != null){
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }

    public static String getManagerToken(HttpServletRequest request) {
        String managertoken = request.getHeader("manager-token");
        return managertoken;
    }

    public static String getUserAgent(HttpServletRequest request){
        String userAgent=request.getHeader("user-agent");
        return userAgent;
    }

    public static boolean getIsAndroid(HttpServletRequest request){
        String userAgent=getUserAgent(request);
        if(userAgent==null){
            return false;
        }
        if(userAgent.indexOf("Android")!=-1){
            return true;
        }
        return false;
    }

    public static StoryConfig.Platfrom getPlatform(HttpServletRequest request){
        String userAgent=getUserAgent(request);
        if(userAgent==null){
            return StoryConfig.Platfrom.OTHER;
        }
        if(userAgent.indexOf("Android")!=-1){
            return StoryConfig.Platfrom.ANDROID;
        }
        if(userAgent.indexOf("iOS")!=-1){
            return StoryConfig.Platfrom.IOS;
        }
        return StoryConfig.Platfrom.OTHER;
    }



}
