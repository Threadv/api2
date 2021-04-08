package com.ifenghui.storybookapi.app.user.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.ifenghui.storybookapi.app.system.service.GeoipService;
import com.ifenghui.storybookapi.app.transaction.service.PaySubscriptionPriceService;
import com.ifenghui.storybookapi.app.transaction.service.UserSvipService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.response.AlipayAuthCodeGetUserInfoResponse;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.controller.PayController;
import com.ifenghui.storybookapi.config.ExceptionCodeConfig;
import com.ifenghui.storybookapi.app.user.dao.UserTokenDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import com.ifenghui.storybookapi.exception.*;
import com.ifenghui.storybookapi.app.user.service.UserTokenService;
import com.ifenghui.storybookapi.style.SvipStyle;
import com.ifenghui.storybookapi.util.GetAddrByIpInfo;
import com.ifenghui.storybookapi.util.HttpRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;

import static com.ifenghui.storybookapi.util.MD5Util.getMD5;

@Component
public class UserTokenServiceImpl implements UserTokenService {

    private static AlipayClient alipayClient;

    @Autowired
    private Environment env;

    @Autowired
    UserTokenDao userTokenDao;

    @Autowired
    UserDao userDao;

    @Autowired
    HttpServletRequest request;

    @Autowired
    UserService userService;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    UserSvipService userSvipService;

    @Autowired
    PaySubscriptionPriceService paySubscriptionPriceService;

    @Autowired
    GeoipService geoipService;

    private static Logger logger = Logger.getLogger(PayController.class);

    @Override
    public boolean checkValidUserToken(String token) {
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken = userTokenDao.findOneByToken(token);
        if(userToken != null && userToken.getIsValid().equals(1)){
            userToken.setLastLoginTime(new Date());
            userTokenDao.save(userToken);
            return true;
        } else{
            return false;
        }
    }

    @Override
    public UserToken findOneByToken(String token){
        UserToken userToken = userTokenDao.findOneByToken(token);
        if(userToken != null) {
            userToken.setUser(userService.getUser(userToken.getUserId()));
        }
        return userToken;
    }

    @Override
    public Integer getUserIdByTokenAndCheck(String token) {
        UserToken userToken = userTokenDao.findOneByToken(token);
        if(userToken != null) {
           return userToken.getUserId().intValue();
        }
        return 0;
    }

    @Override
    public List<UserToken> getValidUserTokenListByUserId(long userId) {
        List<UserToken> userTokenList = userTokenDao.findValidUserTokensByUserId(userId,new Sort(Sort.Direction.DESC,"lastLoginTime"));
        for(UserToken item : userTokenList) {
            item.setUser(userService.getUser(item.getUserId()));
        }
        return userTokenList;
    }

    @Override
    public UserToken addOrEditUserToken(String device, String deviceUnique, String userAgent, User user, String deviceName) {
        UserToken userToken = userTokenDao.findUserTokenByUserIdAndDeviceUnique(user.getId(),deviceUnique);

        String ip = HttpRequest.getIpAddr(request);
        String address=geoipService.getCity(ip);
        if(address==null){
            address="";
        }
        UserExtend userExtend = userExtendService.findUserExtendByUserId(user.getId());
        Integer deviceLimitNum = 3;
        if(userExtend != null && userExtend.getDeviceLimitNum() > 3){
            deviceLimitNum = userExtend.getDeviceLimitNum();
        }

        if(userToken != null){
            List<UserToken> userTokenList = this.getValidUserTokenListByUserId(user.getId());
            boolean isTokenExist = false;
            if(userTokenList.size() >= deviceLimitNum){
                for(UserToken token : userTokenList){
                    if(token.getId().equals(userToken.getId())){
                        isTokenExist = true;
                    }
                }
                if(!isTokenExist){
                    User findUser=userService.getUser(userTokenList.get(0).getUserId());
                    if("".equals(findUser.getPhone())){
                        throw new ApiUserTokenBeyondLimitException(userTokenList.get(0), ExceptionCodeConfig.USER_TOKEN_LOGIN_NOT_BIND_PHONE_MSG, ExceptionStyle.NOT_BIND_PHONE);
                    } else {
                        throw new ApiUserTokenBeyondLimitException(userTokenList.get(0));
                    }
                }
            }

            userToken.setLastLoginTime(new Date());
            userToken.setIsValid(1);
            userToken.setDeviceName(deviceName);
            userToken.setUserAgent(userAgent);
            userToken.setDevice(device);
            userToken.setAddr(address);
            userToken = userTokenDao.save(userToken);
        } else {
            List<UserToken> userTokenList = this.getValidUserTokenListByUserId(user.getId());
            if(userTokenList.size() >= deviceLimitNum){
                User findUser=userService.getUser(userTokenList.get(0).getUserId());
                if("".equals(findUser.getPhone())){
                    throw new ApiUserTokenBeyondLimitException(userTokenList.get(0), ExceptionCodeConfig.USER_TOKEN_LOGIN_NOT_BIND_PHONE_MSG, ExceptionStyle.NOT_BIND_PHONE);
                } else {
                    throw new ApiUserTokenBeyondLimitException(userTokenList.get(0));
                }
            }
            String str = String.valueOf(user.getId())+String.valueOf(System.currentTimeMillis());
            String token = getMD5(str);
            String refreshToken = getMD5(token);
            userToken = new UserToken();
            userToken.setIsValid(1);
            userToken.setUserId(user.getId());
            userToken.setUserAgent(userAgent);
            userToken.setCreateTime(new Date());
            userToken.setDevice(device);
            userToken.setToken(token);
            userToken.setRefreshToken(refreshToken);
            userToken.setLastLoginTime(new Date());
            userToken.setDeviceName(deviceName);
            userToken.setDeviceUnique(deviceUnique);
            userToken.setAddr(address);
            userToken = userTokenDao.save(userToken);
        }
        userToken.setUser(user);
        return userToken;
    }

    @Override
    public UserToken setUnValidUserToken(long userTokenId, long userId) {
        UserToken userToken = userTokenDao.findOne(userTokenId);

        if(userToken == null){
            throw new ApiNotFoundException("未找到当前token！");
        }
        userToken.setUser(userService.getUser(userId));
        if(userToken.getUserId().equals(userId)){
            userToken.setIsValid(0);
            userToken = userTokenDao.save(userToken);
        } else {
            throw new ApiNoPermissionDelException("无修改token失效权限！");
        }
        return userToken;
    }

    @Override
    public UserToken oldAddUserToken(Integer userId, String userAgent, String device) {

        String str=String.valueOf(userId)+String.valueOf(System.currentTimeMillis());
        String token=getMD5(str);
        String refreshToken=getMD5(token);

        UserToken userToken=new UserToken();
        userToken.setUserId(userId.longValue());
        userToken.setCreateTime(new Date());
        userToken.setDevice(device);
        userToken.setUserAgent(userAgent);
        userToken.setToken(token);
        userToken.setRefreshToken(refreshToken);
        userToken.setIsValid(0);//0无效1有效
        userToken.setDeviceName("");
        userToken.setDeviceUnique("");
        userToken.setAddr("");
        userToken.setLastLoginTime(new Date());
        userToken = userTokenDao.save(userToken);
        return userToken;
    }

    @Override
    public String getAlipayUserAuthGrantString() throws java.io.UnsupportedEncodingException {
        String appId = "2017091108667845";
        String pid = env.getProperty("partner");
        Date now = new Date();
        String targetId = "storyship_" + now.getTime();
        String charset="utf-8";
        String private_key = env.getProperty("private_key");
        String sign_type="RSA";
        String originalGrantStr = "apiname=com.alipay.account.auth&app_id=" + appId +
                "&app_name=mc&auth_type=AUTHACCOUNT&biz_type=openservice&method=alipay.open.auth.sdk.code.get&pid=" + pid +
                "&product_id=APP_FAST_LOGIN&scope=kuaijie&sign_type=RSA&target_id=" + targetId;

        String sign = null;//签名
        try {
            sign = AlipaySignature.rsaSign(originalGrantStr,private_key,charset,sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(sign != null) {
            String urlEncodeSign = URLEncoder.encode(sign,charset);
            return originalGrantStr + "&sign=" + urlEncodeSign;
        } else {
            throw new ApiNotFoundException("服务器签名错误！");
        }
    }

    @Override
    public AlipayAuthCodeGetUserInfoResponse getAlipayUserInfoFromAuthCode(String authCode, String userId) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(authCode);
        AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
        logger.info(oauthTokenResponse);
        if(oauthTokenResponse.isSuccess()){
            if(!oauthTokenResponse.getUserId().equals(userId)){
                throw new ApiNotFoundException("用户验证信息有误！");
            }
           return this.getApipayUserInfoFromAccessToken(oauthTokenResponse.getAccessToken(),userId);
        } else {
            throw new ApiNotFoundException("获取支付宝授权信息失败！");
        }
    }

    @Override
    public AlipayAuthCodeGetUserInfoResponse getApipayUserInfoFromAccessToken(String accessToken, String userId) throws AlipayApiException{
        AlipayAuthCodeGetUserInfoResponse response = new AlipayAuthCodeGetUserInfoResponse();
        AlipayClient alipayClient =  this.getAlipayClient();
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse userInfoShareResponse = alipayClient.execute(request,accessToken);

        logger.info(userInfoShareResponse);
        logger.info(userInfoShareResponse.getNickName());
        logger.info(userId);

        if(userInfoShareResponse.isSuccess()){
            if(!userInfoShareResponse.getUserId().equals(userId)){
                throw new ApiNotFoundException("用户验证信息有误！");
            }
            if(userInfoShareResponse.getNickName() ==null){
                response.setAliUserInfo("未设置昵称");
            }else {
                response.setAliUserInfo(userInfoShareResponse.getNickName());
            }
            response.setAliUserId(userId);
            return response;
        } else {
            throw new ApiNotFoundException("获取支付宝授权信息失败！");
        }
    }

    @Override
    public AlipayClient getAlipayClient() {
        if(alipayClient == null){
            String app_id = "2017091108667845";
            String privateKey = env.getProperty("private_key");
            String publicKey = env.getProperty("alipay_public_key");
            alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",app_id, privateKey,"json","utf-8",publicKey,"RSA");
        }
        return alipayClient;
    }

    @Override
    public void setUserSvipToSubscriptionUser() throws ParseException {
        List<Long> userIds = paySubscriptionPriceService.getIsSubscriptionUserIds();
        for(Long item : userIds) {
            User user = userService.getUser(item);
            if(user == null) {
                continue;
            }
            Date endTime = paySubscriptionPriceService.getMaxEndTimeByUserId(item);
            if(endTime == null) {
                continue;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = simpleDateFormat.parse("2018-09-13 00:00:00");
            userSvipService.addUserSvip(item.intValue(), SvipStyle.LEVEL_THREE, startTime, endTime);
            user.setSvip(3);
            userDao.save(user);
        }
    }
}
