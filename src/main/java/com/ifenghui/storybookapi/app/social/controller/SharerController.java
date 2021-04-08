package com.ifenghui.storybookapi.app.social.controller;

/**
 * Created by jia on 2016/12/23.
 */

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.social.entity.VipFriendCard;
import com.ifenghui.storybookapi.app.social.response.GetVipFriendCardInfoResponse;
import com.ifenghui.storybookapi.app.social.response.GetVipFriendCardPageResponse;
import com.ifenghui.storybookapi.app.social.response.GetWxUnionIdResponse;
import com.ifenghui.storybookapi.app.social.service.VipFriendCardService;
import com.ifenghui.storybookapi.app.transaction.controller.CouponController;
import com.ifenghui.storybookapi.app.transaction.dao.CouponGetRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponGetRecord;
import com.ifenghui.storybookapi.app.transaction.response.AcceptCouponResponse;
import com.ifenghui.storybookapi.app.user.dao.UserAccountDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.app.user.service.UserAccountService;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.controller.PayController;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.UserAccountStyle;
import com.ifenghui.storybookapi.util.weixin.LoadJsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/sharer")
@Api(value = "分享大使", description = "分享大使")
public class SharerController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private Environment env;

    @Autowired
    UserService userService;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    CouponController couponController;

    @Autowired
    VipFriendCardService vipFriendCardService;

    @Autowired
    PayController payController;

    Logger logger = Logger.getLogger(SharerController.class);

    @Autowired
    ConfigService configService;

    /**
     * 微信分享
     */
    @RequestMapping(value = "/wxShare", method = RequestMethod.GET)
    public void wxShare(HttpServletRequest request, HttpServletResponse response, String code, Integer userId) throws Exception {

        logger.info("--------方法开始-----------");
        String appid = "wx31edd56a45a12c7f";
        String secret = "6c683afad5338b3e54189a2a48091401";

        logger.info("--------appid = "+appid+"-----------");

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";

        logger.info("--------url = "+url+"-----------");

        String getTokenResponse = LoadJsonUtil.getJSON(url);
        // 获得 access_token  openid
        JSONObject jsonObject = JSONObject.fromObject(getTokenResponse);

        logger.info("-----------------------------");
        logger.info("请求返回数据");
        logger.info(jsonObject);
        logger.info("-----------------------------");
        //用户的唯一标识（openid）
        String openid = String.valueOf(jsonObject.get("openid"));
        String access_token = String.valueOf(jsonObject.get("access_token"));

        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&scope=snsapi_userinfo" + "&lang=zh_CN";
        String getUserInfoResponse = LoadJsonUtil.getJSON(requestUrl);
        logger.info("-----------------------------");
        logger.info("请求2返回数据");
        logger.info(getTokenResponse);
        logger.info("-----------------------------");
        JSONObject jsonObjectUser = JSONObject.fromObject(getUserInfoResponse);
        String unionid = String.valueOf(jsonObjectUser.get("unionid"));

        logger.info(openid + "-----------" + access_token);
        logger.info(userId + "-----------" + unionid);

        //添加优惠券
        try {
            AcceptCouponResponse res = payController.acceptCoupon(userId.longValue(), unionid);
            logger.info(res.getStatus().getCode());
            logger.info(res.getStatus().getMsg());
            response.sendRedirect(env.getProperty("local.url") + "api/pay/shareSuccess.action");
        } catch (ApiNotFoundException e) {

            logger.info("-----------accept返回--------------");
            logger.info(e.getMessage());
            logger.info(e.getApicode());
            int apicode = e.getApicode();

            String redirecturl = env.getProperty("local.url") + "/api/pay/share.action?wxBackCode=" + apicode + "&userId=" + userId;
            if (apicode == 2) {
                response.sendRedirect(redirecturl);
            }
            if (apicode == 3) {
                response.sendRedirect(redirecturl);
            }
            if (apicode == 4) {
                response.sendRedirect(redirecturl);
            }
        }
    }


    /**
     * 自定义form
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/share.action", method = RequestMethod.GET)
    public String share(HttpServletRequest request, HttpServletResponse response,
                        ModelMap modelMap, Long userId, Integer wxBackCode) throws Exception {


//        userId = 18l;
        modelMap.put("userId", "" + userId);
        modelMap.put("wxBackCode", "" + wxBackCode);

        String acceptCouponUrl = env.getProperty("local.url") + "api/pay/acceptCoupon";
        String shareSuccessUrl = env.getProperty("local.url") + "api/pay/shareSuccess.action";
        String wxShare = env.getProperty("wxshareredirect") + "?userId=";
        logger.info(wxShare);
//        String wxShare =  "https://wx.storybook.ifenghui.com/wxshare?userId=";
        String wxShareUrl = URLEncoder.encode(wxShare, "utf-8");
        modelMap.put("acceptCouponUrl", acceptCouponUrl);
        modelMap.put("shareSuccessUrl", shareSuccessUrl);
        modelMap.put("wxShareUrl", wxShareUrl);

        return "share/share";
    }

    /**
     * 自定义form
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/shareSuccess.action", method = RequestMethod.GET)
    public String shareSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return "share/success";
    }

    @Autowired
    CouponGetRecordDao couponGetRecordDao;

    @Autowired
    UserAccountDao userAccountDao;
    /**
     * fen分享大使补充数据，用于修复损失内容
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/fixSharer.action", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse fixSharer(HttpServletRequest request, HttpServletResponse response, String phone, Integer userId, String createTime) throws Exception {
        BaseResponse response1 = new BaseResponse();


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        Date createTimeDD = format.parse(createTime);


        CouponGetRecord couponGetRecord = couponGetRecordDao.getByPhone(phone);

        if (couponGetRecord == null) {
            //添加领取记录
            CouponGetRecord cgr = new CouponGetRecord();
            cgr.setUserId(userId.longValue());
            cgr.setPhone(phone);
            cgr.setCreateTime(createTimeDD);
            cgr.setStatus(0);
            couponGetRecordDao.save(cgr);
        }
        //查询用户是否存在，如果存在增加关联，并在分享大使中体现
        User user = userService.getUserByPhone(phone);
        List<UserAccount> userAccounts = userAccountDao.findUserAccountsBySrcId(phone);
        if (user != null ) {
            userExtendService.editParentId(user.getId().intValue(), userId);
            return response1;
        }
        if(userAccounts.size()!=0){
            int userId1 = userAccounts.get(0).getUserId().intValue();
            userExtendService.editParentId(userId1, userId);
            return response1;
        }
        response1.getStatus().setMsg("user is null");
        return response1;
    }


    @RequestMapping(value="/vip_friend_card_page",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得亲友卡列表", notes = "获得亲友卡列表")
    GetVipFriendCardPageResponse getVipFriendCardPage (
            @RequestHeader("ssToken") @RequestParam() String token,
            @ApiParam(value = "pageNo")@RequestParam Integer pageNo,
            @ApiParam(value = "pageSize")@RequestParam Integer pageSize
    ) {
        GetVipFriendCardPageResponse response = new GetVipFriendCardPageResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        Page<VipFriendCard> vipFriendCardPage = vipFriendCardService.getVipFriendCardPageByUserId(userId.intValue(), pageNo, pageSize);
        response.setVipFriendCardList(vipFriendCardPage.getContent());
        response.setJpaPage(vipFriendCardPage);
        return response;
    }

    @CrossOrigin
    @RequestMapping(value="/gain_vip_friend_card",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "领取亲友卡", notes = "领取亲友卡")
    BaseResponse gainVipFriendCard(
            @ApiParam(value = "cardId")@RequestParam Integer cardId,
            @ApiParam(value = "srcType")@RequestParam Integer srcType,
            @ApiParam(value = "srcInfo")@RequestParam String srcInfo,
            @ApiParam(value = "nick") @RequestParam(required = false) String nick
    ) {
        BaseResponse response = new BaseResponse();
        UserAccountStyle userAccountStyle = UserAccountStyle.getById(srcType);
        vipFriendCardService.gainVipFriendCard(cardId, userAccountStyle, srcInfo, nick);
        return response;
    }

    @CrossOrigin
    @RequestMapping(value="/vip_friend_card_info",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "亲友卡信息", notes = "亲友卡信息")
    GetVipFriendCardInfoResponse getVipFriendCardInfo(
            @ApiParam(value = "cardId")@RequestParam Integer cardId
    ) {
        GetVipFriendCardInfoResponse response = new GetVipFriendCardInfoResponse();
        VipFriendCard vipFriendCard = vipFriendCardService.findOne(cardId);
        User user = userService.getUser(vipFriendCard.getUserId().longValue());
        String storyImg = configService.getConfigByKey("app_vip_share_gift").getVal();
        response.setVipFriendCard(vipFriendCard);
        response.setStoryImg(storyImg);
        response.setNick(user.getNick());
        response.setHeadImg(user.getAvatar());
        response.setVipFriendCard(vipFriendCard);
        return response;
    }

    @CrossOrigin
    @RequestMapping(value="/wxUnionId_info",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "亲友卡信息", notes = "亲友卡信息")
    GetWxUnionIdResponse getWxUnionId(
            @ApiParam(value = "code")@RequestParam String code
    ) {
        GetWxUnionIdResponse response = new GetWxUnionIdResponse();

        logger.info("--------方法开始-----------");
        String appid = "wx31edd56a45a12c7f";
        String secret = "6c683afad5338b3e54189a2a48091401";

        logger.info("--------appid = "+appid+"-----------");

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";

        logger.info("--------url = "+url+"-----------");

        String getTokenResponse = LoadJsonUtil.getJSON(url);
        // 获得 access_token  openid
        JSONObject jsonObject = JSONObject.fromObject(getTokenResponse);

        logger.info("-----------------------------");
        logger.info("请求返回数据");
        logger.info(jsonObject);
        logger.info("-----------------------------");
        //用户的唯一标识（openid）
        String openid = String.valueOf(jsonObject.get("openid"));
        String access_token = String.valueOf(jsonObject.get("access_token"));

        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&scope=snsapi_userinfo" + "&lang=zh_CN";
        String getUserInfoResponse = LoadJsonUtil.getJSON(requestUrl);
        logger.info("-----------------------------");
        logger.info("请求2返回数据");
        logger.info(getTokenResponse);
        logger.info("-----------------------------");
        JSONObject jsonObjectUser = JSONObject.fromObject(getUserInfoResponse);
        String unionId = String.valueOf(jsonObjectUser.get("unionid"));
        String nick = String.valueOf(jsonObjectUser.get("nickname"));
        response.setUnionId(unionId);
        response.setNick(nick);
        return response;
    }
}
