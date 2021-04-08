package com.ifenghui.storybookapi.app.transaction.controller;

import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.entity.VCouponMix;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.Coupon;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredUser;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeUser;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import com.ifenghui.storybookapi.app.transaction.response.*;
import com.ifenghui.storybookapi.app.transaction.service.CouponDeferredService;
import com.ifenghui.storybookapi.app.transaction.service.CouponService;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeUserService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.OrderStyle;
import com.ifenghui.storybookapi.util.VersionUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/coupon")
@Api(value="优惠券",description = "优惠券／订阅券")
public class CouponController {
    @Autowired
    CouponService couponService;

    @Autowired
    UserService userService;

    @Autowired
    CouponDeferredService couponDeferredService;

    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;

    @Autowired
    private Environment env;

    @Autowired
    StoryService storyService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value="/acceptCoupon",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "领取优惠券,分享大使使用，用于记录被分享人的手机号", notes = "")
   public  AcceptCouponResponse acceptCoupon(
            @ApiParam(value = "用户id")@RequestParam Long userId,
            @ApiParam(value = "电话号")@RequestParam String phone
    )throws ApiException {

        AcceptCouponResponse acceptCouponResponse =new AcceptCouponResponse();
        Void res = couponService.acceptCoupon(userId,phone);

        return acceptCouponResponse;
    }

    @RequestMapping(value="/collectDeferredCoupon",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=203,message="用户信息验证无效",response = ExceptionResponse.class),
            @ApiResponse(code=1,message="成功",response = ExceptionResponse.class),
            @ApiResponse(code=2,message="已领取",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "领取赠阅券", notes = "")
    public CollectDeferredCouponResponse collectDeferredCoupon(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "赠阅券id")@RequestParam Long couponId,
            @ApiParam(value = "推广渠道",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  channel
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);


        CollectDeferredCouponResponse collectDeferredCouponResponse =new CollectDeferredCouponResponse();
        couponDeferredService.collectDeferredCoupon(userId,couponId,channel);

        return collectDeferredCouponResponse;
    }
    @RequestMapping(value="/getUserValidityDeferredCoupons",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户可用赠阅券列表", notes = "")
    public GetUserValidityDeferredCouponsResponse getUserValidityDeferredCoupons(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "订阅价格id")@RequestParam Long priceId,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
    )throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        GetUserValidityDeferredCouponsResponse getUserValidityDeferredCouponsResponse =new GetUserValidityDeferredCouponsResponse();

        Page<CouponDeferredUser> couponDeferredUserPage = couponDeferredService.getUserValidityDeferredCoupons(userId,pageNo,pageSize,priceId);
        getUserValidityDeferredCouponsResponse.setCouponDeferredUsers(couponDeferredUserPage.getContent());
        getUserValidityDeferredCouponsResponse.setJpaPage(couponDeferredUserPage);
        String couponRuleUrl = MyEnv.env.getProperty("local.url")+"api/pay/couponRule.action";
        getUserValidityDeferredCouponsResponse.setCouponRuleUrl(couponRuleUrl);
        return getUserValidityDeferredCouponsResponse;
    }
    @RequestMapping(value="/getUserDeferredCoupons",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户赠阅券列表", notes = "")
    public GetUserDeferredCouponsResponse getUserDeferredCoupons(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "类型(1未过期,2已过期)")@RequestParam Integer type,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
    )throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
//        Integer type =1;

        GetUserDeferredCouponsResponse getUserDeferredCouponsResponse =new GetUserDeferredCouponsResponse();
        Page<CouponDeferredUser> couponDeferredUserPage = couponDeferredService.getUserDeferredCoupons(userId,type,pageNo,pageSize);
        getUserDeferredCouponsResponse.setCouponDeferredUsers(couponDeferredUserPage.getContent());
        getUserDeferredCouponsResponse.setJpaPage(couponDeferredUserPage);
        String couponRuleUrl = MyEnv.env.getProperty("local.url")+"api/pay/couponRule.action";
        getUserDeferredCouponsResponse.setCouponRuleUrl(couponRuleUrl);

        return getUserDeferredCouponsResponse;
    }

    @RequestMapping(value="/getCouponByShare",method = RequestMethod.GET)
    @ResponseBody
    @ApiResponse(code=210,message="第一次分享成功后返回的状态",response = ExceptionResponse.class)
    @ApiOperation(value = "分享获取优惠券", notes = "")
    public GetCouponByShareResponse getCouponByShare(
            @ApiParam(value = "用户token")@RequestParam String token
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        String ver = VersionUtil.getVerionInfo(request);
        GetCouponByShareResponse getCouponByShareResponse = new GetCouponByShareResponse();
        if(ver.compareTo("2.0.0") >= 0){
            List<CouponStoryExchangeUser> couponStoryExchangeUserList = couponService.getStoryCouponByShare(userId);
            if(couponStoryExchangeUserList != null && couponStoryExchangeUserList.size() > 0){
                Date nowTime = new Date();
                Coupon couponThree = couponService.getCouponById(3L);//20元
                Date threeEndTime = couponService.getCouponEndTime(nowTime,couponThree);
                CouponUser couponUser = couponService.addCouponUser(3, userId, 0, threeEndTime, nowTime, "", 1, "");
                couponUser.setContent(couponThree.getContent());
                couponUser.setAmount(couponThree.getAmount());
                couponUser.setName(couponThree.getName());
                getCouponByShareResponse.setCouponUser(couponUser);
            }
            getCouponByShareResponse.setCouponStoryUserList(couponStoryExchangeUserList);
            return getCouponByShareResponse;
        } else {
            CouponUser couponUser = null;
            couponUser = couponService.getCouponByShare(userId);
            getCouponByShareResponse.setCouponUser(couponUser);
            return getCouponByShareResponse;
        }
    }
    @RequestMapping(value="/getExpiredMixCoupons",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户已过期券列表", notes = "")
    public GetExpiredMixCouponsResponse getExpiredMixCoupons(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
    )throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
//        Integer type =1;

        GetExpiredMixCouponsResponse getExpiredMixCouponsResponse =new GetExpiredMixCouponsResponse();
        Page<VCouponMix> vCouponMixPage = couponService.getExpiredMixCoupons(userId,pageNo,pageSize);
        getExpiredMixCouponsResponse.setExpiredMixCoupons(vCouponMixPage.getContent());
        getExpiredMixCouponsResponse.setJpaPage(vCouponMixPage);

        return getExpiredMixCouponsResponse;
    }
    @RequestMapping(value="/getShareInfo",method = RequestMethod.GET)
    @ResponseBody
//    @ApiResponse(code=210,message="第一次分享成功后返回的状态",response = ExceptionResponse.class)
    @ApiOperation(value = "获取分享优惠券信息", notes = "")
    public GetShareInfoResponse getShareInfo(
            @ApiParam(value = "用户token")@RequestParam String token
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        GetShareInfoResponse getShareInfoResponse =new GetShareInfoResponse();
        String shareUrl = env.getProperty("local.url")+"api/pay/share.action?userId="+userId;
        String shareIcon = env.getProperty("oss.url")+ "shareIcon/share.png";
        String shareTitle = MyEnv.getMessage("share.title");
        String shareContent = MyEnv.getMessage("share.sub.title");
        String shareMomentsContent = "1年和孩子一起读完100个故事，故事飞船邀您体验神奇的互动故事书。20元优惠券戳开即得。";
        String shareRule = MyEnv.getMessage("share.content");

        getShareInfoResponse.setShareUrl(shareUrl);
        getShareInfoResponse.setShareIcon(shareIcon);
        getShareInfoResponse.setShareTitle(shareTitle);
        getShareInfoResponse.setShareContent(shareContent);
        getShareInfoResponse.setShareMomentsContent(shareMomentsContent);
        getShareInfoResponse.setShareRule(shareRule);

        return getShareInfoResponse;
    }

    @RequestMapping(value="/getUserCoupons",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户优惠券列表", notes = "")
    public GetUserCouponsResponse getUserCoupons(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "类型(1未过期,2已过期)")@RequestParam Integer type,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
    )throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
//        Integer type =1;

        GetUserCouponsResponse getUserCouponsResponse =new GetUserCouponsResponse();
        Page<CouponUser> couponUserPage = couponService.getUserCoupons(userId,type,pageNo,pageSize);
        getUserCouponsResponse.setCouponUsers(couponUserPage.getContent());
        getUserCouponsResponse.setJpaPage(couponUserPage);
        String couponRuleUrl = env.getProperty("local.url")+"api/pay/couponRule.action";
        getUserCouponsResponse.setCouponRuleUrl(couponRuleUrl);

        return getUserCouponsResponse;
    }
    @RequestMapping(value="/getUserUnReadCoupons",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户未读优惠券列表", notes = "")
    public GetUserUnReadCouponsResponse getUserUnReadCoupons(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
    )throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);


        GetUserUnReadCouponsResponse getUserUnReadCouponsResponse =new GetUserUnReadCouponsResponse();
        Page<CouponUser> couponUserPage = couponService.getUserUnReacCoupons(userId,pageNo,pageSize);
        getUserUnReadCouponsResponse.setCouponUsers(couponUserPage.getContent());
        getUserUnReadCouponsResponse.setJpaPage(couponUserPage);

        return getUserUnReadCouponsResponse;
    }

    @RequestMapping(value="/getUserValidityCoupons",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户可用优惠券列表", notes = "")
    public GetUserValidityCouponsResponse getUserValidityCoupons(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "类型【1订阅，2购买故事集】") @RequestParam(required = false) Integer type,
            @ApiParam(value = "订阅价格id/故事集id")@RequestParam(required = false)Long priceId,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
    )throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        if(priceId == null){
            priceId = 0L;
        }
        if(type == null){
            type = 1;
        }
//        pageNo = pageNo - 1;
        GetUserValidityCouponsResponse getUserValidityCouponsResponse = new GetUserValidityCouponsResponse();
        Page<CouponUser> couponUserPage = couponService.getUserValidityCoupons(userId,pageNo,pageSize,priceId,type);
        getUserValidityCouponsResponse.setCouponUsers(couponUserPage.getContent());
        getUserValidityCouponsResponse.setJpaPage(couponUserPage);
        String couponRuleUrl = env.getProperty("local.url")+"api/pay/couponRule.action";
        getUserValidityCouponsResponse.setCouponRuleUrl(couponRuleUrl);
        return getUserValidityCouponsResponse;
    }

    @RequestMapping(value="/user_valid_coupons",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据价格获取用户可用优惠券列表")
    GetUserValidityCouponsResponse getUserValidityCouponss(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize,
            @ApiParam(value = "价格数字单位（分）")@RequestParam Integer price
    )throws ApiNotTokenException {
        Long userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1;
        GetUserValidityCouponsResponse response = new GetUserValidityCouponsResponse();
        Page<CouponUser> couponUserPage = couponService.getUserValidCouponsByPrice(userId,pageNo,pageSize,price);
        response.setCouponUsers(couponUserPage.getContent());
        response.setJpaPage(couponUserPage);
        String couponRuleUrl = env.getProperty("local.url")+"api/pay/couponRule.action";
        response.setCouponRuleUrl(couponRuleUrl);
        return response;
    }

    @RequestMapping(value="/user_story_coupon_list",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户故事兑换券列表", notes = "获取用户故事兑换券列表")
    GetUserStoryCouponsResponse getUserStoryCoupons(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "是否过期(0 未过期 1 已过期)")@RequestParam Integer isExpire,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
    )throws ApiNotTokenException {
        GetUserStoryCouponsResponse response = new GetUserStoryCouponsResponse();
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        Page<CouponStoryExchangeUser> couponStoryExchangeUserPage = couponStoryExchangeUserService.getCouponStoryExchangeUserPageByUserIdAndIsExpire(userId.intValue(),isExpire,pageNo,pageSize);
        response.setCouponStoryExchangeUserList(couponStoryExchangeUserPage.getContent());
        response.setJpaPage(couponStoryExchangeUserPage);
        String couponRuleUrl = env.getProperty("local.url")+"api/pay/couponRule.action";
        response.setCouponRuleUrl(couponRuleUrl);
        return response;
    }

    @RequestMapping(value="/user_valid_story_coupon_list",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户故事可用兑换券列表", notes = "获取用户故事可用兑换券列表")
    GetUserStoryCouponsResponse getUserValidStoryCoupons(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "是否可用(0 可用 1 不可用)")@RequestParam Integer status,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
    )throws ApiNotTokenException {
        GetUserStoryCouponsResponse response = new GetUserStoryCouponsResponse();
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        Page<CouponStoryExchangeUser> couponStoryExchangeUserPage = couponStoryExchangeUserService.getCouponStoryExchangeUserPageByUserIdAndStatus(userId.intValue(),status,pageNo,pageSize);
        response.setCouponStoryExchangeUserList(couponStoryExchangeUserPage.getContent());
        response.setJpaPage(couponStoryExchangeUserPage);
        String couponRuleUrl = env.getProperty("local.url")+"api/pay/couponRule.action";
        response.setCouponRuleUrl(couponRuleUrl);
        return response;
    }

    @RequestMapping(value = "/collectCoupon", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code = 203, message = "用户信息验证无效", response = ExceptionResponse.class),
            @ApiResponse(code = 1, message = "成功", response = ExceptionResponse.class),
            @ApiResponse(code = 2, message = "已领取", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "领取代金券", notes = "")
    CollectCouponResponse collectCoupon(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "代金券id") @RequestParam Long couponId,
            @ApiParam(value = "推广渠道", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);


        CollectCouponResponse collectCouponResponse = new CollectCouponResponse();
        Void res = couponService.collectCoupon(userId, couponId, channel);
        collectCouponResponse.getStatus().setMsg("领取成功");
        return collectCouponResponse;
    }




    @RequestMapping(value="/use_story_coupon",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "使用故事兑换券购买故事", notes = "使用故事兑换券购买故事")
    UseCouponStoryExchangeUserResponse useCouponStoryExchangeUser(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "故事id")@RequestParam Integer storyId,
            @ApiParam(value = "优惠券id")@RequestParam Integer storyCouponId
    )throws ApiNotTokenException {
        UseCouponStoryExchangeUserResponse response = new UseCouponStoryExchangeUserResponse();
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        couponStoryExchangeUserService.useCouponStoryExchangeUser(storyCouponId,storyId,userId);
        return response;
    }

    @RequestMapping(value="/coupon_use_condition",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "订单使用兑换券情况", notes = "订单使用兑换券情况")
    UserHasCouponNumberResponse getCouponUseCondition(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "价格")@RequestParam Integer price,
            @ApiParam(value = "订单类型")@RequestParam OrderStyle orderStyle,
            @ApiParam(value = "对应传值（故事订单传故事数量 系列故事传系列故事id 课程订单传课程数量）")@RequestParam(required = false) Integer targetValue
    ) throws ApiNotTokenException {
        UserHasCouponNumberResponse response = new UserHasCouponNumberResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        response.setCashCouponNumber(couponService.getCashCouponNumber(userId,price));
        if(orderStyle.equals(OrderStyle.VIP_ORDER)){
            response.setStoryCouponNumber(0);
            response.setStoryCouponNeedNumber(1);
        } else if(orderStyle.equals(OrderStyle.ABILITY_PLAN_ORDER)){
            //购买优能计划类型价格写死判断是否可用优惠券
            response.setCashCouponNumber(couponService.getCashCouponNumber(userId,460000));
            response.setStoryCouponNumber(0);
            response.setStoryCouponNeedNumber(1);
        } else {
            response.setStoryCouponNumber(couponStoryExchangeUserService.getUserHasStoryCouponNumber(userId.intValue()));
            response.setStoryCouponNeedNumber(couponStoryExchangeUserService.getUserNeedUseStoryCouponNumber(userId.intValue(),orderStyle, targetValue));
        }

        return response;
    }

    @RequestMapping(value="/activity_send_story_coupon", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单使用兑换券情况", notes = "订单使用兑换券情况")
    BaseResponse sendActivityStoryCoupon(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "数量")@RequestParam Integer sendCouponNum
    ){
        BaseResponse response = new BaseResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        couponStoryExchangeUserService.sendActivityStoryCoupon(userId.intValue(), sendCouponNum);
        return response;
    }

}
