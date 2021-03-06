package com.ifenghui.storybookapi.app.user.controller;

import com.alipay.api.AlipayApiException;
import com.ifenghui.storybookapi.api.response.base.ApiStatus;
import com.ifenghui.storybookapi.api.response.base.ApiStatusResponse;
import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.social.service.VipFriendCardService;
import com.ifenghui.storybookapi.app.studyplan.controller.StudyPlanController;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponGetRecord;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeUserService;
import com.ifenghui.storybookapi.app.user.entity.*;
import com.ifenghui.storybookapi.app.user.response.*;
import com.ifenghui.storybookapi.app.user.service.*;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.app.transaction.service.BuyMagazineRecordService;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.wallet.service.UserStarRecordService;
import com.ifenghui.storybookapi.app.transaction.service.CouponService;
import com.ifenghui.storybookapi.style.UserAccountStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import com.ifenghui.storybookapi.util.VersionUtil;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/userToken")
@Api(value = "??????", description = "??????Token????????????")
public class UserTokenController {

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    UserService userService;

    @Autowired
    CouponService couponSerivce;

    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;

    @Autowired
    ConfigService configService;

    @Autowired
    UserStarRecordService userStarRecordService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    BuyMagazineRecordService buyMagazineRecordService;

    @Autowired
    UserUmengTokenService userUmengTokenService;

    @Autowired
    WalletService walletService;

    @Autowired
    CouponService couponService;

    @Autowired
    VipFriendCardService vipFriendCardService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UserRelateService userRelateService;

    @Autowired
    WeekPlanJoinService weekPlanJoinService;

    private static Logger logger = Logger.getLogger(UserTokenController.class);

    @RequestMapping(value = "/get_user_phone", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????????????????????user")
    UserTokenResponse getUserByPhone(
            @ApiParam(value = "phone") @RequestParam String phone
    ) throws ApiException {

        UserTokenResponse response = new UserTokenResponse();

        User user = userService.getUserByPhone(phone);
        if (user != null) {
            List<UserToken> list = userTokenService.getValidUserTokenListByUserId(user.getId());
            if (list.size() > 0) {
                response.setUserToken(list.get(0));
            }
        }
        return response;
    }


    @RequestMapping(value = "/check_user_token", method = RequestMethod.GET)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 1, message = "??????", response = ExceptionResponse.class)})
    @ApiOperation(value = "????????????Token??????", notes = "???")
    CheckUserTokenResponse checkValidUserToken(
            @ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiException {
        CheckUserTokenResponse response = new CheckUserTokenResponse();
        if (userTokenService.checkValidUserToken(token)) {
            response.setIsValid(1);
        } else {
            response.setIsValid(0);
        }
        return response;
    }

    @RequestMapping(value = "/user_valid_token_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 1, message = "??????", response = ExceptionResponse.class)})
    @ApiOperation(value = "??????????????????Token??????", notes = "???")
    GetValidUserTokenListByUserIdResponse getValidUserTokenListByUserId(
            @ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiException {
        GetValidUserTokenListByUserIdResponse response = new GetValidUserTokenListByUserIdResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        List<UserToken> userTokenList = userTokenService.getValidUserTokenListByUserId(userId);
        response.setUserTokenList(userTokenList);
        return response;
    }

    /**
     * V1.6.3 ????????????
     *
     * @param phone
     * @param device
     * @param password
     * @param deviceName
     * @param deviceUnique
     * @return
     * @throws ApiNotFoundException
     */
    @RequestMapping(value = "/phoneLogin", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 3, message = "???????????????????????????????????????", response = ExceptionResponse.class),
            @ApiResponse(code = 4, message = "????????????", response = ExceptionResponse.class),
            @ApiResponse(code = 1, message = "??????", response = ExceptionResponse.class),
            @ApiResponse(code = 214, message = "??????????????????????????????", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "???????????????V1.6.3???????????????", notes = "???????????????V1.6.3???????????????")
    public PhoneLoginResponse phoneLogin(
            @ApiParam(value = "?????????") @RequestParam String phone,
            @ApiParam(value = "????????????") @RequestParam String device,
            @ApiParam(value = "??????") @RequestParam String password,
            @ApiParam(value = "????????????") @RequestParam String deviceName,
            @ApiParam(value = "??????????????????") @RequestParam String deviceUnique,
            @ApiParam(value = "??????token") @RequestParam(required = false, defaultValue = "") String umengToken
    ) throws ApiNotFoundException {
        PhoneLoginResponse response = new PhoneLoginResponse();
        User user = userService.getUserByPhone(phone);
        if (user == null) {
            throw new ApiNotFoundException("???????????????????????????????????????", 3);
        }
        if (!user.getPassword().equals(password)) {
            throw new ApiNotFoundException("????????????", 4);
        }
        String userAgent = request.getHeader("User-Agent");
        UserToken userToken = userTokenService.addOrEditUserToken(device, deviceUnique, userAgent, user, deviceName);

        if (umengToken != null && !"".equals(umengToken)) {
            try {
                userUmengTokenService.updateUserUmengToken(user.getId().intValue(), umengToken, userAgent, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.setUserToken(userToken);
        return response;
    }

    /**
     * ???????????????V1.6.3????????????
     *
     * @param srcId
     * @param device
     * @param deviceName
     * @param deviceUnique
     * @return
     * @throws ApiNotFoundException
     */
    @RequestMapping(value = "/otherLogin", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 5, message = "????????????????????????", response = ExceptionResponse.class),
            @ApiResponse(code = 1, message = "??????", response = ExceptionResponse.class),
            @ApiResponse(code = 214, message = "??????????????????????????????", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "??????????????????V1.6.3???????????????", notes = "??????????????????V1.6.3???????????????")
    public OtherLoginResponse otherLogin(
            @ApiParam(value = "???????????????") @RequestParam String srcId,
            @ApiParam(value = "????????????") @RequestParam String device,
            @ApiParam(value = "????????????") @RequestParam String deviceName,
            @ApiParam(value = "??????????????????") @RequestParam String deviceUnique,
            @ApiParam(value = "??????token") @RequestParam(required = false, defaultValue = "") String umengToken
    ) throws ApiNotFoundException {
        OtherLoginResponse response = new OtherLoginResponse();

        UserAccount userAccount = userService.getUserAccountBySrcId(srcId);
        if (userAccount == null) {
            throw new ApiNotFoundException("????????????????????????", 5);
        }
        String userAgent = request.getHeader("User-Agent");
        UserToken userToken = userTokenService.addOrEditUserToken(device, deviceUnique, userAgent, userAccount.getUser(), deviceName);
        if (umengToken != null && !"".equals(umengToken)) {
            try {
                userUmengTokenService.updateUserUmengToken(userToken.getUserId().intValue(), umengToken, userAgent, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.setUserToken(userToken);
        return response;
    }

    /**
     * V1.6.3 ????????????????????????
     *
     * @param phone
     * @param device
     * @param password
     * @param deviceName
     * @param deviceUnique
     * @return
     * @throws ApiNotFoundException
     */
    @RequestMapping(value = "/phoneRegister", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 2, message = "?????????", response = ExceptionResponse.class),
            @ApiResponse(code = 1, message = "??????", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "???????????? ???V1.6.3 ???????????????", notes = "???????????? ???V1.6.3 ???????????????")
    public PhoneRegisterResponse phoneRegister(
            @ApiParam(value = "?????????") @RequestParam String phone,
            @ApiParam(value = "????????????") @RequestParam String device,
            @ApiParam(value = "??????") @RequestParam String password,
            @ApiParam(value = "????????????") @RequestParam String deviceName,
            @ApiParam(value = "??????????????????") @RequestParam String deviceUnique
    ) throws ApiNotFoundException {
        PhoneRegisterResponse phoneRegisterResponse = new PhoneRegisterResponse();
        String userAgent = request.getHeader("User-Agent");
        String ver = VersionUtil.getVerionInfo(request);
        String channel = VersionUtil.getChannelInfo(request);

        User newUser = userService.phoneRegisterUser(phone, password, ver, channel);

        phoneRegisterResponse.setIsHasFriendCard(vipFriendCardService.createUserGiveStoryFromVipFriendCard(phone, newUser.getId()));

        if (ver.compareTo("2.0.0") >= 0) {
            couponStoryExchangeUserService.sendRegisterUserStoryCoupon(newUser.getId().intValue(), phone);
            /**
             * ??????????????????????????????
             */
            couponSerivce.registerSendCoupon(newUser.getId(), phone);
        } else {
            couponSerivce.getCouponByRegsiter(newUser.getId(), phone);
        }


        /**????????????????????????*/
        logger.info("addddOne======================/**????????????????????????*/");
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.TWO_FOUR);
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.FOUR_SIX);


        /**
         * ????????????????????????
         */
//        userStarRecordService.addRegisterUserStarRecord(newUser.getId());
        walletService.addRegisterUserStarRecord(newUser.getId().intValue());


        /**
         * ???????????????????????????????????????
         */
//        if(ver.compareTo("1.7.0") >= 0) {
//            buyMagazineRecordService.giveNewUserNowMagazineAndStory(newUser.getId());
//        }

        /**
         * ?????? token
         */
        UserToken userToken = userTokenService.addOrEditUserToken(device, deviceUnique, userAgent, newUser, deviceName);
        phoneRegisterResponse.getStatus().setMsg("????????????");
        phoneRegisterResponse.setUserToken(userToken);

        /**
         * ????????????????????????????????????,????????????????????????????????????(???????????????????????????????????????????????????,??????usercontroller??????????????????????????????)
         */
        UserRelate userRelate = userRelateService.getUserRelateByPhone(phone);
        //?????????????????????
        Integer userId = 0;
        if (userRelate != null){
            User user = userService.getUserByPhone(phone);
            userId = user.getId().intValue();

            weekPlanJoinService.addOneWeekPlanByUserId(userId, WeekPlanStyle.TWO_FOUR);
            weekPlanJoinService.addOneWeekPlanByUserId(userId, WeekPlanStyle.FOUR_SIX);
        }

        return phoneRegisterResponse;

    }

    /**
     * ????????????
     *
     * @return
     * @throws ApiNotFoundException
     */
    @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 1, message = "??????", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "?????????????????????", notes = "?????????????????????")
    PhoneRegisterResponse removeUser(
            @ApiParam(value = "userId") @RequestParam Integer userId,
            @ApiParam(value = "???????????????") @RequestParam String systemKey
    ) throws ApiNotFoundException {
        PhoneRegisterResponse phoneRegisterResponse = new PhoneRegisterResponse();
        if ("vista688".equals(systemKey)) {
            userService.removeUser(userId);
        } else {
            throw new ApiNotFoundException("???????????????");
        }
        return phoneRegisterResponse;

    }

    /**
     * V1.6.3 ????????????
     *
     * @param srcId
     * @param phone
     * @param type
     * @param device
     * @param deviceName
     * @param deviceUnique
     * @param avatar
     * @param nick
     * @param sex
     * @param birthday
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/otherRegister", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 2, message = "?????????", response = ExceptionResponse.class),
            @ApiResponse(code = 1, message = "??????", response = ExceptionResponse.class),
            @ApiResponse(code = 3, message = "???????????????", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "??????????????????V1.6.3 ???????????????", notes = "??????????????????V1.6.3 ???????????????")
    public OtherRegisterResponse otherRegister(
            @ApiParam(value = "???????????????") @RequestParam String srcId,
            @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String phone,
            @ApiParam(value = "??????????????????????????????1???qq2?????????3?????????4?????????5????????????6)") @RequestParam Integer type,
            @ApiParam(value = "????????????") @RequestParam String device,
            @ApiParam(value = "????????????") @RequestParam String deviceName,
            @ApiParam(value = "??????????????????") @RequestParam String deviceUnique,
            @ApiParam(value = "???????????????(??????)", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String avatar,
            @ApiParam(value = "???????????????(??????)", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String nick,
            @ApiParam(value = "??????????????????1???0??????(??????)", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Integer sex,
            @ApiParam(value = "???????????????(??????)", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String birthday
    ) throws ApiException {
        OtherRegisterResponse otherRegisterResponse = new OtherRegisterResponse();
        UserAccountStyle userAccountStyle=UserAccountStyle.getById(type);
        userService.checkUserPhoneCondition(userAccountStyle, phone, srcId);
        String ver = VersionUtil.getVerionInfo(request);
        String channel = VersionUtil.getChannelInfo(request);
        String userAgent = request.getHeader("User-Agent");

        User newUser = userService.otherRegisterUser(ver, channel, userAccountStyle, phone, nick, sex, birthday, avatar);
        otherRegisterResponse.setIsHasFriendCard(vipFriendCardService.createUserGiveStoryFromVipFriendCard(srcId, newUser.getId()));
        CouponGetRecord couponGetRecord = couponService.getCouponGetRecord(srcId);
        if (couponGetRecord != null) {
            userExtendService.editParentId(newUser.getId().intValue(), couponGetRecord.getUserId().intValue());
        } else {
            userExtendService.createUserExtend(newUser.getId().intValue(), 0);
        }

        /**
         * ???????????????????????????
         */
        userService.addUserAccountByUser(newUser, srcId, UserAccountStyle.getById(type));

        /**
         * ???????????????
         * type = 4 ??????????????????????????????
         */
        if (ver.compareTo("2.0.0") >= 0) {
            couponStoryExchangeUserService.sendRegisterUserStoryCoupon(newUser.getId().intValue(), phone);
            couponSerivce.registerSendCoupon(newUser.getId(), phone);
        } else {
            if (userAccountStyle != UserAccountStyle.GUEST) {
                couponSerivce.getCouponByRegsiter(newUser.getId(), phone);
            }
        }

        /**????????????????????????*/
        logger.info("addddOne======================/**????????????????????????*/");
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.TWO_FOUR);
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.FOUR_SIX);


        /**
         * ????????????????????????
         */
//        userStarRecordService.addRegisterUserStarRecord(newUser.getId());
        walletService.addRegisterUserStarRecord(newUser.getId().intValue());
        /**
         * ???????????????????????????????????????
         * V.1.7.0 ????????????
         * ?????????
         */
//        if(ver.compareTo("1.7.0") >= 0) {
//            buyMagazineRecordService.giveNewUserNowMagazineAndStory(newUser.getId());
//        }
        /**
         * ?????? token
         */
        UserToken userToken = userTokenService.addOrEditUserToken(device, deviceUnique, userAgent, newUser, deviceName);
        otherRegisterResponse.setUserToken(userToken);


        /**
         * ????????????????????????????????????,????????????????????????????????????(???????????????????????????????????????????????????,??????usercontroller??????????????????????????????)
         */
        UserRelate userRelate = userRelateService.getUserRelateByUnionid(srcId);
        //?????????????????????
        Integer userId = 0;
        if (userRelate != null){
            UserAccount userAccount = userAccountService.getUserAccountBySrcId(srcId);
            userId = userAccount.getUserId().intValue();

            weekPlanJoinService.addOneWeekPlanByUserId(userId, WeekPlanStyle.TWO_FOUR);
            weekPlanJoinService.addOneWeekPlanByUserId(userId, WeekPlanStyle.FOUR_SIX);
        }

        return otherRegisterResponse;

    }

    @RequestMapping(value = "/forgetPasswd", method = RequestMethod.PUT)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 8, message = "?????????????????????????????????", response = ExceptionResponse.class),
            @ApiResponse(code = 1, message = "??????", response = ExceptionResponse.class)})
    @ApiOperation(value = "????????????????????????V1.6.3???????????????", notes = "????????????????????????V1.6.3???????????????")
    public ApiStatusResponse forgetPasswd(
            @ApiParam(value = "???????????????") @RequestParam String phone,
            @ApiParam(value = "?????????") @RequestParam String password
    ) throws ApiNotFoundException {

        User user = userService.getUserByPhone(phone);
        if (user == null) {
            throw new ApiNotFoundException("?????????????????????????????????", 8);
        }
        user.setPassword(password);
        userService.updateUser(user);
        List<UserToken> userTokenList = userTokenService.getValidUserTokenListByUserId(user.getId());
        for (UserToken userToken : userTokenList) {
            userTokenService.setUnValidUserToken(userToken.getId(), user.getId());
        }
        ApiStatusResponse response = new ApiStatusResponse();
        response.getStatus().setMsg("????????????");
        return response;
    }

    @RequestMapping(value = "/failure_user_token", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "??????token?????????V1.6.3 ???????????????", notes = "??????token?????????V1.6.3 ???????????????")
    ApiStatusResponse setUnValidUserToken(
            @ApiParam(value = "tokenId") @RequestParam long tokenId,
            @ApiParam(value = "??????token") @RequestParam String token
    ) {
        ApiStatusResponse response = new ApiStatusResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        userTokenService.setUnValidUserToken(tokenId, userId);
        return response;

    }

    @RequestMapping(value = "/auto_failure_user_token", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????token?????????V1.6.3 ???????????????", notes = "????????????token?????????V1.6.3 ???????????????")
    ApiStatusResponse setUnValidUserToken(
            @ApiParam(value = "??????id") @RequestParam long userId
    ) {
        ApiStatusResponse response = new ApiStatusResponse();
        List<UserToken> userTokenList = userTokenService.getValidUserTokenListByUserId(userId);
        UserExtend userExtend = userExtendService.findUserExtendByUserId(userId);
        Integer deviceLimitNum = 3;
        if (userExtend != null && userExtend.getDeviceLimitNum() > deviceLimitNum) {
            deviceLimitNum = userExtend.getDeviceLimitNum();
        }
        if (userTokenList.size() == deviceLimitNum) {
            userTokenService.setUnValidUserToken(userTokenList.get(2).getId(), userId);
        }
        return response;
    }


    @RequestMapping(value = "/testGetIpAddr", method = RequestMethod.GET)
    @ResponseBody
    ApiStatusResponse testGetIpAddr() {
        ApiStatusResponse response = new ApiStatusResponse();
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null) {
            throw new ApiNotFoundException("x-forwarded-for:" + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip != null) {
            throw new ApiNotFoundException("Proxy-Client-IP:" + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip != null) {
            throw new ApiNotFoundException("WL-Proxy-Client-IP:" + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null) {
            throw new ApiNotFoundException("getRemoteAddr:" + ip);
        }

        return response;
    }

    @RequestMapping(value = "/alipay_auth_grant_str", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    AlipayAuthGrantResponse alipayAuthGrant() throws java.io.UnsupportedEncodingException {
        AlipayAuthGrantResponse response = new AlipayAuthGrantResponse();
        response.setStrInfo(userTokenService.getAlipayUserAuthGrantString());
        return response;
    }

    @RequestMapping(value = "alipay_get_user_info", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "?????????????????????????????????")
    AlipayAuthCodeGetUserInfoResponse alipayAuthCodeGetUserInfo(
            @ApiParam(value = "auth_code") @RequestParam String authCode,
            @ApiParam(value = "user_id") @RequestParam String userId
    ) throws AlipayApiException {
        return userTokenService.getAlipayUserInfoFromAuthCode(authCode, userId);
    }


    @RequestMapping(value = "send_subscription_user_vip", method = RequestMethod.GET)
    @ApiOperation(value = "??????????????????")
    BaseResponse setUserSvipToSubscriptionUser() throws ParseException {
        userTokenService.setUserSvipToSubscriptionUser();
        return null;
    }
}
