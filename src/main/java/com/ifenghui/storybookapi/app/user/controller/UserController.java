package com.ifenghui.storybookapi.app.user.controller;

import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.app.entity.Config;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.social.service.UserReadRecordService;
import com.ifenghui.storybookapi.app.story.service.MagazineService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanReadRecord;
import com.ifenghui.storybookapi.app.studyplan.service.StatisticLabelService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanReadRecordService;
import com.ifenghui.storybookapi.app.system.service.GeoipService;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.UserAbilityPlanRelate;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponGetRecord;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.PaySubscriptionOrderService;
import com.ifenghui.storybookapi.app.user.entity.*;
import com.ifenghui.storybookapi.app.user.response.*;
import com.ifenghui.storybookapi.app.user.service.*;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;

import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.transaction.entity.vip.UserSvip;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.config.StoryConfig;
import com.ifenghui.storybookapi.exception.*;
import com.ifenghui.storybookapi.app.wallet.service.UserStarRecordService;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.ReadRecordTypeStyle;
import com.ifenghui.storybookapi.style.UserAccountStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import com.ifenghui.storybookapi.util.VersionUtil;
import io.swagger.annotations.*;
import io.swagger.annotations.ApiResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ifenghui.storybookapi.util.MD5Util.getMD5;


@Controller
@EnableAutoConfiguration
@RequestMapping("/api/user")
@Api(value="??????",description = "??????????????????")
public class UserController {
//    @JsonBackReference
    @Autowired
    UserExtendService userExtendService;
    @Autowired
    UserService userService;
    @Autowired
    StoryService storyService;
    @Autowired
    OrderService orderService;
    @Autowired
    MagazineService magazineService;
    @Autowired
    WalletService walletService;
    @Autowired
    BuyMagazineRecordService buyMagazineRecordService;
    @Autowired
    CouponDeferredService couponDeferredService;
    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;
    @Autowired
    CouponService couponService;
    @Autowired
    ConfigService configService;

    @Autowired
    StatisticLabelService statisticLabelService;
    @Autowired
    UserSvipService userSvipService;
    @Autowired
    UserAbilityPlanRelateService userAbilityPlanRelateService;

    @Autowired
    UserReadRecordService userReadRecordService;

    @Autowired
    UserStarRecordService userStarRecordService;

    @Autowired
    UserTokenService userTokenService;
    @Autowired
    WeekPlanReadRecordService weekPlanReadRecordService;

    @Autowired
    PaySubscriptionOrderService paySubscriptionOrderService;

    @Autowired
    UserRelateService userRelateService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    WeekPlanJoinService weekPlanJoinService;

    @Autowired
    private Environment env;
    @Autowired
    GeoipService geoipService;



    @Autowired
    HttpServletRequest request;
    private static Logger logger = Logger.getLogger(UserController.class);
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????",notes = "????????????id ??????????????????")
    @ApiResponses({@ApiResponse(code=1,message="??????",response = GetUserResponse.class)
                ,@ApiResponse(code=201,message="????????????????????????",response = ExceptionResponse.class)})
    GetUserResponse getUser(@ApiParam(value = "??????token")@RequestParam String  token
    )  throws  ApiNotTokenException,ApiNotFoundException {
        int userId;

        userId = (int)userService.checkAndGetCurrentUserId(token);
        UserToken ut=userService.getUserIdByToken(token);


        GetUserResponse t=new GetUserResponse();

        //??????????????????
        Wallet wallet = walletService.getWalletByUserId(userId);
        User user=userService.getUser(ut.getUserId());
        user.setBalance(wallet.getBalance());
        ut.setUser(user);
        t.setUserToken(ut);
        if(ut==null){
            throw new ApiNotFoundException("????????????????????????");
        }


        return t;
    }

    @RequestMapping(value = "/getUserByToken",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????",notes = "????????????token ??????????????????")
    @ApiResponses({@ApiResponse(code=1,message="??????",response = GetUserByTokenResponse.class)
            ,@ApiResponse(code=201,message="????????????????????????",response = ExceptionResponse.class)})
    GetUserByTokenResponse getUserByToken(@ApiParam(value = "??????token")@RequestParam String  token
    )  throws  ApiNotTokenException,ApiNotFoundException {
        Long userId= null;

        userId = userService.checkAndGetCurrentUserId(token);
        User user = userService.getUser(userId);

        GetUserByTokenResponse response = new GetUserByTokenResponse();
        response.setUser(user);
        return response;
    }
    @RequestMapping(value = "/getUserByNick",method = RequestMethod.GET)
    @ResponseBody
    GetUsersResponse getUserByNick(@RequestParam String nick, @RequestParam int pageNo, @RequestParam int pageSize) {


        GetUsersResponse getUsersResponse =new GetUsersResponse();

        List<User> userList=this.userService.getUsersByNick(nick,pageNo,pageSize);
        getUsersResponse.setUsers(userList);


        return getUsersResponse;
    }


    @RequestMapping(value="/phoneRegister",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=2,message = "?????????",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "????????????", notes = "????????????")
    PhoneRegisterResponse phoneRegister(
            @ApiParam(value = "?????????")@RequestParam String phone,
            @ApiParam(value = "????????????")@RequestParam String device,
            @ApiParam(value = "??????")@RequestParam String password
    ) throws ApiNotFoundException {
        PhoneRegisterResponse phoneRegisterResponse=new PhoneRegisterResponse();
        String userAgent=request.getHeader("User-Agent");
        String ver = VersionUtil.getVerionInfo(request);
        String channel = VersionUtil.getChannelInfo(request);

        User newUser = userService.phoneRegisterUser(phone,password,ver,channel);

        couponService.getCouponByRegsiter(newUser.getId(),phone);

        /**
         * ????????????????????????
         */
        walletService.addRegisterUserStarRecord(newUser.getId().intValue());

        /**????????????????????????*/
        logger.info("addddOne======================/**????????????????????????*/");
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.TWO_FOUR);
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.FOUR_SIX);


        /**
         * ?????? token
         */
        UserToken userToken = userTokenService.oldAddUserToken(newUser.getId().intValue(),userAgent,device);
        phoneRegisterResponse.getStatus().setMsg("????????????");
        phoneRegisterResponse.setUserToken(userToken);

        /**
         * ????????????????????????????????????,????????????????????????????????????(???????????????????????????????????????????????????,??????usertokencontroller??????????????????????????????)
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


    @RequestMapping(value="/otherRegister",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=2,message = "?????????",response = ExceptionResponse.class),
           @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class),
           @ApiResponse(code=3,message = "???????????????",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "???????????????", notes = "???????????????")
    OtherRegisterResponse otherRegister(
            @ApiParam(value = "???????????????")@RequestParam String srcId,
            @ApiParam(value = "?????????",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  phone,
            @ApiParam(value = "??????????????????????????????1???qq2?????????3?????????4?????????5)")@RequestParam Integer type,
            @ApiParam(value = "????????????")@RequestParam String device,
            @ApiParam(value = "???????????????(??????)",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String avatar,
            @ApiParam(value = "???????????????(??????)",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String nick,
            @ApiParam(value = "??????????????????1???0??????(??????)",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") Integer sex,
            @ApiParam(value = "???????????????(??????)",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String birthday
    ) throws ApiException {

        //????????????
        UserAccountStyle userAccountStyle=UserAccountStyle.getById(type);

        OtherRegisterResponse otherRegisterResponse=new OtherRegisterResponse();
        userService.checkUserPhoneCondition(UserAccountStyle.getById(type),phone,srcId);
        String ver = VersionUtil.getVerionInfo(request);
        String channel = VersionUtil.getChannelInfo(request);
        String userAgent = request.getHeader("User-Agent");
        User newUser = userService.otherRegisterUser(ver,channel, UserAccountStyle.getById(type), phone, nick, sex, birthday, avatar);

        /**
         * ???????????????????????????
         */
        userService.addUserAccountByUser(newUser,srcId,UserAccountStyle.getById(type));

        /**????????????????????????*/
        logger.info("addddOne======================/**????????????????????????*/");
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.TWO_FOUR);
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.FOUR_SIX);


        /**
         * ???????????????
         * type = 4 ??????????????????????????????
         */
        if(userAccountStyle != UserAccountStyle.GUEST){
            couponService.getCouponByRegsiter(newUser.getId(),phone);
        }
        /**
         * ????????????????????????
         */
        walletService.addRegisterUserStarRecord(newUser.getId().intValue());

        /**
         * ?????? token
         */
        UserToken userToken = userTokenService.oldAddUserToken(newUser.getId().intValue(),userAgent,device);
        otherRegisterResponse.setUserToken(userToken);

        /**
         * ????????????????????????????????????,????????????????????????????????????(???????????????????????????????????????????????????,??????usertokencontroller??????????????????????????????)
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


    @RequestMapping(value="/phoneLogin",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=3,message = "???????????????????????????????????????",response = ExceptionResponse.class),
            @ApiResponse(code=4,message = "????????????",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "????????????", notes = "????????????")
    PhoneLoginResponse phoneLogin (
            @ApiParam(value = "?????????")@RequestParam String phone,
            @ApiParam(value = "????????????")@RequestParam String device,
            @ApiParam(value = "??????")@RequestParam String password
    ) throws ApiNotFoundException {
        PhoneLoginResponse phoneLoginResponse=new PhoneLoginResponse();
        String userAgent = request.getHeader("User-Agent");

        User user = userService.getUserByPhone(phone);
        if(user==null){
            throw new ApiNotFoundException("???????????????????????????????????????",3);
        }
        if(!user.getPassword().equals(password)){
            throw new ApiNotFoundException("????????????",4);
        }

        UserToken userToken = userTokenService.oldAddUserToken(user.getId().intValue(),userAgent,device);
        phoneLoginResponse.setUserToken(userToken);
        return phoneLoginResponse;

    }

    @RequestMapping(value="/otherLogin",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=5,message = "????????????????????????",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "???????????????", notes = "???????????????")
    OtherLoginResponse otherLogin (
            @ApiParam(value = "???????????????")@RequestParam String srcId,
            @ApiParam(value = "????????????")@RequestParam String device
    ) throws ApiNotFoundException {
        OtherLoginResponse otherLoginResponse=new OtherLoginResponse();
        String userAgent=request.getHeader("User-Agent");

        UserAccount userAccount = userService.getUserAccountBySrcId(srcId);
        if(userAccount==null){
            throw new ApiNotFoundException("????????????????????????",5);
        }

        UserToken userToken = userTokenService.oldAddUserToken(userAccount.getUserId().intValue(),userAgent,device);
        otherLoginResponse.setUserToken(userToken);
        return otherLoginResponse;
    }

    @RequestMapping(value="/finishUser",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    FinishUserResponse finishUser (@ApiParam(value = "??????token")@RequestParam String  token,
                                  @ApiParam( value = "??????")@RequestParam String nick,
                                  @ApiParam(value = "??????,0??????1???")@RequestParam Integer  sex,
                                  @ApiParam(value = "??????",required = false)@RequestParam(required = false) String addr,
                                  @ApiParam(value = "??????")@RequestParam String birthday,
                                  @ApiParam(value = "????????????",required = false)@RequestParam(required = false) String slogen,
                                  @ApiParam(value = "??????????????????",required = false)@RequestParam(required = false) String avatar
    ) throws ApiNotTokenException {
        Long userId= null;
        FinishUserResponse t=new FinishUserResponse();
        userId = userService.checkAndGetCurrentUserId(token);

        User user=userService.getUser(userId);
        if(nick!=null && user.getNick()!=nick){
            user.setNick(nick);
            t.getStatus().setMsg("??????????????????");
        }
        if(addr!=null && user.getAddr()!=addr){
            user.setAddr(addr);
            t.getStatus().setMsg("??????????????????");
        }
        if(slogen!=null && user.getSlogen()!=slogen){
            user.setSlogen(slogen);
        }

        if(sex!=null && user.getSex()!=sex.intValue()){
            user.setSex(sex);
            t.getStatus().setMsg("??????????????????");
        }
        if(avatar!=null && user.getAvatar()!=avatar){
            //???????????????http???
            if(avatar.startsWith("http")){
                user.setAvatar(avatar);
            }else{
                //?????????http
                user.setAvatar(env.getProperty("oss.url")+"/"+avatar);
            }
            t.getStatus().setMsg("??????????????????");
        }

        if(birthday!=null && String.valueOf(user.getBirthday())!=birthday){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date=null;
            try {
                date = sdf.parse(birthday);
                user.setBirthday(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t.getStatus().setMsg("????????????????????????");
        }


        user=userService.updateUser(user);
        UserToken ut=userService.getUserIdByToken(token);

        //??????????????????
        Wallet wallet = walletService.getWalletByUserId(userId);
        Integer balance = 0;
        Integer starCount = 0;
        if(wallet!=null){
            balance = wallet.getBalance();
            starCount = wallet.getStarCount();
        }
        Integer age = 0;

        //????????????
        age = userService.getAge(user.getBirthday());
        user.setStarCount(starCount);
        user.setAge(age);
        ut.setUser(user);
//       u.setAvatar(env.getProperty("cmsconfig.oss.post")+"/"+user.getAvatar());
        t.setUserToken(ut);
        t.getStatus().setMsg("????????????");
        return t;
    }
    @RequestMapping(value="/updatePasswd",method = RequestMethod.PUT)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=7,message = "???????????????",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)})
    @ApiOperation(value = "????????????", notes = "????????????")
    UpdatePasswdResponse updatePasswd (@ApiParam(value = "??????token")@RequestParam String  token,
                                   @ApiParam( value = "?????????")@RequestParam String oldPassword,
                                   @ApiParam( value = "?????????")@RequestParam String newPassword
    )throws ApiNotFoundException,ApiNotTokenException {
        UserToken userToken=userService.getUserIdByToken(token);
        if(userToken==null){
            throw new ApiNotTokenException();
        }
        User user=userService.getUser(userToken.getUserId());
        if(!user.getPassword().equals(oldPassword)){
            throw new ApiNotFoundException("???????????????",7);
        }
        user.setPassword(newPassword);
        userService.updateUser(user);
        List<UserToken> userTokenList = userTokenService.getValidUserTokenListByUserId(user.getId());
        for(UserToken userTokenItem:userTokenList){
            userTokenService.setUnValidUserToken(userTokenItem.getId(),user.getId());
        }
        UpdatePasswdResponse updatePasswdResponse=new UpdatePasswdResponse();

        updatePasswdResponse.setUserToken(userToken);
        updatePasswdResponse.getStatus().setMsg("??????????????????");

        return updatePasswdResponse;

    }
    @RequestMapping(value="/forgetPasswd",method = RequestMethod.PUT)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=8,message = "?????????????????????????????????",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)})
    @ApiOperation(value = "?????????????????????", notes = "?????????????????????")
    ForgetPasswdResponse forgetPasswd (@ApiParam(value = "???????????????")@RequestParam String  phone,
                                       @ApiParam(value = "????????????")@RequestParam String device,
                                       @ApiParam( value = "?????????")@RequestParam String password
    )throws ApiNotFoundException {

        User user=userService.getUserByPhone(phone);
        if(user==null){
            throw new ApiNotFoundException("?????????????????????????????????",8);
        }
        user.setPassword(password);
        userService.updateUser(user);


        List<UserToken> userTokens=userService.getUserTokensByUserIdAndDevice(user.getId(),device);
        UserToken userToken=new UserToken();
        if(userTokens.size()==0){
//            userToken=new UserToken();
            String userAgent=request.getHeader("User-Agent");
            String str=String.valueOf(user.getId())+String.valueOf(System.currentTimeMillis());
//            System.out.println("str***"+str);
            String token=getMD5(str);
            String  refreshToken=getMD5(token);
            userToken.setUserAgent(userAgent);
            userToken.setToken(token);
            userToken.setDevice(device);
//            System.out.println("str***"+11111);
            userToken.setCreateTime(new Date());
            userToken.setUserId(user.getId());
            userToken.setRefreshToken(refreshToken);
            userToken.setIsValid(0);//0??????1??????
            userToken.setDeviceName("");
            userToken.setDeviceUnique("");
            userToken.setAddr("");
            userToken.setLastLoginTime(new Date());
            userToken=userService.addUserToken(userToken);
        }else{
            userToken=userTokens.get(0);
        }

        ForgetPasswdResponse f=new ForgetPasswdResponse();

        f.setUserToken(userToken);
        f.getStatus().setMsg("????????????");
        return f;

    }

    @RequestMapping(value="/bindWeixin",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=202,message = "?????????????????????????????????",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)})
    @ApiOperation(value = "?????????????????????", notes = "?????????????????????")
    BindWeixinResponse bindWeixin (
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam( value = "??????????????????id")@RequestParam String unionid
    ) throws ApiNotTokenException,ApiDuplicateException {
        Long userId= null;
        userId = userService.checkAndGetCurrentUserId(token);
        User user=userService.getUser(userId);

        UserAccount userAccount=userService.getUserAccountBySrcId(unionid);
        if(userAccount!=null){
            throw new ApiDuplicateException("?????????????????????????????????");
        }

        UserAccount userAccount1=new UserAccount();
        userAccount1.setUser(user);
        userAccount1.setCreateTime(new Date());
        userAccount1.setAccountStyle(UserAccountStyle.WEIXIN);
        userAccount1.setSrcId(unionid);

        UserAccount userAccount2= userService.addUserAccount(userAccount1);

        CouponGetRecord couponGetRecord = couponService.getCouponGetRecord(unionid);
        if(couponGetRecord != null) {
            userExtendService.editParentId(user.getId().intValue(), couponGetRecord.getUserId().intValue());
        }

        BindWeixinResponse b=new BindWeixinResponse();
        b.setUserAccount(userAccount2);
        b.getStatus().setMsg("??????????????????");
        return b;

    }
//    static Pattern phonePattern = Pattern.compile("12312341234|^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$");
    static Pattern phonePattern = Pattern.compile("^1\\d{10}$");
    @RequestMapping(value="/checkPhone",method = RequestMethod.GET)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=13,message = "????????????????????????????????????",response = ExceptionResponse.class),
            @ApiResponse(code=14,message = "????????????????????????",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)})
    @ApiOperation(value = "???????????????????????????", notes = "???????????????????????????")
    CheckPhoneResponse checkPhone (
                                   @ApiParam( value = "?????????")@RequestParam String phone
    ) throws ApiNotFoundException {



        Matcher m = phonePattern.matcher(phone);

        if(!m.matches()){
            throw new ApiNotFoundException("???????????????????????????",14);
        }

        User user=userService.getUserByPhone(phone);
        if(user==null){
            CheckPhoneResponse c=new CheckPhoneResponse();
            c.setPhone(phone);
            return c;
        }

        throw new ApiNotFoundException("?????????????????????",13);
    }

    @RequestMapping(value="/getIosIsCheck",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????iOS??????", notes = "??????iOS??????")
    GetIosIsCheckResponse getIosIsCheck (
    ) {

        GetIosIsCheckResponse response = new GetIosIsCheckResponse();
        Integer isCheck = 0;
        //????????????
        String version = VersionUtil.getVerionInfo(request);
        String userAgent = request.getHeader("User-Agent");
        StoryConfig.Platfrom platform=VersionUtil.getPlatform(request);
        if (platform== StoryConfig.Platfrom.IOS) {
            //???????????????????????????????????????config???ver????????????????????????
            String key = "version";
            if(userAgent.contains("appname:zhijianStory")){
                key = "zhijianStory"+"_version";
            }
            Config config=this.configService.getConfigByKey(key);
            //????????????????????????
            Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);
            if(iosIsCheck ==1 ){
                //?????????????????????????????????1
                isCheck = 1;
            }
        }

        response.setIsCheck(isCheck);
        return response;
    }

    @RequestMapping(value="/get_privilege",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "????????????", notes = "????????????")
    GetPrivilegeResponse get_privilege (
            @ApiParam(value = "??????token")@RequestParam(required = false) String  token
    ) throws ApiException {

        GetPrivilegeResponse response = new GetPrivilegeResponse();
        if(token != null){
            Long userId = userService.checkAndGetCurrentUserId(token);
            User user=userService.getUser(userId);
            if(user != null && (user.getIsAbilityPlan() == 1 || user.getSvip()==3 || user.getSvip()==4) ){
                UserAbilityPlanRelate relateRecord = userAbilityPlanRelateService.getLastestUserAbilityPlanRelateRecord(userId);
                UserSvip lastestUserSvipRecord = userSvipService.getLastestUserSvipRecord(userId);

                Date endTime = null;
                if(relateRecord == null && lastestUserSvipRecord != null){
                    endTime = lastestUserSvipRecord.getEndTime();
                }
                if(relateRecord != null && lastestUserSvipRecord == null){
                    endTime = relateRecord.getEndTime();
                }
                if(relateRecord != null && lastestUserSvipRecord != null){
                    if(lastestUserSvipRecord.getEndTime().getTime() > relateRecord.getEndTime().getTime()){
                        endTime=lastestUserSvipRecord.getEndTime();
                    }
                }

                response.setIsAbliltyPlan(1);
                response.setEndTime(endTime);
            }else {
                response.setIsAbliltyPlan(0);
            }
        }else {
            response.setIsAbliltyPlan(0);
        }

        Msg msg1 = new Msg();
        msg1.setTitle("1.???????????????????????????????");
        String cotent1 ="?????????????????????????????????APP???????????????:\n" +
                "??? ????????????????????????\n" +
                "??? ????????????????????????\n" +
                "??? ??????????????????????????????\n" +
                "??? ????????????????????????\n" +
                "??? ????????????" ;

//        "??? ????????????????????????\n" +
//                "??? ????????????????????????\n" +
//                "??? ??????????????????????????????\n" +
//                "??? ????????????????????????\n" +
//                "??? ????????????????????????" ;
        msg1.setContent(cotent1);

        Msg msg2 = new Msg();
        msg2.setTitle("2.?????????????????????????????????");
        String cotent2 ="????????????????????????????????????????????????????????????????????????\n" +
                "?????????????????????????????????????????????????????????????????????";
        msg2.setContent(cotent2);

        List<Msg> msgList = new ArrayList<>();
        msgList.add(msg1);
        msgList.add(msg2);

        response.setMsgList(msgList);
        String url = MyEnv.env.getProperty("ability.url");
        response.setUrl(url);
        return response;
    }

    @RequestMapping(value="/setting",method = RequestMethod.GET)
    @ResponseBody
//    @ApiResponses({@ApiResponse(code=13,message = "????????????????????????????????????",response = ExceptionResponse.class),
//            @ApiResponse(code=14,message = "????????????????????????",response = ExceptionResponse.class),
//            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)})
    @ApiOperation(value = "????????????", notes = "????????????")
    SettingResponse setting (
              @ApiParam(value = "??????token")@RequestParam(required = false,defaultValue = "") String  token,
              @ApiParam(value = "????????????",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  ver
              ) throws ApiException {
        Long userId;
        SettingResponse settingResponse = new SettingResponse();
        /**?????????????????????*/
        settingResponse.setIsBandAli(0);
        /**??????????????????*/
        settingResponse.setQuestionUrl("");

        Integer inPromotion = 1;
        //???????????????????????????
        Integer isCheck = 0;
        //????????????
        String version = VersionUtil.getVerionInfo(request);
        String userAgent = request.getHeader("User-Agent");
        StoryConfig.Platfrom platform=VersionUtil.getPlatform(request);

        logger.info("request??????version========================" +version);

        if (platform== StoryConfig.Platfrom.IOS) {
            //???????????????????????????????????????config???ver????????????????????????
            String key = "version";
            if(userAgent.contains("appname:zhijianStory")){
                key = "zhijianStory"+"_version";
            }
            Config config=this.configService.getConfigByKey(key);
            logger.info("request??????version========================" +version + "version.compareTo(config.getVal())========================" +config.getVal());
            Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);
            if(iosIsCheck ==1 ){
                isCheck = 1;
                inPromotion = 0;
            }
            //??????IP ??????IP????????????????????????
//            String ip = HttpRequest.getIpAddr(request);
//            String country = geoipService.getCountry(ip);
//            logger.info("iosIsCheck====ip================="+ip+"country================="+country);
//            if(!(country == null || country.equalsIgnoreCase("CN")) || ver.compareTo(config.getVal()) == 0 ){
//                logger.info("version.compareTo(config.getVal())========================" +config.getVal());
//                //?????????????????????????????????1
//                isCheck = 1;
//                inPromotion = 0;
//            }
        }

        if(token == null || token.length() <= 0){
            settingResponse.setInPromotion(inPromotion);
            settingResponse.setIsCheck(isCheck);
            logger.info("i========================");
            logger.info("isCheck = "+ isCheck);
            logger.info("inPromotion = "+ inPromotion);
            return settingResponse;
        }else{
            userId = userService.checkAndGetCurrentUserId(token);
        }
        //??????????????????
        User user=userService.getUser(userId);
        int startReadDayNumber = 0;
        if(user != null) {
            WeekPlanReadRecord weekPlanReadRecord = weekPlanReadRecordService.getFirstWeekPlanReadRecord(user.getId().intValue(), WeekPlanStyle.ALL_PLAN);
            if(weekPlanReadRecord != null) {
                startReadDayNumber = statisticLabelService.differentDaysByMillisecond(weekPlanReadRecord.getCreateTime(), new Date());
            }
            UserAccount isBand = userAccountService.getUserAccountByUserIdAndSrcType(user.getId(), UserAccountStyle.ALI.getId());
            if(isBand!= null){
                settingResponse.setIsBandAli(1);
            }
        }

//        if(user != null && user.getIsAbilityPlan() == 1){
//            UserAbilityPlanRelate relateRecord = userAbilityPlanRelateService.getLastestUserAbilityPlanRelateRecord(userId);
//            settingResponse.setEndTime(relateRecord.getEndTime());
//        }

        //?????????????????????????????????
        String phone = user.getPhone();
        if("".equals(phone)){
            settingResponse.setIsPhoneBind(0);
        }else {
            settingResponse.setIsPhoneBind(1);
        }

        settingResponse.setStartReadDayNumber(startReadDayNumber);
        Integer storyCount = weekPlanReadRecordService.getCountByPlanTypeAndTargetTypeAndUserIdAndReadType(WeekPlanStyle.ALL_PLAN, LabelTargetStyle.Story, userId.intValue(), ReadRecordTypeStyle.STORY, 1);
        settingResponse.setStoryNumber(storyCount);

        //???????????????
        Integer storyWordCount = weekPlanReadRecordService.getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(WeekPlanStyle.ALL_PLAN, LabelTargetStyle.Story, userId.intValue(), ReadRecordTypeStyle.STORY, 1);
        Integer magWordCount = weekPlanReadRecordService.getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(WeekPlanStyle.ALL_PLAN, LabelTargetStyle.Magazine, userId.intValue(), ReadRecordTypeStyle.MAGAZINE, 0);

        settingResponse.setWordCount(storyWordCount + magWordCount);

        //??????????????????
        Wallet wallet = walletService.getWalletByUserId(userId);
        Integer balance = 0;
        Integer androidBalance = 0;
        Integer starCount = 0;
        if(wallet!=null){
            balance = wallet.getBalance();
            androidBalance = wallet.getBalanceAndroid();
            starCount = wallet.getStarCount();
        }

        //ios  android ??????
        settingResponse.setIosBalance(balance);
        settingResponse.setAndroidBalance(androidBalance);

        Integer age = 0;
        if(user!=null){
            user.setBalance(balance);
            user.setStarCount(starCount);
            //????????????
            user.setAge(userService.getAge(user.getBirthday()));
        }

        //????????????????????????
        Integer srcType = 1;//??????
        UserAccount wx_userAccount = userService.getUserAccountByUserIdAndSrcType(userId,srcType);
        GetSettingUserAccountResponse getSettingUserAccountResponse = new GetSettingUserAccountResponse();
        getSettingUserAccountResponse.setWx(wx_userAccount);
//        srcType = 5;//??????
//        UserAccount huawei_userAccount = userService.getUserAccountByUserIdAndSrcType(userId,srcType);
//        getSettingUserAccountResponse.setHuawei(huawei_userAccount);
        //????????????????????????
        Integer srcType6 = 6;//Ali
        UserAccount aLi_userAccount = userService.getUserAccountByUserIdAndSrcType(userId,srcType6);
        getSettingUserAccountResponse.setAli(aLi_userAccount);

        settingResponse.setUser(user);
        //?????????????????????
        Integer isSubscribe = 0;
        isSubscribe = storyService.isSubscribe(userId);
        //???????????????????????????????????????
        Integer couponCout = couponService.getUserCouponCount(userId);
        //?????????????????????????????????????????????
        Integer couponDeferredCout = couponDeferredService.getUserDeferredCouponsCount(userId);
        //???????????????????????????????????????
        Integer couponStoryCout = couponStoryExchangeUserService.getUserHasStoryCouponNumber(userId.intValue());

        Integer mixCouponCount = couponCout + couponDeferredCout + couponStoryCout;

        //????????????????????????
        CouponUser cu = couponService.getByUserIdAndCouponId(userId,2L);//2,?????????????????????????????????
        String shareExplain = "";
//        if(cu==null){
//            shareExplain = "??????????????????10????????????";
            shareExplain = "???????????????";
//        }


        List<SubscriptionRecord> subscriptionRecordList = paySubscriptionOrderService.getSubscriptionRecordByUserId(user);
        if(subscriptionRecordList != null && subscriptionRecordList.size() > 0){
            settingResponse.setIsShowSubscription(1);
        } else {
            settingResponse.setIsShowSubscription(0);
        }

        settingResponse.setIsSubscribe(isSubscribe);
        settingResponse.setCouponCount(couponCout);
        settingResponse.setMixCouponCount(mixCouponCount);
        settingResponse.setShareExplain(shareExplain);
        settingResponse.setAccount(getSettingUserAccountResponse);
        settingResponse.setInPromotion(inPromotion);
        settingResponse.setIsCheck(isCheck);
        return settingResponse;
    }
    @RequestMapping(value="/delWxAccount",method = RequestMethod.PUT)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)})
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    DelWxAccountdResponse delWxAccount (@ApiParam(value = "??????token")@RequestParam String  token
    )throws ApiNotFoundException,ApiNotTokenException {

        Long userId = userService.checkAndGetCurrentUserId(token);
        /**
         * ?????????????????????????????????
         */
        Integer srcType = 1;
        UserAccount userAccount = userService.getUserAccountByUserIdAndSrcType(userId,srcType);
        if (userAccount == null){
            throw new ApiNotFoundException("????????????????????????");
        }
        /**
         * ????????????
         */
        userService.delUserAccountByUserIdAndSrcType(userId,srcType);
        DelWxAccountdResponse delWxAccountdResponse=new DelWxAccountdResponse();

        return delWxAccountdResponse;

    }


    //TODO  ?????????????????????
    @RequestMapping(value="/bindAli",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=202,message = "???????????????????????????????????????",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)})
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    BindWeixinResponse bindAli (
//            @ApiParam(value = "??????token")@RequestParam String  token,
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam( value = "?????????????????????id")@RequestParam String srcId
    ) throws ApiNotTokenException,ApiDuplicateException {
        Long userId= null;
        userId = userService.checkAndGetCurrentUserId(ssToken);
        User user=userService.getUser(userId);

        UserAccount userAccount=userService.getUserAccountBySrcId(srcId);
        if(userAccount!=null){
            throw new ApiDuplicateException("???????????????????????????????????????");
        }

        UserAccount userAccount1=new UserAccount();
        userAccount1.setUser(user);
        userAccount1.setCreateTime(new Date());
        userAccount1.setAccountStyle(UserAccountStyle.ALI);
        userAccount1.setSrcId(srcId);

        UserAccount userAccount2= userService.addUserAccount(userAccount1);

        CouponGetRecord couponGetRecord = couponService.getCouponGetRecord(srcId);
        if(couponGetRecord != null) {
            userExtendService.editParentId(user.getId().intValue(), couponGetRecord.getUserId().intValue());
        }

        BindWeixinResponse b=new BindWeixinResponse();
        b.setUserAccount(userAccount2);
        b.getStatus().setMsg("?????????????????????");
        return b;

    }

    @RequestMapping(value="/delAliAccount",method = RequestMethod.PUT)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)})
    @ApiOperation(value = "?????????????????????", notes = "?????????????????????")
    DelWxAccountdResponse delAliAccount (
//            @ApiParam(value = "??????token")@RequestParam String  token
              @RequestHeader(value = "ssToken") String ssToken
    )throws ApiNotFoundException,ApiNotTokenException {

        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        /**
         * ????????????????????????????????????
         */
        Integer srcType = 6;
        UserAccount userAccount = userService.getUserAccountByUserIdAndSrcType(userId,srcType);
        if (userAccount == null){
            throw new ApiNotFoundException("???????????????????????????");
        }
        /**
         * ????????????
         */
        userService.delUserAccountByUserIdAndSrcType(userId,srcType);
        DelWxAccountdResponse delWxAccountdResponse=new DelWxAccountdResponse();

        return delWxAccountdResponse;

    }


    @RequestMapping(value="/editPhone",method = RequestMethod.PUT)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)})
    @ApiOperation(value = "???????????????", notes = "???????????????")
    EditPhoneResponse editPhone (
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam( value = "?????????")@RequestParam String phone
    )throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);
        //????????????????????????????????????
        Integer pageNo = 0;
        Integer pageSize = 100;
        Page<User> puser = userService.getUserByPhonePage(phone,pageNo,pageSize);
        if(puser.getContent().size() >0){
            throw new ApiIsAddException("????????????????????????");
        }
        userService.bindPhone(userId,phone);
//        User user=userService.getUser(userId);
//
//        user.setPhone(phone);
//        userService.updateUser(user);

        EditPhoneResponse editPhoneResponse = new EditPhoneResponse();
        editPhoneResponse.getStatus().setMsg("?????????????????????");
        return editPhoneResponse;

    }
    @RequestMapping(value="/delUserByPhone",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)})
    @ApiOperation(value = "???????????????????????????", notes = "???jemter????????????")
    EditPhoneResponse delUserByPhone (
    )throws ApiException {
        EditPhoneResponse editPhoneResponse = new EditPhoneResponse();
        String phone = "12341234123";

        userService.deleteUserByPhone(phone);


        return editPhoneResponse;

    }

    @RequestMapping(value="/getUserSvipStatus",method = RequestMethod.GET)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=1,message = "??????",response = ExceptionResponse.class)})
    @ApiOperation(value = "????????????svip??????", notes = "???")
    GetUserSvipResponse getUserSvipStatus(
            @ApiParam(value = "??????token")@RequestParam String  token
    )throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);

        GetUserSvipResponse response = new GetUserSvipResponse();

        UserSvip userSvip = userService.getUserSvip(userId);

        response.setUserSvip(userSvip);
        return response;

    }





}
