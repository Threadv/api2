package com.ifenghui.storybookapi.app.temp.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.temp.response.TestGetNowTimeResponse;
import com.ifenghui.storybookapi.app.temp.response.TestResponse;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.util.IpUtil;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Controller
@EnableAutoConfiguration
//@EnableAutoConfiguration
//@SpringBootApplication
//@ConfigurationProperties(prefix = "spring")
@RequestMapping("/api")
public class TestController {
//    @JsonBackReference
//    @RequestMapping("/index")
//    @ResponseBody
//    TestResponse home(@RequestParam Long id) {
////        return "Hello World!";
//        TestResponse t=new TestResponse();
//        t.setId(id.intValue());
//        return t;
//    }

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value="/test",method = RequestMethod.GET)
    @ResponseBody
    TestResponse test(@ApiParam(value = "ID")@RequestParam Long id) {
        AlipaySignature alipaySignature= new AlipaySignature();
        TestResponse t = new TestResponse();
//        return "Hello World!";
        System.out.println(alipaySignature);
        t.setId(id.intValue());
        return t;
    }
    @RequestMapping(value="/getNowTime",method = RequestMethod.GET)
    @ResponseBody
    TestGetNowTimeResponse getNowTime() {

        Date nowTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TestGetNowTimeResponse testGetNowTimeResponse = new TestGetNowTimeResponse();
        TimeZone timeZone= TimeZone.getDefault();
        testGetNowTimeResponse.setNowTimeStr(sdf.format(nowTime)+"timeZone:"+timeZone);
        return testGetNowTimeResponse;
    }
//    public static void main(String[] args) {
//        SpringApplication.run(TestController.class, args);
//    }

        @RequestMapping(value="/getIp",method = RequestMethod.GET)
        @ResponseBody
        BaseResponse test2() {
//测试获取用户ip
            String ip=IpUtil.getIpAddr(request);
            String ip1 = request.getHeader("X-Forwarded-For");

            String ip2 = request.getHeader("X-Real-IP");

            String srcIp = getRemoteAddr(request);
            BaseResponse testGetNowTimeResponse = new BaseResponse();
            testGetNowTimeResponse.getStatus().setMsg("ip:"+ip+" "+"ip1:"+ip1+" "+"ip2:"+ip2+" srcIp:"+srcIp);
            return testGetNowTimeResponse;
        }
    protected static String getRemoteAddr(HttpServletRequest request) {
        String caller = request.getHeader("X-Forwarded-For");
        if (caller == null || caller.isEmpty() || caller.equals("unknown")) {
            caller = request.getHeader("Proxy-Client-IP");
        }

        if (caller == null || caller.isEmpty() || caller.equals("unknown")) {
            caller = request.getHeader("WL-Proxy-Client-IP");
        }

        if (caller == null || caller.isEmpty() || caller.equals("un")) {
            caller = request.getHeader("HTTP_CLIENT_IP");
        }

        if (caller == null || caller.isEmpty() || caller.equals("un")) {
            caller = request.getHeader("X-Real-IP");
        }

        if (caller == null || caller.isEmpty() || caller.equals("unknown")) {
            caller = request.getRemoteAddr();
        }
        return caller;
    }

}
