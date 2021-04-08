package com.ifenghui.storybookapi.app.presale.controller;



import com.ifenghui.storybookapi.api.response.base.ApiStatusResponse;
import com.ifenghui.storybookapi.app.app.controller.SmsController;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.response.CheckPhoneCodeResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleUser;
import com.ifenghui.storybookapi.app.presale.service.PreSalePayService;
import com.ifenghui.storybookapi.app.presale.service.PreSaleUserService;
import com.ifenghui.storybookapi.app.user.controller.UserTokenController;
import com.ifenghui.storybookapi.app.user.response.OtherLoginResponse;
import com.ifenghui.storybookapi.app.user.response.OtherRegisterResponse;
import com.ifenghui.storybookapi.app.user.response.PhoneLoginResponse;
import com.ifenghui.storybookapi.app.user.response.PhoneRegisterResponse;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByCodeResponse;
import com.ifenghui.storybookapi.util.DateUtil;
import com.ifenghui.storybookapi.util.PhoneUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import weixin.Utils.MD5Util;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "兑换码-基本复制的分销vipcode用于激活操作", description = "兑换码/用户注册登录相关接口")
@RequestMapping("/sale/vipCode")
public class PreSaleVipCodeController {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(PreSaleVipCodeController.class);

    @Autowired
    PreSalePayService preSalePayService;

    @Autowired
    PreSaleUserService userService;

//    @Autowired
//    RemoteAppApiService remoteAppApiService;
    @Autowired
    SmsController smsController;

    @Autowired
    Environment env;

    @Autowired
    UserTokenController userTokenController;

    @Autowired
    com.ifenghui.storybookapi.app.transaction.controller.VipCodeController vipCodeController;

    @RequestMapping(value = "/update_passwd", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改密码")
    BaseResponse updatePw(
            @ApiParam(value = "phone") @RequestParam String phone,
            @ApiParam(value = "newPassword") @RequestParam String newPassword,
            @ApiParam(value = "短信验证码") @RequestParam String code
    ) throws Exception {

        logger.info("======newPassword====" + newPassword+"===phone = "+phone +"-------code="+code);

        BaseResponse response = new BaseResponse();
        if (phone == null || phone.equals("") || phone.equals("null")) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("请输入手机号！");
            logger.info("请输入手机号！");
            return response;
        }
        if (!PhoneUtil.isPhone(phone).equals("")) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg(PhoneUtil.isPhone(phone));
            logger.info(PhoneUtil.isPhone(phone));
            return response;
        }

        if (newPassword == null || newPassword.equals("") || newPassword.equals("null")) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("请输入密码！");
            logger.info("请输入密码！");
            return response;
        }
        newPassword = MD5Util.MD5Encode(newPassword, "utf-8");

        if (code == null || code.equals("") || code.equals("null")) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("请输入验证码！");
            logger.info("请输入验证码！");
            return response;
        }

//        String url = MyEnv.env.getProperty("checkCode");
//        String param = "phone=" + phone
//                + "&code=" + code;
//        String res = HttpRequest.sendGet(url, param);
//        JSONObject jsonObject = JSONObject.fromObject(res);
        CheckPhoneCodeResponse jsonObject=smsController.checkPhoneCode(phone,code);

        int resCode =jsonObject.getStatus().getCode();
        logger.info("-=========================" + jsonObject);
        if (resCode != 1) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg(jsonObject.getStatus().getMsg());
            return response;
        }
        //验证成功 重置密码
//        String updatepwUrl = MyEnv.env.getProperty("updatepwUrl");
//        String param3 = "phone=" + phone
//                + "&password=" + newPassword;
//
//        String jsonUpurl = HttpRequest.httpPut(updatepwUrl, param3, "utf-8", null);
//
//        logger.info("-------jsonUp------------" + jsonUpurl);
//        JSONObject jsonUp = JSONObject.fromObject(jsonUpurl);

        ApiStatusResponse base=userTokenController.forgetPasswd(phone,newPassword);


        response.getStatus().setCode(base.getStatus().getCode());
        response.getStatus().setMsg(base.getStatus().getMsg());
        return response;
    }


    @RequestMapping(value = "/send_sms", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获得验证码")
    ApiStatusResponse createCash(
            @ApiParam(value = "phone") @RequestParam String phone
    ) {

        ApiStatusResponse response = new ApiStatusResponse();

//        String url = MyEnv.env.getProperty("sendsmsUrl");
//        String param = "phone=" + phone;
//        String res = HttpRequest.sendPost(url, param);
//        JSONObject jsonObject = JSONObject.fromObject(res);
        CheckPhoneCodeResponse jsonObject =smsController.sendRegisterSms(phone);
//        String code = JSONObject.fromObject(jsonObject.get("status")).get("code").toString();
        response.setStatus(jsonObject.getStatus());
        return response;
    }


    @RequestMapping(value = "/phone_register", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "手机号注册&激活，校验验证码")
    @Transactional
    ApiStatusResponse phoneRegister(
            @ApiParam(value = "订单id") @RequestParam Integer orderId,
            @ApiParam(value = "unionid") @RequestParam String unionid,
            @ApiParam(value = "phone") @RequestParam String phone,
            @ApiParam(value = "password") @RequestParam String password,
            @ApiParam(value = "短信验证码") @RequestParam String code
    ) {

        ApiStatusResponse response = new ApiStatusResponse();

        if (phone == null || phone.equals("")) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("请输入手机号！");
            return response;
        }

        if (!PhoneUtil.isPhone(phone).equals("")) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg(PhoneUtil.isPhone(phone));
            return response;
        }


        if (code == null || code.equals("")) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("请输入验证码！");
            return response;
        }

        if (password == null || password.equals("")) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("请输入密码！");
            return response;
        }

        //密码加密
        String pw = MD5Util.MD5Encode(password, "utf-8");


        PreSalePay order = preSalePayService.findPayById(orderId);
        if(order.getIsActive()==1){
            return response;
        }

//        String url = MyEnv.env.getProperty("checkCode");
//        String param = "phone=" + phone
//                + "&code=" + code;
//        String res = HttpRequest.sendGet(url, param);
//        JSONObject jsonObject = JSONObject.fromObject(res);
        CheckPhoneCodeResponse jsonObject =smsController.checkPhoneCode(phone,code);


//        String resCode = JSONObject.fromObject(jsonObject.get("status")).get("code").toString();
        logger.info("-=========================" + jsonObject);
        if (jsonObject.getStatus().getCode() != 1) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg(jsonObject.getStatus().getMsg());
            return response;
        }
        //验证成功注册
//        String phoneregisterUrl = MyEnv.env.getProperty("phoneregisterUrl");
//        String param2 = "phone=" + phone
//                + "&password=" + pw
//                + "&device=" + "故事飞船网页"
//                + "&deviceName=" + "故事飞船网页"
//                + "&deviceUnique=" + "故事飞船网页" + phone;
//        String phoneRegister = HttpRequest.sendPost(phoneregisterUrl, param2);
//
//
//
//        logger.info("--------phoneRegister----" + phoneRegister);
//        JSONObject json = JSONObject.fromObject(phoneRegister);
        String device="故事飞船网页";
        String deviceName="故事飞船网页";
        String deviceUnique="故事飞船网页" + phone;
        PhoneRegisterResponse json =userTokenController.phoneRegister(phone,device,pw,deviceName,deviceUnique);

//        String registCode = JSONObject.fromObject(json.get("status")).get("code").toString();
        if (json.getStatus().getCode()!=1) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg(json.getStatus().getMsg());
            return response;
        }
        String token = json.getUserToken().getToken();

        //激活兑换码 兑换码
        order = preSalePayService.findPayById(orderId);
        if (order == null) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("订单不存在！");
            return response;
        }
        PreSaleUser user = userService.getUserByUnionid(unionid);
        if (order.getUserId() != user.getId().intValue()) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("非此用户订单");
            return response;
        }

        String subscribeByCodeUrl = env.getProperty("subscribeByCodeUrl");

        Integer ageType = null;
        int goodsId = order.getGoodsId();
        if(goodsId ==42){
            ageType=1;
        }else if(goodsId ==43){
            ageType=2;
        }

//        String param3 = "token=" + token
//                + "&code=" + order.getCode()
//                +"&ageType="+ageType;
//        String subscribeByCode = HttpRequest.sendPost(subscribeByCodeUrl, param3);
//        logger.info("--------subscribeByCode----" + subscribeByCode);
//        JSONObject json2 = JSONObject.fromObject(subscribeByCode);

        SubscribeByCodeResponse json2=vipCodeController.subscribeByCode211(token,token,order.getCode(),null,null,null,null);

//        String ccode = JSONObject.fromObject(json2.get("status")).get("code").toString();
        if (json2.getStatus().getCode() != 1) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg(json2.getStatus().getMsg());
            return response;
        }
        //激活类型
//        order.setSign(phone);
//        order.setType(1);
//        order.setVipTime(DateUtil.addOneYear());
//        preSalePayService.update(order);
        preSalePayService.updateActiveDate(order,phone,1, DateUtil.addOneYear());
        return response;
    }

    @RequestMapping(value = "/phone_login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "手机号登录&激活")
    @Transactional
    ApiStatusResponse phoneLogin(
            @ApiParam(value = "订单id") @RequestParam Integer orderId,
            @ApiParam(value = "unionid") @RequestParam String unionid,
            @ApiParam(value = "phone") @RequestParam String phone,
            @ApiParam(value = "password") @RequestParam String password
    ) {

        ApiStatusResponse response = new ApiStatusResponse();

        if (phone == null || phone.equals("")) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("请输入手机号！");
            return response;
        }

        if (!PhoneUtil.isPhone(phone).equals("")) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg(PhoneUtil.isPhone(phone));
            return response;
        }

        if (password == null || password.equals("")) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("请输入密码！");
            return response;
        }

        //密码加密
        String pw = MD5Util.MD5Encode(password, "utf-8");

        PreSalePay order = preSalePayService.findPayById(orderId);
        if(order.getIsActive()==1){
            return response;
        }
        //手机登录
//        String phoneloginUrl = MyEnv.env.getProperty("phoneloginUrl");
//        String param = "phone=" + phone
//                + "&password=" + pw
//                + "&device=" + "故事飞船网页"
//                + "&deviceName=" + "故事飞船网页"
//                + "&deviceUnique=" + "故事飞船网页" + phone;
//        String phoneLogin = HttpRequest.sendPost(phoneloginUrl, param);
//        JSONObject json = JSONObject.fromObject(phoneLogin);

        PhoneLoginResponse json=userTokenController.phoneLogin(phone,"故事飞船网页",pw,"故事飞船网页","故事飞船网页" + phone,"");

//        logger.info("--------phoneLogin----" + phoneLogin);
        String token;
        if (json.getUserToken()!= null) {
            token = json.getUserToken().getToken();
            //激活兑换码 兑换码
            order = preSalePayService.findPayById(orderId);
            if (order == null) {
                response.getStatus().setCode(0);
                response.getStatus().setMsg("订单不存在！");
                return response;
            }
            PreSaleUser user = userService.getUserByUnionid(unionid);
            if (order.getUserId() != user.getId().intValue()) {
                response.getStatus().setCode(0);
                response.getStatus().setMsg("非此用户订单");
                return response;
            }

            String subscribeByCodeUrl = env.getProperty("subscribeByCodeUrl");
            Integer ageType = null;
            int goodsId = order.getGoodsId();
            if(goodsId ==42){
                ageType=1;
            }else if(goodsId ==43){
                ageType=2;
            }

//            String param3 = "token=" + token
//                    + "&code=" + order.getCode()
//                    +"&ageType="+ageType;
//            String subscribeByCode = HttpRequest.sendPost(subscribeByCodeUrl, param3);
//            logger.info("--------subscribeByCode----" + subscribeByCode);
//            JSONObject json2 = JSONObject.fromObject(subscribeByCode);

            SubscribeByCodeResponse json2=vipCodeController.subscribeByCode211(token,token,order.getCode(),null,null,null,null);

            if (json2.getStatus().getCode() != 1) {
                response.getStatus().setCode(0);
                response.getStatus().setMsg(json2.getStatus().getMsg());
                return response;
            }
            //激活类型
//            order.setSign(phone);
//            order.setType(1);
//            order.setVipTime(DateUtil.addOneYear());
//            preSalePayService.update(order);
            preSalePayService.updateActiveDate(order,phone,1,DateUtil.addOneYear());
            return response;
        }
        response.getStatus().setCode(0);
        response.getStatus().setMsg(json.getStatus().getMsg());
        return response;
    }

    @RequestMapping(value = "/wx_login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信注册&激活")
    @Transactional
    ApiStatusResponse wxLogin(
            @ApiParam(value = "订单id") @RequestParam Integer orderId,
            @ApiParam(value = "unionid") @RequestParam String unionid
    ) {
        ApiStatusResponse response = new ApiStatusResponse();

        PreSaleUser user = userService.getUserByUnionid(unionid);

//        String otherloginUrl = MyEnv.env.getProperty("otherloginUrl");
//        String param = "srcId=" + unionid
//                + "&device=" + "故事飞船网页"
//                + "&deviceName=" + "故事飞船网页"
//                + "&deviceUnique=" + "故事飞船网页" + unionid;
//        logger.info("loginUrl----" + otherloginUrl);
//        String login = HttpRequest.sendPost(otherloginUrl, param);
//        logger.info("--------login----" + login);
//        JSONObject json = JSONObject.fromObject(login);
        OtherLoginResponse json=userTokenController.otherLogin(unionid,"故事飞船网页","故事飞船网页","故事飞船网页"+unionid,null);
        String token;
        if (json.getUserToken() != null ) {
            token = json.getUserToken().getToken();
            logger.info("-------token------------" + token);
        } else {
//            String otherregisterUrl = MyEnv.env.getProperty("otherregisterUrl");
//            String param2 = "srcId=" + unionid
//                    + "&type=" + 1
//                    + "&device=" + "故事飞船网页"
//                    + "&deviceName=" + "故事飞船网页"
//                    + "&deviceUnique=" + "故事飞船网页" + unionid
//                    + "&avatar=" + user.getIcon()
//                    + "&nick=" + user.getNick();
//            String register = HttpRequest.sendPost(otherregisterUrl, param2);
//            JSONObject json2 = JSONObject.fromObject(register);

            OtherRegisterResponse json2=userTokenController.otherRegister(unionid,"",1,"故事飞船网页","故事飞船网页","故事飞船网页"+unionid,user.getIcon(),user.getNick(),0,"");
            token = json2.getUserToken().getToken();
            logger.info("-------token------------" + token);
        }

        //激活兑换码 兑换码
        PreSalePay order = preSalePayService.findPayById(orderId);
        if (order == null) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("订单不存在！");
            return response;
        }
        if (order.getUserId() != user.getId().intValue()) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("非此用户订单");
            return response;
        }
        String subscribeByCodeUrl = env.getProperty("subscribeByCodeUrl");

        Integer ageType = null;
        int goodsId = order.getGoodsId();
        if(goodsId ==42){
            ageType=1;
        }else if(goodsId ==43){
            ageType=2;
        }

//        String param3 = "token=" + token
//                + "&code=" + order.getCode()
//                +"&ageType="+ageType;
//        String subscribeByCode = HttpRequest.sendPost(subscribeByCodeUrl, param3);
//        logger.info("--------subscribeByCode----" + subscribeByCode);
//        JSONObject json3 = JSONObject.fromObject(subscribeByCode);
        SubscribeByCodeResponse json3=vipCodeController.subscribeByCode211(token,token,order.getCode(),null,null,null,null);
//        String ccode = JSONObject.fromObject(json3.get("status")).get("code").toString();
        if (json3.getStatus().getCode() != 1) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg(json3.getStatus().getMsg());
            return response;
        }
//        order.setSign(user.getNick());
//        order.setType(2);
//        order.setVipTime(DateUtil.addOneYear());
        preSalePayService.updateActiveDate(order,user.getNick(),2,DateUtil.addOneYear());
        return response;
    }

}
