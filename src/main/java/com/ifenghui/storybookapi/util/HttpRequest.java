package com.ifenghui.storybookapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wml on 2017/3/2.
 */
public class HttpRequest {

    /**
    * 向指定URL发送GET方法的请求
    *
    * @param url
    *            发送请求的URL
    * @param param
    *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
    * @return URL 所代表远程资源的响应结果
    */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
    * 向指定 URL 发送POST方法的请求
    *
    * @param url
    *            发送请求的 URL
    * @param param
    *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
    * @return 所代表远程资源的响应结果
    */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HttpRequest.class);
    /**
     * 获得ip地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {

//        String ip = request.getHeader("x-forwarded-for");
//        logger.info("--------------------ip--------"+ip);
//        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
        //
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        logger.info("--------------------ip--------"+ip);
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        logger.info("--------------------ip--------"+ip);
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        logger.info("--------------------ip--------"+ip);
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED");
//        }
//        logger.info("--------------------ip--------"+ip);
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
//        }
//        logger.info("--------------------ip--------"+ip);
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_FORWARDED_FOR");
//        }
//        logger.info("--------------------ip--------"+ip);
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_FORWARDED");
//        }
//        logger.info("--------------------ip--------"+ip);
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_VIA");
//        }
//        logger.info("--------------------ip--------"+ip);
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("REMOTE_ADDR");
//        }
//        logger.info("--------------------ip--------"+ip);
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
        String ip = request.getHeader("X-Forwarded-For");
        logger.info("--------------------ip--------"+ip);
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        logger.info("--------------------ip--------"+ip);
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        logger.info("--------------------ip---getRemoteAddr-----"+request.getRemoteAddr());
        return request.getRemoteAddr();
    }

    /**
     * 由ip地址分析城市
     * @param request
     * @return
     */
    public static String getAddrByIp(HttpServletRequest request){


        String host = "https://dm-81.data.aliyun.com";
        String path = "/rest/160601/ip/getIpInfo.json";
        String method = "GET";
        String appcode = "075e3442a30745969e73e6f2de67c889";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        //        querys.put("ip", "0.0.0.0");
        String ip = HttpRequest.getIpAddr(request);
        querys.put("ip", ip);

        String addr = "";
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
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            String a = response.toString();

            String resString = EntityUtils.toString(response.getEntity());

            //jackson json转换工具
            ObjectMapper objectMapper = new ObjectMapper();
            GetAddrByIp getAddrByIp = new GetAddrByIp();

            GetAddrByIp resp = objectMapper.readValue(resString, GetAddrByIp.class);

            GetAddrByIpInfo addrData = new GetAddrByIpInfo();

            addr = resp.getData().getCity();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return addr;
    }


    public static GetAddrByIpInfo getAddrByIpInfo(HttpServletRequest request){
        String host = "https://dm-81.data.aliyun.com";
        String path = "/rest/160601/ip/getIpInfo.json";
        String method = "GET";
        String appcode = "075e3442a30745969e73e6f2de67c889";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        //        querys.put("ip", "0.0.0.0");
        String ip = HttpRequest.getIpAddr(request);
        querys.put("ip", ip);

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
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            String a = response.toString();

            String resString = EntityUtils.toString(response.getEntity());

            //jackson json转换工具
            ObjectMapper objectMapper = new ObjectMapper();
            GetAddrByIp resp = objectMapper.readValue(resString, GetAddrByIp.class);
            return resp.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
