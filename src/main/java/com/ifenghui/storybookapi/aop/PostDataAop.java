package com.ifenghui.storybookapi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Aspect
@Order(2)
/**
 * 这里用于记录post数据的请求日志，
 * 是为了迁移到k8s添加的，迁移后前端nginx不再记录post数据，改为应用层自行记录。
 * 2019-7-18 wsl 参考配置文件中 server.tomcat.access-log-pattern 参数，有等同的postdata
 */
public class PostDataAop {

    @Autowired
    HttpServletRequest request;
    @After("execution(* com.ifenghui.storybookapi.app.*.controller.*.*(..))||execution(* com.ifenghui.storybookapi.active1902.controller.*.*(..))||execution(* com.ifenghui.storybookapi.active1902.admincontroller.*.*(..))")
    public void afterStatusService(JoinPoint jp) throws Throwable {

        try{
            request.setAttribute("postdata",map2Form(request.getParameterMap()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String map2Form(Map<String, String[]> map) throws UnsupportedEncodingException {
        if(map==null||map.size()==0){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (map == null) {
            return stringBuilder.toString();
        } else {
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                String key=entry.getKey();
                String[] values=entry.getValue();
                stringBuilder.append(key).append("=").append(URLEncoder.encode(values[0],"utf-8")).append("&");
            }
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        }
    }


}
