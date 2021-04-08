package com.ifenghui.storybookapi.app.presale.controller;



import com.ifenghui.storybookapi.app.presale.entity.Activity;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleUser;
import com.ifenghui.storybookapi.app.presale.response.GetUserIdResponse;
import com.ifenghui.storybookapi.app.presale.response.PreSaleGetUserResponse;
import com.ifenghui.storybookapi.app.presale.service.ActivityService;
import com.ifenghui.storybookapi.app.presale.service.PreSaleUserService;
import com.ifenghui.storybookapi.app.presale.service.YiZhiActivityUserService;
import com.ifenghui.storybookapi.util.weixin.LoadJsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
//@EnableAutoConfiguration
@Api(value = "用户", description = "用户相关接口")
@RequestMapping("/sale/user")
public class PreSaleUserController {

    Logger logger= Logger.getLogger(PreSaleUserController.class);

    @Autowired
    HttpServletResponse response;
    @Autowired
    PreSaleUserService saleUserService;

    @Autowired
    YiZhiActivityUserService userService;

    @Value("${fwappid}")
    String wxAppId;

    @Value("${fwsecret}")
    String wxSecret;

    @Autowired
    ActivityService activityService;

    @RequestMapping(value = "/add_wxuser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信注册用户", notes = "微信注册用户")
    PreSaleGetUserResponse addUser(
            @ApiParam(value = "code") @RequestParam String code
    ) {

        PreSaleGetUserResponse response = new PreSaleGetUserResponse();
        PreSaleUser preSaleUser = null;
        try {
            preSaleUser = saleUserService.addUser(code);
        } catch (Exception e) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg(e.getMessage());
            e.printStackTrace();
        }
        response.setPreSaleUser(preSaleUser);
        return response;
    }

    @RequestMapping(value = "/get_wxuser", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "微信注册用户", notes = "微信注册用户")
    PreSaleGetUserResponse getWxUser(
            @ApiParam(value = "unionId") @RequestParam String unionId
    ) {

        PreSaleGetUserResponse response = new PreSaleGetUserResponse();
        PreSaleUser preSaleUser = null;
        try {
            preSaleUser = saleUserService.getUserByUnionid(unionId);
        } catch (Exception e) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg(e.getMessage());
            e.printStackTrace();
        }
        response.setPreSaleUser(preSaleUser);
        return response;
    }

    @RequestMapping(value = "/get_userid", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过token获得userId", notes = "通过token获得userId")
    GetUserIdResponse getUserId(
            @ApiParam(value = "用户token") @RequestParam String token
    ) {

        GetUserIdResponse response = new GetUserIdResponse();
        //通过token获取userId
        Integer userId;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
            response.setUserId(userId);
            return response;
        } catch (Exception e) {
            response.getStatus().setCode(2);
            response.getStatus().setMsg("token相关用户不存在！");
            return response;
        }
    }

    /**
     * 微信授权跳转
     * @param code
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/redirect_auth", method = RequestMethod.GET)
    @ApiOperation(value = "提示授权跳转", notes = "")
    public String redirectAuth(
            @ApiParam(value = "type") @RequestParam String type,
            String code
    ) {

        String appid = wxAppId;
        String secret = wxSecret;

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";

        String getTokenResponse = LoadJsonUtil.getJSON(url);
        // 获得 access_token  openid
        JSONObject jsonObject = JSONObject.fromObject(getTokenResponse);
        //用户的唯一标识（openid）
        String openid = String.valueOf(jsonObject.get("openid"));
        String access_token = String.valueOf(jsonObject.get("access_token"));
        if(openid==null){
//            try {
//                response.getWriter().print("openid is null");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return jsonObject.toString();
        }
        logger.info("openid::"+openid);
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&scope=snsapi_userinfo" + "&lang=zh_CN";
        String getUserInfoResponse = LoadJsonUtil.getJSON(requestUrl);
        JSONObject jsonObjectUser = JSONObject.fromObject(getUserInfoResponse);
        String unionid = String.valueOf(jsonObjectUser.get("unionid"));
        String headImg = String.valueOf(jsonObjectUser.get("headimgurl"));
        String nickName = String.valueOf(jsonObjectUser.get("nickname"));
        if (unionid == null || unionid.equals("null") || headImg == null || nickName == null) {
            //不是微信打开的
            try {
                response.getWriter().print("unionid error:");
                response.getWriter().print(unionid);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        PreSaleUser preSaleUser=new PreSaleUser();
        preSaleUser.setUnionId(unionid);
        preSaleUser.setOpenId(openid);
        preSaleUser.setNick(nickName);
        preSaleUser.setIcon(headImg);
        saleUserService.addUser(preSaleUser);
//        articleShareService.createArticleShare(articleId, userId, nickName, headImg, unionid,UserLoginStyle.WX_LOGIN);

//        logger.info("=======redirectShareUrl-------------"+redirectShareUrl);
        Activity activity= activityService.findByKey(type);


        String redirectShareUrl = activity.getWxRedirectUri()+"?unionId="+unionid;
        logger.info("redirect:"+redirectShareUrl);
//        returnUrl = authUrl.getUri() + "?type="+type+"&parent=" + parent;
        return "redirect:"+redirectShareUrl ;
    }

}
