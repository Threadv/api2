package com.ifenghui.storybookapi.util;

import com.ifenghui.storybookapi.app.app.entity.Config;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.system.service.GeoipService;
import com.ifenghui.storybookapi.app.user.controller.UserTokenController;
import com.ifenghui.storybookapi.config.StoryConfig;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VersionUtil {

    private static Logger logger = Logger.getLogger(UserTokenController.class);

    public static String getVerionInfo(HttpServletRequest request){
        String ver;
        String userAgent = request.getHeader("User-Agent");
//        userAgent="Mozilla/5.0 (Linux; Android 8.1.0; V1809A Build/OPM1.171019.026; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/044705 Mobile Safari/537.36 MMWEBID/2805 MicroMessenger/7.0.4.1420(0x2700043C) Process/tools NetType/WIFI Language/zh_CN";
//        logger.info("--------------------otherRegister--------userAgent----"+userAgent);
        if(userAgent==null){
            return "0.0.0";
        }
        String[] agentArr = userAgent.split(":");
        if (userAgent.contains("ios")&&userAgent.contains("phone_version")) {
            //ios
            ver = agentArr[3];
//            ver = ver.substring(0,ver.length() - 1)
            ver = ver.split(";")[0];
//            logger.info("--------------------otherRegister--------ios-ver---"+ver);
        }else if(userAgent.contains("Android")&&userAgent.contains("storyship")){
            //安卓
            ver = agentArr[0].split("_")[1];
//            logger.info("--------------------otherRegister--------Android-ver---"+ver);
//            ver = "1.5.0";
        }else{
            ver = "0.0.0";
        }
        if (ver.startsWith("0.")){
            ver = ver.substring(2, ver.length());
        }

        return ver;
    }

    /**
     * 验证输入的版本是否允许使用
     * @param request
     * @param ver
     * @return
     */
    public static boolean isAllow(HttpServletRequest request,String ver){
        String currentVer=getVerionInfo(request);
        if(compare(currentVer,ver)<=0){
            return true;
        }
        return false;
    }
    public static boolean isAllow(String currentVer,String ver){
        if(compare(currentVer,ver)<=0){
            return true;
        }
        return false;
    }

    public static int compare(String currentVer,String appVer){

            //大于限制
            // 如果输入版本大于当前版本返回1
            // 如果输入版本小于当前版本返回-1
            // 版本相等返回0
//            currentVer=this.appVer;

        String[] currentVerArr=currentVer.split("[.]");
        String[] inputVerArr=appVer.split("[.]");


            if(Integer.parseInt(inputVerArr[0])>Integer.parseInt(currentVerArr[0])){
                return 1;
            }else if(Integer.parseInt(inputVerArr[0])<Integer.parseInt(currentVerArr[0])){
                return -1;
            }else{
                // return 0
            }
            if(Integer.parseInt(inputVerArr[1])>Integer.parseInt(currentVerArr[1])){
                return 1;
            }else if(Integer.parseInt(inputVerArr[1])<Integer.parseInt(currentVerArr[1])){
                return -1;
            }else{
                // return 0
            }
            if(Integer.parseInt(inputVerArr[2])>Integer.parseInt(currentVerArr[2])){
                return 1;
            }else if(Integer.parseInt(inputVerArr[2])<Integer.parseInt(currentVerArr[2])){
                return -1;
            }else{
                return 0;
            }


    }


    public static String getChannelInfo(HttpServletRequest request){
        String channel = request.getHeader("channel");

        return urlDecode(channel);
    }

    public static String urlDecode(String str){
        if(str != null){
            try{
                str = URLDecoder.decode(str,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            str = "";
        }
        return str;
    }

    public static Map<String, String> getParams(HttpServletRequest request){
        Map<String, String> params= new HashMap<>();
        for (Iterator iter = request.getParameterMap().keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) request.getParameterMap().get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }

    public static WalletStyle getWalletStyle(HttpServletRequest request){
        WalletStyle walletStyle = WalletStyle.ANDROID_WALLET;
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("ios")) {
            walletStyle = WalletStyle.IOS_WALLET;
        }
        return walletStyle;
    }

    public static StoryConfig.Platfrom getPlatform(HttpServletRequest request){
        //验证android或ios是否可见ad
        String ua=(String)request.getHeader("user-agent");
        if(ua == null) {
            return StoryConfig.platform=StoryConfig.Platfrom.OTHER;
        }
        if(ua.indexOf("Android")!=-1){
            return StoryConfig.platform= StoryConfig.Platfrom.ANDROID;
        }else if(ua.indexOf("ios_version")!=-1){
            return StoryConfig.platform= StoryConfig.Platfrom.IOS;
        } else {
            return  StoryConfig.platform=StoryConfig.Platfrom.OTHER;
        }
    }
    //返回平台类型1，android，2ios
    public static String getPlatformStr(HttpServletRequest request){
        StoryConfig.Platfrom platform=getPlatform(request);
        if(platform== StoryConfig.Platfrom.ANDROID){
            return "1";
        }else if(platform== StoryConfig.Platfrom.IOS){
            return "2";
        }else{
            return "";
        }
    }

    public static Integer getIosIsCheck(HttpServletRequest request, ConfigService configService,GeoipService geoipService) {
        String userAgent = request.getHeader("User-Agent");
        String ver = VersionUtil.getVerionInfo(request);
        Integer isCheck = 0;
        logger.info("-------------------- now --------ios-ver---"+ver);
        if (VersionUtil.getPlatform(request)== StoryConfig.Platfrom.IOS) {
            //判断版本是否在审核中，获取config的ver是否等于当前版本
            String key = "version";
            if (userAgent.contains("appname:zhijianStory")) {
                key = "zhijianStory" + "_version";
            }
//            Config config = configService.getConfigByKey(key);
            //获取IP 根据IP判断是否在审核中
            String ip = HttpRequest.getIpAddr(request);
//            String lock=configService.getConfigByKey("peview_lock").getVal();
//            if(lock.trim().equals("1")){
                String country = geoipService.getCountry(ip);
                if(country!=null){
                    if(!country.equals("CN")&&!country.equals("TW")&&!country.equals("HK")&&!country.equals("HK")){
                        isCheck=1;
                        logger.info("iosIsCheck====ip==========="+ip+"=========country========"+country + "=====isCheck"+isCheck);
                    }
                }
//            }

            if(configService.isIosReview(request)){
                isCheck=1;
            }

//            logger.info("配置   ver"+config.getVal());
        }

        logger.info("request   ver"+ver);
        return isCheck;
    }

//    public static void main(String[] args){
////        phone_version:iPhone X; ios_version:iOS 11.4; app_version:0.2.10.0;
//        VersionUtil.getVerionInfo(null);
//
//    }

}
