package com.ifenghui.storybookapi.app.presale.controller;




import com.ifenghui.storybookapi.adminapi.controlleradmin.code.CodeAdminController;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp.CodeTypeItem;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp.VipCodesResponse;
import com.ifenghui.storybookapi.app.presale.entity.*;
import com.ifenghui.storybookapi.app.presale.exception.PreSaleException;
import com.ifenghui.storybookapi.app.presale.exception.PreSaleExceptionResponse;
import com.ifenghui.storybookapi.app.presale.exception.PreSaleNotFoundException;
import com.ifenghui.storybookapi.app.presale.response.*;
import com.ifenghui.storybookapi.app.presale.service.*;
import com.ifenghui.storybookapi.app.presale.notify.PreSaleWeixinNotify;
import com.ifenghui.storybookapi.app.presale.service.PreSaleGiftCountService;

import com.ifenghui.storybookapi.app.presale.response.PreSaleGetUserResponse;

import com.ifenghui.storybookapi.app.presale.service.impl.PreSalePayServiceImpl;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.VipGoodsStyle;
import com.ifenghui.storybookapi.util.JS_SDK.RandomStringGenerator;
import com.ifenghui.storybookapi.util.JS_SDK.SHA1;
import com.ifenghui.storybookapi.util.JS_SDK.Token;
import com.ifenghui.storybookapi.util.JS_SDK.WXConfig;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.ifenghui.storybookapi.util.SetXMLUtil.setXML;


@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "预售商品", description = "预售商品")
@RequestMapping("/sale/pre_sale")
public class PreSaleController {


    @Value("${certPath}")
    public String certpath;

//    @Value("${saleconfig.oss.url}")
//    String ossUrl;

    @Value("${fwwxkey}")
    String wxkey;

    @Value("${sale.activity.notify}")
    String wxcallurl;

    @Value("${fwmch_id}")
    String wx_mch_id;

    @Value("${pre_sale_out_trade}")
    String outTradeNoPre;


    @Autowired
    PreSaleGoodsService preSaleGoodsService;

    @Autowired
    ActivityService activityService;

    @Autowired
    PreSaleGiftService preSaleGiftService;

    @Autowired
    PreSaleCodeService preSaleCodeService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    PreSalePayService preSalePayService;

    @Autowired
    PreSaleUserService preSaleUserService;

    @Autowired
    PreSalePayCallBackService preSalePayCallBackService;

    @Autowired
    Environment env;

    @Autowired
    PreSaleGiftCountService giftCountService;

//    @Autowired
//    RemoteAppApiService remoteAppApiService;

    @Autowired
    PreSaleGoodsService goodsService;

//    @Autowired
//    RemoteAdminRoleApiService remoteAdminRoleApiService;

    @Autowired
    CodeAdminController codeAdminController;

    @Autowired
    PreSalePayController preSalePayController;



    private static Logger logger = Logger.getLogger(PreSaleController.class);

    /**
     * 1 通过id获得商品
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/get_goods", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得商品", notes = "获得商品")
    PreSaleGoodsResponse getGoods(
            @ApiParam(value = "商品id") @RequestParam Integer goodsId
    ) {

//        PreSaleGoods.ossUrl = ossUrl;
        PreSaleGoodsResponse response = new PreSaleGoodsResponse();
        PreSaleGoods preSaleGoods = preSaleGoodsService.findGoodsById(goodsId);
        response.setPreSaleGoods(preSaleGoods);
        return response;
    }

    /**
     * 2、通过商品id创建订单，返回订单信息
     *
     * @param goodId
     * @return
     */
    @RequestMapping(value = "/add_pay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "生成订单", notes = "生成订单")
    PreSalePayResponse addPay(
            @ApiParam(value = "商品id") @RequestParam Integer goodId,
            @ApiParam(value = "用户id") @RequestParam Integer userId,
            @ApiParam(value = "活动id") @RequestParam(defaultValue = "1") Integer activityId

    ) {
        PreSaleUser user = preSaleUserService.findUserById(userId);
        return preSalePayController.addPay(user.getUnionId(),activityId,goodId,"");

//        WXConfig.wxcallurl = this.wxcallurl;
//        WXConfig.pre_sale_out_trade = this.outTradeNoPre;
////        WXConfig.certpath=this.certpath;
////        PreSalePayServiceImpl.certpath = certpath;
//        PreSalePayResponse response = new PreSalePayResponse();
//        // 微信key
//        String wxkey = env.getProperty("fwwxkey");
//        PreSaleGoods preSaleGoods = preSaleGoodsService.findGoodsById(goodId);
//        //商品价格
//        int price = preSaleGoods.getPrice();
//        //未完成状态订单
//        PreSalePay preSalePay = preSalePayService.add(userId, goodId, price, activityId, 0, OrderPayStyle.DEFAULT_NULL);
//        if (preSalePay == null) {
//            response.getStatus().setCode(3);
//            response.getStatus().setMsg("无法创建订单，活动已过期");
//            return response;
//        }
//        PreSaleUser user = preSaleUserService.findUserById(userId);
//        //用户openid
//        String openId = user.getOpenId();
//        //生成jsonObject
//        JSONObject jsonObject = preSalePayService.doUnifiedOrder(outTradeNoPre + preSalePay.getId(),
//                preSaleGoods.getContent(), preSaleGoods.getPrice(), preSaleGoods.getId(), request, openId, wxkey);
//        if (jsonObject == null) {
//            response.getStatus().setCode(0);
//            response.getStatus().setMsg("失败");
//        }
//        response.setJsonObject(jsonObject);
////        preSalePayService.doUnifiedOrder();
//        response.setPreSalePay(preSalePay);
//        return response;
    }

    /**
     * 微信回调接口
     *
     * @throws IOException
     * @throws PreSaleException
     */
    @RequestMapping(value = "/wxcallBack", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信支付回调", notes = "微信支付回调")
    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = PreSaleGetUserResponse.class)
            , @ApiResponse(code = 201, message = "没有找到这个用户", response = PreSaleExceptionResponse.class)})
    void wxpaycallBack(
    ) throws IOException, PreSaleException {

        logger.info("接收参数");
        logger.info("entry");
        //获取url判断回掉来源
//        appname = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);//获取url最后一个/的内容

        PreSaleWeixinNotify wxNotify = new PreSaleWeixinNotify(request);
        //获得微信服务器传递的信息
        logger.info("第一次日志-----=========orderid=========----" + wxNotify.getOrderId().intValue());

        logger.info("准备验签");
        //验签
        String appid;
        String wxkey;
        String mch_id;

        appid = env.getProperty("fwappid");
        wxkey = env.getProperty("fwwxkey");
        mch_id = env.getProperty("fwmch_id");

        logger.info("-----fwappid----" + appid);
        logger.info("-----fwwxkey----" + wxkey);
        logger.info("-----fwmch_id----" + mch_id);

        boolean flagSign = wxNotify.checkWeixinSign(wxNotify, appid, wxkey, mch_id);

        logger.info("=========orderid=========----" + wxNotify.getOrderId().intValue());

        if (!flagSign) {//验签失败
            logger.info("fail");
            response.getWriter().write(setXML("FAILURE", "444444"));
            return;
        }

        try {
            PreSalePay preSalePay = preSalePayService.findPayById(wxNotify.getOrderId().intValue());//获取此订单数据
            if (preSalePay == null) {
                throw new PreSaleNotFoundException("订单已删除");
            }
            PreSalePayCallBack checkRecord = preSalePayCallBackService.getPayCallbackRecordByPayId(preSalePay.getId());
            //存回调数据
            if (checkRecord == null) {
                PreSalePayCallBack payCallback = new PreSalePayCallBack();

                payCallback.setContent(wxNotify.getXmlDoc());
                payCallback.setPayId(wxNotify.getOrderId().intValue());
                payCallback.setCreateTime(new Date());
                preSalePayCallBackService.addPayCallback(payCallback);
            }

            // this.setOrderSuccess(wxNotify.getOrderId().intValue(), wxNotify.getPayAccount(), RechargePayStyle.WEIXINP_PAY, wxNotify.getXmlDoc(), wxNotify.getTradeNo(), "微信支付充值");
            this.setPayStatusSuccess(wxNotify.getOrderId().intValue(),OrderPayStyle.WEIXINP_PAY);
            this.addCode(wxNotify.getOrderId().intValue());
//            this.addExpress(wxNotify.getOrderId().intValue());

        } catch (PreSaleNotFoundException e) {
            e.printStackTrace();
            logger.info("--------------------wxpayNotify----payRechargeOrder--null---");
            logger.info("订单已删除");
            response.getWriter().write(setXML("FAILURE", "recharge order has delete!"));
            return;
        }

        logger.info("--------------------wxPayNotify-------success-");
        System.out.println("success");
        response.getWriter().write(setXML("SUCCESS", ""));
        return;
    }


    /**
     * 设置订单完成
     */
    private void setPayStatusSuccess(int payId,OrderPayStyle payStyle) throws PreSaleNotFoundException {
        //设置订单支付成功状态
        preSalePayService.setPayStatusSuccess(payId,payStyle);
    }

    /**
     * 生成兑换码
     */
    private void addCode(int payId) throws PreSaleNotFoundException {

        PreSalePay pay = preSalePayService.findPayById(payId);

        VipCodesResponse codesResponse= codeAdminController.getCodeTypes();
        PreSaleGoods goods = goodsService.findGoodsById(pay.getGoodsId());
        logger.info("add code payId::"+payId+" codeType:"+goods.getCodeType());
        CodeTypeItem codeType=null;
        for(CodeTypeItem codeTypeItem: codesResponse.getCodeTypeItems()){
            if(codeTypeItem.getId()==goods.getCodeType().intValue()){
                codeType=codeTypeItem;
                break;
            }
        }

        if(codeType != null) {
            Activity activity=activityService.findById(pay.getActivityId());

            PreSaleCode preSaleCode = codeAdminController.addCode(4, "微信活动:" + activity.getContent(), VipGoodsStyle.getById(goods.getCodeType()), pay.getActivityId()).getPreSaleCode();
            pay.setCode(preSaleCode.getCode());
            logger.info("set pay code:"+activity.getId()+":"+preSaleCode.getCode());
            preSalePayService.update(pay);
        }

    }

    /**
     * 生成礼品带领取信息
     */
    private void addExpress(int payId) throws PreSaleNotFoundException {
        PreSaleGift gift = preSaleGiftService.addGiftByPayId(payId);
        Activity activity = activityService.findById(gift.getActivityId());
        PreSaleGoods goods = preSaleGoodsService.findGoodsById(gift.getGoodsId());
        giftCountService.addItem(gift.getUserId(),2, gift.getId(), gift.getActivityId(), activity.getContent() + goods.getContent());

    }

    @RequestMapping(value = "/get_pay", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看订单信息", notes = "查看订单信息")
    PreSalePayResponse getPay(
            @ApiParam(value = "订单id") @RequestParam Integer payId
    ) {

        PreSalePayResponse response = new PreSalePayResponse();
        PreSalePay preSalePay = preSalePayService.findPayById(payId);
        response.setPreSalePay(preSalePay);
        return response;
    }


    @RequestMapping(value = "/set_address", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "填写地址信息", notes = "填写地址信息")
    PreSaleGiftResponse setAddress(
            @ApiParam(value = "订单id") @RequestParam Integer payId,
            @ApiParam(value = "收件人") @RequestParam String receiver,
            @ApiParam(value = "联系电话") @RequestParam String phone,
            @ApiParam(value = "收件地址") @RequestParam String address,
            @ApiParam(value = "活动id") @RequestParam(defaultValue = "1") Integer activityId
    ) {

        PreSaleGiftResponse response = new PreSaleGiftResponse();
        PreSaleGift giftByPayId = preSaleGiftService.getGiftByPayId(payId);
        PreSalePay preSalePay=preSalePayService.findPayById(payId);
        Activity activity=activityService.findById(preSalePay.getActivityId());
        if (giftByPayId != null && giftByPayId.getStatus() == 1) {
            response.getStatus().setCode(2);
            response.getStatus().setMsg("已经领取");
            return response;
        }
        PreSaleGift preSaleGift = preSaleGiftService.setAddress(payId, receiver, phone, address);
        response.setPreSaleGift(preSaleGift);

        //导出到物流中心
//        remoteAdminRoleApiService.addCenterOrder(preSaleGift.getName()
//                ,"SALE_ACTIVITY"
//                ,preSalePay.getSuccessTime()
//                ,"活动导入"+activity.getContent()
//                ,1
//                ,payId+""
//                ,preSaleGift.getPhone()
//                ,preSaleGift.getReceiver()
//                ,""
//                ,""
//                ,""
//                ,preSaleGift.getAddress()
//                ,23
//                ,0
//        );

//        preSalePay.setIsExpressCenter(1);
        preSalePayService.update(preSalePay);

        return response;
    }

    @RequestMapping(value = "/set_express", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "填写物流信息", notes = "填写物流信息")
    PreSaleGiftResponse setExpress(
            @ApiParam(value = "订单id") @RequestParam Integer payId,
            @ApiParam(value = "物流公司") @RequestParam String express_company_name,
            @ApiParam(value = "快递单号") @RequestParam String express_code,
            @ApiParam(value = "发货状态") @RequestParam Integer express_status
    ) {
        PreSaleGiftResponse response = new PreSaleGiftResponse();
        PreSaleGift preSaleGift = preSaleGiftService.setExpress(payId, express_company_name, express_code, express_status);
        response.setPreSaleGift(preSaleGift);
        return response;
    }


    @RequestMapping(value = "/get_gift_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "实物领取展示列表", notes = "实物领取展示列表")
    PreSaleGiftListResponse getGiftList(
            @ApiParam(value = "用户id") @RequestParam(required = true) Integer userId,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize

    ) {

        PreSaleGiftListResponse response = new PreSaleGiftListResponse();
        if (userId != null) {

            Page<PreSaleGift> giftPage = preSaleGiftService.findGiftListByUserId(userId, pageNo, pageSize);
            response.setJpaPage(giftPage);
            response.setPreSaleGiftList(giftPage.getContent());
            return response;
        } else {
            response.getStatus().setCode(2);
            response.getStatus().setMsg("userId不能为空");
            return response;
        }
    }


    @RequestMapping(value = "/get_code_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得兑换码列表", notes = "获得兑换码列表")
    PreSaleCodeListResponse getCodeList(
            @ApiParam(value = "兑换码id") @RequestParam Integer userId,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) {

        PreSaleCodeListResponse response = new PreSaleCodeListResponse();
        if (userId != null) {
            Page<PreSaleCode> codePage = preSaleCodeService.getCodeListByUserId(userId, pageNo, pageSize);
            response.setJpaPage(codePage);
            response.setCodeList(codePage.getContent());
            return response;
        } else {
            response.getStatus().setCode(2);
            response.getStatus().setMsg("userId不能为空");
            return response;
        }
    }


    @RequestMapping(value = "/add_user", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "注册用户", notes = "注册用户")
    PreSaleGetUserResponse addUser(
            @ApiParam(value = "code") @RequestParam String code
    ) {

        PreSaleGetUserResponse response = new PreSaleGetUserResponse();
        PreSaleUser preSaleUser = null;
        try {
            preSaleUser = preSaleUserService.addUser(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setPreSaleUser(preSaleUser);
        return response;
    }

    @RequestMapping(value = "/get_code", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得兑换码", notes = "获得兑换码")
    PreSaleCodeResponse getCode(
            @ApiParam(value = "订单id") @RequestParam Integer payId
    ) {

        PreSaleCodeResponse response = new PreSaleCodeResponse();
        PreSaleCode preSaleCode = preSaleCodeService.getCodeByPayId(payId);
        response.setCode(preSaleCode);
        return response;
    }


    @RequestMapping(value = "/get_gift_detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得礼物领取详情", notes = "获得礼物领取详情")
    PreSaleGiftResponse getGiftDetail(
            @ApiParam(value = "礼物id") @RequestParam Integer Id
    ) {

        PreSaleGiftResponse response = new PreSaleGiftResponse();
        PreSaleGift preSaleGift = preSaleGiftService.getGiftById(Id);
        response.setPreSaleGift(preSaleGift);
        return response;
    }

    @RequestMapping(value = "/get_wx_data", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得微信分享所需数据", notes = "获得微信分享所需数据")
    WxJsonResponse getWxData(
            @ApiParam(value = "当前页面url") @RequestParam String url
    ) {

        WxJsonResponse response = new WxJsonResponse();
        try {

            String[] strings;
            String uri;
            if (url.contains("#")) {
                strings = url.split("#");
                uri = strings[0];
            } else {
                uri = url;
            }
            String ticket = Token.getTicket();
            String noncestr = RandomStringGenerator.getRandomStringByLength(32);
            long timestamp = System.currentTimeMillis() / 1000;
            String sign = "jsapi_ticket=" + ticket + "&noncestr="//请勿更换字符组装顺序
                    + noncestr + "&timestamp=" + timestamp
                    + "&url=" + uri;
            String signature = new SHA1().getDigestOfString(sign.getBytes("utf-8"));
            System.out.println(url);
            System.out.println("url:" + uri);
            System.out.println(sign);
            System.out.println(signature);
            response.setAppId(WXConfig.appid);
            response.setTimestamp(timestamp);
            response.setNonceStr(noncestr);
            response.setSignature(signature);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.getStatus().setCode(2);
            response.getStatus().setMsg(e.getMessage());
            return response;
        }
    }



}
