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
@Api(value="用户",description = "用户相关接口")
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
    @ApiOperation(value = "获取单个用户",notes = "根据用户id 获取用户详情")
    @ApiResponses({@ApiResponse(code=1,message="成功",response = GetUserResponse.class)
                ,@ApiResponse(code=201,message="没有找到这个用户",response = ExceptionResponse.class)})
    GetUserResponse getUser(@ApiParam(value = "用户token")@RequestParam String  token
    )  throws  ApiNotTokenException,ApiNotFoundException {
        int userId;

        userId = (int)userService.checkAndGetCurrentUserId(token);
        UserToken ut=userService.getUserIdByToken(token);


        GetUserResponse t=new GetUserResponse();

        //获取用户余额
        Wallet wallet = walletService.getWalletByUserId(userId);
        User user=userService.getUser(ut.getUserId());
        user.setBalance(wallet.getBalance());
        ut.setUser(user);
        t.setUserToken(ut);
        if(ut==null){
            throw new ApiNotFoundException("没有找到这个用户");
        }


        return t;
    }

    @RequestMapping(value = "/getUserByToken",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取单个用户",notes = "根据用户token 获取用户详情")
    @ApiResponses({@ApiResponse(code=1,message="成功",response = GetUserByTokenResponse.class)
            ,@ApiResponse(code=201,message="没有找到这个用户",response = ExceptionResponse.class)})
    GetUserByTokenResponse getUserByToken(@ApiParam(value = "用户token")@RequestParam String  token
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
            @ApiResponse(code=2,message = "已注册",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "手机注册", notes = "手机注册")
    PhoneRegisterResponse phoneRegister(
            @ApiParam(value = "手机号")@RequestParam String phone,
            @ApiParam(value = "设备信息")@RequestParam String device,
            @ApiParam(value = "密码")@RequestParam String password
    ) throws ApiNotFoundException {
        PhoneRegisterResponse phoneRegisterResponse=new PhoneRegisterResponse();
        String userAgent=request.getHeader("User-Agent");
        String ver = VersionUtil.getVerionInfo(request);
        String channel = VersionUtil.getChannelInfo(request);

        User newUser = userService.phoneRegisterUser(phone,password,ver,channel);

        couponService.getCouponByRegsiter(newUser.getId(),phone);

        /**
         * 调用积分流水添加
         */
        walletService.addRegisterUserStarRecord(newUser.getId().intValue());

        /**添加一周学习计划*/
        logger.info("addddOne======================/**添加一周学习计划*/");
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.TWO_FOUR);
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.FOUR_SIX);


        /**
         * 生成 token
         */
        UserToken userToken = userTokenService.oldAddUserToken(newUser.getId().intValue(),userAgent,device);
        phoneRegisterResponse.getStatus().setMsg("注册成功");
        phoneRegisterResponse.setUserToken(userToken);

        /**
         * 如果用户在小程序中登记过,则注册时添加首周阅读权限(不确定目前到底调用的那边的注册接口,所以usertokencontroller那边也写了同样的代码)
         */
        UserRelate userRelate = userRelateService.getUserRelateByPhone(phone);
        //如果用户登记过
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
            @ApiResponse(code=2,message = "已注册",response = ExceptionResponse.class),
           @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class),
           @ApiResponse(code=3,message = "手机号为空",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "第三方注册", notes = "第三方注册")
    OtherRegisterResponse otherRegister(
            @ApiParam(value = "第三方信息")@RequestParam String srcId,
            @ApiParam(value = "手机号",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  phone,
            @ApiParam(value = "第三方登陆类型（微信1，qq2，微博3，游客4，华为5)")@RequestParam Integer type,
            @ApiParam(value = "设备信息")@RequestParam String device,
            @ApiParam(value = "第三方头像(可选)",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String avatar,
            @ApiParam(value = "第三方昵称(可选)",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String nick,
            @ApiParam(value = "第三方性别（1男0女）(可选)",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") Integer sex,
            @ApiParam(value = "第三方生日(可选)",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String birthday
    ) throws ApiException {

        //账户类型
        UserAccountStyle userAccountStyle=UserAccountStyle.getById(type);

        OtherRegisterResponse otherRegisterResponse=new OtherRegisterResponse();
        userService.checkUserPhoneCondition(UserAccountStyle.getById(type),phone,srcId);
        String ver = VersionUtil.getVerionInfo(request);
        String channel = VersionUtil.getChannelInfo(request);
        String userAgent = request.getHeader("User-Agent");
        User newUser = userService.otherRegisterUser(ver,channel, UserAccountStyle.getById(type), phone, nick, sex, birthday, avatar);

        /**
         * 生成第三方关联信息
         */
        userService.addUserAccountByUser(newUser,srcId,UserAccountStyle.getById(type));

        /**添加一周学习计划*/
        logger.info("addddOne======================/**添加一周学习计划*/");
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.TWO_FOUR);
        weekPlanJoinService.addOneWeekPlanByUserId(newUser.getId().intValue(), WeekPlanStyle.FOUR_SIX);


        /**
         * 处理优惠券
         * type = 4 的游客用户没有优惠券
         */
        if(userAccountStyle != UserAccountStyle.GUEST){
            couponService.getCouponByRegsiter(newUser.getId(),phone);
        }
        /**
         * 调用积分流水添加
         */
        walletService.addRegisterUserStarRecord(newUser.getId().intValue());

        /**
         * 生成 token
         */
        UserToken userToken = userTokenService.oldAddUserToken(newUser.getId().intValue(),userAgent,device);
        otherRegisterResponse.setUserToken(userToken);

        /**
         * 如果用户在小程序中登记过,则注册时添加首周阅读权限(不确定目前到底调用的那边的注册接口,所以usertokencontroller那边也写了同样的代码)
         */
        UserRelate userRelate = userRelateService.getUserRelateByUnionid(srcId);
        //如果用户登记过
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
            @ApiResponse(code=3,message = "手机号错误或此手机号未注册",response = ExceptionResponse.class),
            @ApiResponse(code=4,message = "密码错误",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "手机登录", notes = "手机登录")
    PhoneLoginResponse phoneLogin (
            @ApiParam(value = "手机号")@RequestParam String phone,
            @ApiParam(value = "设备信息")@RequestParam String device,
            @ApiParam(value = "密码")@RequestParam String password
    ) throws ApiNotFoundException {
        PhoneLoginResponse phoneLoginResponse=new PhoneLoginResponse();
        String userAgent = request.getHeader("User-Agent");

        User user = userService.getUserByPhone(phone);
        if(user==null){
            throw new ApiNotFoundException("手机号错误或此手机号未注册",3);
        }
        if(!user.getPassword().equals(password)){
            throw new ApiNotFoundException("密码错误",4);
        }

        UserToken userToken = userTokenService.oldAddUserToken(user.getId().intValue(),userAgent,device);
        phoneLoginResponse.setUserToken(userToken);
        return phoneLoginResponse;

    }

    @RequestMapping(value="/otherLogin",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=5,message = "第三方账号未注册",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "第三方登录", notes = "第三方登录")
    OtherLoginResponse otherLogin (
            @ApiParam(value = "第三方信息")@RequestParam String srcId,
            @ApiParam(value = "设备信息")@RequestParam String device
    ) throws ApiNotFoundException {
        OtherLoginResponse otherLoginResponse=new OtherLoginResponse();
        String userAgent=request.getHeader("User-Agent");

        UserAccount userAccount = userService.getUserAccountBySrcId(srcId);
        if(userAccount==null){
            throw new ApiNotFoundException("第三方账号未注册",5);
        }

        UserToken userToken = userTokenService.oldAddUserToken(userAccount.getUserId().intValue(),userAgent,device);
        otherLoginResponse.setUserToken(userToken);
        return otherLoginResponse;
    }

    @RequestMapping(value="/finishUser",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "完善用户信息", notes = "完善用户信息")
    FinishUserResponse finishUser (@ApiParam(value = "用户token")@RequestParam String  token,
                                  @ApiParam( value = "昵称")@RequestParam String nick,
                                  @ApiParam(value = "性别,0女，1男")@RequestParam Integer  sex,
                                  @ApiParam(value = "地址",required = false)@RequestParam(required = false) String addr,
                                  @ApiParam(value = "生日")@RequestParam String birthday,
                                  @ApiParam(value = "个性签名",required = false)@RequestParam(required = false) String slogen,
                                  @ApiParam(value = "头像文件地址",required = false)@RequestParam(required = false) String avatar
    ) throws ApiNotTokenException {
        Long userId= null;
        FinishUserResponse t=new FinishUserResponse();
        userId = userService.checkAndGetCurrentUserId(token);

        User user=userService.getUser(userId);
        if(nick!=null && user.getNick()!=nick){
            user.setNick(nick);
            t.getStatus().setMsg("更新名称成功");
        }
        if(addr!=null && user.getAddr()!=addr){
            user.setAddr(addr);
            t.getStatus().setMsg("更新地址成功");
        }
        if(slogen!=null && user.getSlogen()!=slogen){
            user.setSlogen(slogen);
        }

        if(sex!=null && user.getSex()!=sex.intValue()){
            user.setSex(sex);
            t.getStatus().setMsg("更新性别成功");
        }
        if(avatar!=null && user.getAvatar()!=avatar){
            //判断是否有http头
            if(avatar.startsWith("http")){
                user.setAvatar(avatar);
            }else{
                //不包含http
                user.setAvatar(env.getProperty("oss.url")+"/"+avatar);
            }
            t.getStatus().setMsg("更新头像成功");
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
            t.getStatus().setMsg("更新出生日期成功");
        }


        user=userService.updateUser(user);
        UserToken ut=userService.getUserIdByToken(token);

        //获取用户余额
        Wallet wallet = walletService.getWalletByUserId(userId);
        Integer balance = 0;
        Integer starCount = 0;
        if(wallet!=null){
            balance = wallet.getBalance();
            starCount = wallet.getStarCount();
        }
        Integer age = 0;

        //获取年龄
        age = userService.getAge(user.getBirthday());
        user.setStarCount(starCount);
        user.setAge(age);
        ut.setUser(user);
//       u.setAvatar(env.getProperty("cmsconfig.oss.post")+"/"+user.getAvatar());
        t.setUserToken(ut);
        t.getStatus().setMsg("修改成功");
        return t;
    }
    @RequestMapping(value="/updatePasswd",method = RequestMethod.PUT)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=7,message = "原密码错误",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)})
    @ApiOperation(value = "修改密码", notes = "修改密码")
    UpdatePasswdResponse updatePasswd (@ApiParam(value = "用户token")@RequestParam String  token,
                                   @ApiParam( value = "原密码")@RequestParam String oldPassword,
                                   @ApiParam( value = "新密码")@RequestParam String newPassword
    )throws ApiNotFoundException,ApiNotTokenException {
        UserToken userToken=userService.getUserIdByToken(token);
        if(userToken==null){
            throw new ApiNotTokenException();
        }
        User user=userService.getUser(userToken.getUserId());
        if(!user.getPassword().equals(oldPassword)){
            throw new ApiNotFoundException("原密码错误",7);
        }
        user.setPassword(newPassword);
        userService.updateUser(user);
        List<UserToken> userTokenList = userTokenService.getValidUserTokenListByUserId(user.getId());
        for(UserToken userTokenItem:userTokenList){
            userTokenService.setUnValidUserToken(userTokenItem.getId(),user.getId());
        }
        UpdatePasswdResponse updatePasswdResponse=new UpdatePasswdResponse();

        updatePasswdResponse.setUserToken(userToken);
        updatePasswdResponse.getStatus().setMsg("重置密码成功");

        return updatePasswdResponse;

    }
    @RequestMapping(value="/forgetPasswd",method = RequestMethod.PUT)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=8,message = "此手机号注册用户不存在",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)})
    @ApiOperation(value = "忘记密码，重置", notes = "忘记密码，重置")
    ForgetPasswdResponse forgetPasswd (@ApiParam(value = "用户手机号")@RequestParam String  phone,
                                       @ApiParam(value = "设备信息")@RequestParam String device,
                                       @ApiParam( value = "新密码")@RequestParam String password
    )throws ApiNotFoundException {

        User user=userService.getUserByPhone(phone);
        if(user==null){
            throw new ApiNotFoundException("此手机号注册用户不存在",8);
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
            userToken.setIsValid(0);//0无效1有效
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
        f.getStatus().setMsg("重置成功");
        return f;

    }

    @RequestMapping(value="/bindWeixin",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=202,message = "此微信号已绑定其他账号",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)})
    @ApiOperation(value = "手机号绑定微信", notes = "手机号绑定微信")
    BindWeixinResponse bindWeixin (
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam( value = "微信账号唯一id")@RequestParam String unionid
    ) throws ApiNotTokenException,ApiDuplicateException {
        Long userId= null;
        userId = userService.checkAndGetCurrentUserId(token);
        User user=userService.getUser(userId);

        UserAccount userAccount=userService.getUserAccountBySrcId(unionid);
        if(userAccount!=null){
            throw new ApiDuplicateException("此微信号已绑定其他账号");
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
        b.getStatus().setMsg("微信绑定成功");
        return b;

    }
//    static Pattern phonePattern = Pattern.compile("12312341234|^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$");
    static Pattern phonePattern = Pattern.compile("^1\\d{10}$");
    @RequestMapping(value="/checkPhone",method = RequestMethod.GET)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=13,message = "使用该手机号的用户已存在",response = ExceptionResponse.class),
            @ApiResponse(code=14,message = "该手机号格式非法",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)})
    @ApiOperation(value = "验证手机号是否存在", notes = "验证手机号是否存在")
    CheckPhoneResponse checkPhone (
                                   @ApiParam( value = "手机号")@RequestParam String phone
    ) throws ApiNotFoundException {



        Matcher m = phonePattern.matcher(phone);

        if(!m.matches()){
            throw new ApiNotFoundException("请输入正确的手机号",14);
        }

        User user=userService.getUserByPhone(phone);
        if(user==null){
            CheckPhoneResponse c=new CheckPhoneResponse();
            c.setPhone(phone);
            return c;
        }

        throw new ApiNotFoundException("此手机号已注册",13);
    }

    @RequestMapping(value="/getIosIsCheck",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "是否iOS审核", notes = "是否iOS审核")
    GetIosIsCheckResponse getIosIsCheck (
    ) {

        GetIosIsCheckResponse response = new GetIosIsCheckResponse();
        Integer isCheck = 0;
        //判断设备
        String version = VersionUtil.getVerionInfo(request);
        String userAgent = request.getHeader("User-Agent");
        StoryConfig.Platfrom platform=VersionUtil.getPlatform(request);
        if (platform== StoryConfig.Platfrom.IOS) {
            //判断版本是否在审核中，获取config的ver是否等于当前版本
            String key = "version";
            if(userAgent.contains("appname:zhijianStory")){
                key = "zhijianStory"+"_version";
            }
            Config config=this.configService.getConfigByKey(key);
            //判断是否在审核中
            Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);
            if(iosIsCheck ==1 ){
                //在审核中，返回审核状态1
                isCheck = 1;
            }
        }

        response.setIsCheck(isCheck);
        return response;
    }

    @RequestMapping(value="/get_privilege",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "畅享权限", notes = "畅享权限")
    GetPrivilegeResponse get_privilege (
            @ApiParam(value = "用户token")@RequestParam(required = false) String  token
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
        msg1.setTitle("1.畅读权限包括哪些内容?");
        String cotent1 ="拥有畅读权限可免费阅读APP内容，包括:\n" +
                "① 飞船阅读课启蒙版\n" +
                "② 飞船阅读课成长版\n" +
                "③ 全部带有畅读标识故事\n" +
                "④ 益智思维互动训练\n" +
                "⑤ 大咖课程" ;

//        "① 飞船阅读课启蒙版\n" +
//                "② 飞船阅读课成长版\n" +
//                "③ 全部带有畅读标识故事\n" +
//                "④ 益智思维互动训练\n" +
//                "⑤ 线上大咖专家讲座" ;
        msg1.setContent(cotent1);

        Msg msg2 = new Msg();
        msg2.setTitle("2.畅读权限有效期是多久？");
        String cotent2 ="畅读权限暂不支持单独购买，购买宝宝会读即可享受。\n" +
                "购买宝宝会读，即可获得相应购买时长的畅读权限。";
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
//    @ApiResponses({@ApiResponse(code=13,message = "使用该手机号的用户已存在",response = ExceptionResponse.class),
//            @ApiResponse(code=14,message = "该手机号格式非法",response = ExceptionResponse.class),
//            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)})
    @ApiOperation(value = "设置接口", notes = "设置界面")
    SettingResponse setting (
              @ApiParam(value = "用户token")@RequestParam(required = false,defaultValue = "") String  token,
              @ApiParam(value = "版本号）",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  ver
              ) throws ApiException {
        Long userId;
        SettingResponse settingResponse = new SettingResponse();
        /**是否绑定支付宝*/
        settingResponse.setIsBandAli(0);
        /**常见问题链接*/
        settingResponse.setQuestionUrl("");

        Integer inPromotion = 1;
        //判断返回是否审核中
        Integer isCheck = 0;
        //判断设备
        String version = VersionUtil.getVerionInfo(request);
        String userAgent = request.getHeader("User-Agent");
        StoryConfig.Platfrom platform=VersionUtil.getPlatform(request);

        logger.info("request取值version========================" +version);

        if (platform== StoryConfig.Platfrom.IOS) {
            //判断版本是否在审核中，获取config的ver是否等于当前版本
            String key = "version";
            if(userAgent.contains("appname:zhijianStory")){
                key = "zhijianStory"+"_version";
            }
            Config config=this.configService.getConfigByKey(key);
            logger.info("request取值version========================" +version + "version.compareTo(config.getVal())========================" +config.getVal());
            Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);
            if(iosIsCheck ==1 ){
                isCheck = 1;
                inPromotion = 0;
            }
            //获取IP 根据IP判断是否在审核中
//            String ip = HttpRequest.getIpAddr(request);
//            String country = geoipService.getCountry(ip);
//            logger.info("iosIsCheck====ip================="+ip+"country================="+country);
//            if(!(country == null || country.equalsIgnoreCase("CN")) || ver.compareTo(config.getVal()) == 0 ){
//                logger.info("version.compareTo(config.getVal())========================" +config.getVal());
//                //在审核中，返回审核状态1
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
        //获取用户信息
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

        //判断用户是否绑定了手机
        String phone = user.getPhone();
        if("".equals(phone)){
            settingResponse.setIsPhoneBind(0);
        }else {
            settingResponse.setIsPhoneBind(1);
        }

        settingResponse.setStartReadDayNumber(startReadDayNumber);
        Integer storyCount = weekPlanReadRecordService.getCountByPlanTypeAndTargetTypeAndUserIdAndReadType(WeekPlanStyle.ALL_PLAN, LabelTargetStyle.Story, userId.intValue(), ReadRecordTypeStyle.STORY, 1);
        settingResponse.setStoryNumber(storyCount);

        //阅读文字量
        Integer storyWordCount = weekPlanReadRecordService.getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(WeekPlanStyle.ALL_PLAN, LabelTargetStyle.Story, userId.intValue(), ReadRecordTypeStyle.STORY, 1);
        Integer magWordCount = weekPlanReadRecordService.getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(WeekPlanStyle.ALL_PLAN, LabelTargetStyle.Magazine, userId.intValue(), ReadRecordTypeStyle.MAGAZINE, 0);

        settingResponse.setWordCount(storyWordCount + magWordCount);

        //获取用户余额
        Wallet wallet = walletService.getWalletByUserId(userId);
        Integer balance = 0;
        Integer androidBalance = 0;
        Integer starCount = 0;
        if(wallet!=null){
            balance = wallet.getBalance();
            androidBalance = wallet.getBalanceAndroid();
            starCount = wallet.getStarCount();
        }

        //ios  android 余额
        settingResponse.setIosBalance(balance);
        settingResponse.setAndroidBalance(androidBalance);

        Integer age = 0;
        if(user!=null){
            user.setBalance(balance);
            user.setStarCount(starCount);
            //获取年龄
            user.setAge(userService.getAge(user.getBirthday()));
        }

        //获取微信绑定信息
        Integer srcType = 1;//微信
        UserAccount wx_userAccount = userService.getUserAccountByUserIdAndSrcType(userId,srcType);
        GetSettingUserAccountResponse getSettingUserAccountResponse = new GetSettingUserAccountResponse();
        getSettingUserAccountResponse.setWx(wx_userAccount);
//        srcType = 5;//华为
//        UserAccount huawei_userAccount = userService.getUserAccountByUserIdAndSrcType(userId,srcType);
//        getSettingUserAccountResponse.setHuawei(huawei_userAccount);
        //获取微信绑定信息
        Integer srcType6 = 6;//Ali
        UserAccount aLi_userAccount = userService.getUserAccountByUserIdAndSrcType(userId,srcType6);
        getSettingUserAccountResponse.setAli(aLi_userAccount);

        settingResponse.setUser(user);
        //判断是否已订阅
        Integer isSubscribe = 0;
        isSubscribe = storyService.isSubscribe(userId);
        //获取此用户未过期优惠券数量
        Integer couponCout = couponService.getUserCouponCount(userId);
        //获取此用户未过期延时优惠券数量
        Integer couponDeferredCout = couponDeferredService.getUserDeferredCouponsCount(userId);
        //获取此用户未过期兑换券数量
        Integer couponStoryCout = couponStoryExchangeUserService.getUserHasStoryCouponNumber(userId.intValue());

        Integer mixCouponCount = couponCout + couponDeferredCout + couponStoryCout;

        //判断是否首次分享
        CouponUser cu = couponService.getByUserIdAndCouponId(userId,2L);//2,代表分享要领取的优惠券
        String shareExplain = "";
//        if(cu==null){
//            shareExplain = "首次分享获取10元优惠券";
            shareExplain = "推荐领优惠";
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
    @ApiResponses({@ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)})
    @ApiOperation(value = "解除绑定微信", notes = "解除绑定微信")
    DelWxAccountdResponse delWxAccount (@ApiParam(value = "用户token")@RequestParam String  token
    )throws ApiNotFoundException,ApiNotTokenException {

        Long userId = userService.checkAndGetCurrentUserId(token);
        /**
         * 查询此用户微信绑定信息
         */
        Integer srcType = 1;
        UserAccount userAccount = userService.getUserAccountByUserIdAndSrcType(userId,srcType);
        if (userAccount == null){
            throw new ApiNotFoundException("此用户未绑定微信");
        }
        /**
         * 解除绑定
         */
        userService.delUserAccountByUserIdAndSrcType(userId,srcType);
        DelWxAccountdResponse delWxAccountdResponse=new DelWxAccountdResponse();

        return delWxAccountdResponse;

    }


    //TODO  绑定支付宝用户
    @RequestMapping(value="/bindAli",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=202,message = "此支付宝账号已绑定其他账号",response = ExceptionResponse.class),
            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)})
    @ApiOperation(value = "手机号绑定支付宝", notes = "手机号绑定支付宝")
    BindWeixinResponse bindAli (
//            @ApiParam(value = "用户token")@RequestParam String  token,
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam( value = "支付宝账号唯一id")@RequestParam String srcId
    ) throws ApiNotTokenException,ApiDuplicateException {
        Long userId= null;
        userId = userService.checkAndGetCurrentUserId(ssToken);
        User user=userService.getUser(userId);

        UserAccount userAccount=userService.getUserAccountBySrcId(srcId);
        if(userAccount!=null){
            throw new ApiDuplicateException("此支付宝账号已绑定其他账号");
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
        b.getStatus().setMsg("支付宝绑定成功");
        return b;

    }

    @RequestMapping(value="/delAliAccount",method = RequestMethod.PUT)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)})
    @ApiOperation(value = "解除绑定支付宝", notes = "解除绑定支付宝")
    DelWxAccountdResponse delAliAccount (
//            @ApiParam(value = "用户token")@RequestParam String  token
              @RequestHeader(value = "ssToken") String ssToken
    )throws ApiNotFoundException,ApiNotTokenException {

        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        /**
         * 查询此用户支付宝绑定信息
         */
        Integer srcType = 6;
        UserAccount userAccount = userService.getUserAccountByUserIdAndSrcType(userId,srcType);
        if (userAccount == null){
            throw new ApiNotFoundException("此用户未绑定支付宝");
        }
        /**
         * 解除绑定
         */
        userService.delUserAccountByUserIdAndSrcType(userId,srcType);
        DelWxAccountdResponse delWxAccountdResponse=new DelWxAccountdResponse();

        return delWxAccountdResponse;

    }


    @RequestMapping(value="/editPhone",method = RequestMethod.PUT)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)})
    @ApiOperation(value = "更换手机号", notes = "更换手机号")
    EditPhoneResponse editPhone (
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam( value = "手机号")@RequestParam String phone
    )throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);
        //验证此手机号是否被绑定过
        Integer pageNo = 0;
        Integer pageSize = 100;
        Page<User> puser = userService.getUserByPhonePage(phone,pageNo,pageSize);
        if(puser.getContent().size() >0){
            throw new ApiIsAddException("此手机号已被绑定");
        }
        userService.bindPhone(userId,phone);
//        User user=userService.getUser(userId);
//
//        user.setPhone(phone);
//        userService.updateUser(user);

        EditPhoneResponse editPhoneResponse = new EditPhoneResponse();
        editPhoneResponse.getStatus().setMsg("修改手机号成功");
        return editPhoneResponse;

    }
    @RequestMapping(value="/delUserByPhone",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)})
    @ApiOperation(value = "删除特定手机号用户", notes = "（jemter测试用）")
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
            @ApiResponse(code=1,message = "成功",response = ExceptionResponse.class)})
    @ApiOperation(value = "获取用户svip状态", notes = "）")
    GetUserSvipResponse getUserSvipStatus(
            @ApiParam(value = "用户token")@RequestParam String  token
    )throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);

        GetUserSvipResponse response = new GetUserSvipResponse();

        UserSvip userSvip = userService.getUserSvip(userId);

        response.setUserSvip(userSvip);
        return response;

    }





}
