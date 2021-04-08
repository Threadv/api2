package com.ifenghui.storybookapi.adminapi.controlleradmin;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.baidu.ueditor.ActionEnter;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
//@Component

@RequestMapping(value = "/adminapi/oss", name = "oss处理")
public class OSSController {

//    Logger logger= LogManager.getLogger(OSSController.class);
    Logger logger= Logger.getLogger(OSSController.class);
    @Value("${oss.accountendpoint}")
    String endpoint;

    @Value("${oss.accesskeyid}")
    String accessKeyId;

    @Value("${oss.accesskeysecret}")
    String accessKeySecret;

    @Value("${oss.backet}")
    String backet;

    @Value("${oss.url}")
    String ossUrl;

//    @Value("${ueditor.basepath}")
    String ueditorBase="/data/www";



    @RequestMapping(value = "/ueditor-controller", method = RequestMethod.GET)
    public void ueditorController(HttpServletRequest request, HttpServletResponse response){
//        request.setCharacterEncoding( "utf-8" );
        response.setHeader("Content-Type" , "application/javascript");
        response.setCharacterEncoding("utf-8");
        String str=request.getQueryString();
        String rootPath = request.getSession().getServletContext().getRealPath( "/" );
//        URL url = this.getClass().getClassLoader().getResource("application.properties");
        URL url = this.getClass().getClassLoader().getResource("application.properties");
//        if(true){
//            throw new RuntimeException("ueditor3:"+rootPath+"v3/oss/config.json >"+url);
//        }

        logger.info("ueditor:"+url);
        File f=new File(url.getPath());
        try {
            logger.info("ueditor2:"+f.getParent());
            String resp=new ActionEnter( request, ueditorBase).exec();
            response.getWriter().write( resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/ostObjectPolicy", method = RequestMethod.GET)
    @ApiOperation("oss授权")
    public void ostObjectPolicy(
            HttpServletRequest request, HttpServletResponse response
//            ,@RequestParam("callback") @ApiParam("callback") String callback
//            ,@RequestParam("dir") @ApiParam("dir") String dir
    ) throws ServletException, IOException {
        //暂时不做验证
//        if(request.getHeader("referer")==null){
//
//        }
        response.setCharacterEncoding("utf-8");
        this.ostObjectPolicydoGet(request,response);
    }

    private String getKey(HttpServletRequest request){
        String srcname=request.getParameter("srcname");
        String dir=request.getParameter("dir");
        String usesrc=request.getParameter("usesrc");
        if(srcname==null){
            return null;

        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        File f=new File(srcname);
        String suffix = f.getName().substring(f.getName().lastIndexOf(".") + 1);

        String keyFileName=System.currentTimeMillis()+"."+suffix;
        if(usesrc!=null&&"1".equals(usesrc)){
            try {
                keyFileName= URLDecoder.decode(f.getName(),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String key=dir+"/"+sdf.format(new java.util.Date())+"/"+keyFileName;

        return key;
    }

    /**
     * 官方demo,返回直传签名
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ostObjectPolicydoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String endpoint =this.endpoint;
        String accessId =accessKeyId;
        String accessKey = accessKeySecret;
        String bucket = backet;
        String dir = request.getParameter("dir");
        String host = "http://" + bucket + "." + endpoint;
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        try {
            long expireTime = 60;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            //respMap.put("expire", formatISO8601Date(expiration));
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            String key=getKey(request);
            if(key!=null){
                respMap.put("key",key);
            }
            JSONObject ja1 = JSONObject.fromObject(respMap);
            System.out.println(ja1.toString());
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Methods", "GET, POST");
            response(request, response, ja1.toString());

        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    private void response(HttpServletRequest request, HttpServletResponse response, String results) throws IOException {
//        String callbackFunName = request.getParameter("callback");
//        if (callbackFunName==null || callbackFunName.equalsIgnoreCase("")){
//            response.getWriter().println(results);
//        }

//        else{
            response.getWriter().println(results);
//        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();
    }

}
