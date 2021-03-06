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


@CrossOrigin(origins = "*", maxAge = 3600)//??????????????????????????????
@Controller
@EnableAutoConfiguration
@Api(value = "????????????", description = "????????????")
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
     * 1 ??????id????????????
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/get_goods", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "????????????", notes = "????????????")
    PreSaleGoodsResponse getGoods(
            @ApiParam(value = "??????id") @RequestParam Integer goodsId
    ) {

//        PreSaleGoods.ossUrl = ossUrl;
        PreSaleGoodsResponse response = new PreSaleGoodsResponse();
        PreSaleGoods preSaleGoods = preSaleGoodsService.findGoodsById(goodsId);
        response.setPreSaleGoods(preSaleGoods);
        return response;
    }

    /**
     * 2???????????????id?????????????????????????????????
     *
     * @param goodId
     * @return
     */
    @RequestMapping(value = "/add_pay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????", notes = "????????????")
    PreSalePayResponse addPay(
            @ApiParam(value = "??????id") @RequestParam Integer goodId,
            @ApiParam(value = "??????id") @RequestParam Integer userId,
            @ApiParam(value = "??????id") @RequestParam(defaultValue = "1") Integer activityId

    ) {
        PreSaleUser user = preSaleUserService.findUserById(userId);
        return preSalePayController.addPay(user.getUnionId(),activityId,goodId,"");

//        WXConfig.wxcallurl = this.wxcallurl;
//        WXConfig.pre_sale_out_trade = this.outTradeNoPre;
////        WXConfig.certpath=this.certpath;
////        PreSalePayServiceImpl.certpath = certpath;
//        PreSalePayResponse response = new PreSalePayResponse();
//        // ??????key
//        String wxkey = env.getProperty("fwwxkey");
//        PreSaleGoods preSaleGoods = preSaleGoodsService.findGoodsById(goodId);
//        //????????????
//        int price = preSaleGoods.getPrice();
//        //?????????????????????
//        PreSalePay preSalePay = preSalePayService.add(userId, goodId, price, activityId, 0, OrderPayStyle.DEFAULT_NULL);
//        if (preSalePay == null) {
//            response.getStatus().setCode(3);
//            response.getStatus().setMsg("????????????????????????????????????");
//            return response;
//        }
//        PreSaleUser user = preSaleUserService.findUserById(userId);
//        //??????openid
//        String openId = user.getOpenId();
//        //??????jsonObject
//        JSONObject jsonObject = preSalePayService.doUnifiedOrder(outTradeNoPre + preSalePay.getId(),
//                preSaleGoods.getContent(), preSaleGoods.getPrice(), preSaleGoods.getId(), request, openId, wxkey);
//        if (jsonObject == null) {
//            response.getStatus().setCode(0);
//            response.getStatus().setMsg("??????");
//        }
//        response.setJsonObject(jsonObject);
////        preSalePayService.doUnifiedOrder();
//        response.setPreSalePay(preSalePay);
//        return response;
    }

    /**
     * ??????????????????
     *
     * @throws IOException
     * @throws PreSaleException
     */
    @RequestMapping(value = "/wxcallBack", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @ApiResponses({@ApiResponse(code = 1, message = "??????", response = PreSaleGetUserResponse.class)
            , @ApiResponse(code = 201, message = "????????????????????????", response = PreSaleExceptionResponse.class)})
    void wxpaycallBack(
    ) throws IOException, PreSaleException {

        logger.info("????????????");
        logger.info("entry");
        //??????url??????????????????
//        appname = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);//??????url????????????/?????????

        PreSaleWeixinNotify wxNotify = new PreSaleWeixinNotify(request);
        //????????????????????????????????????
        logger.info("???????????????-----=========orderid=========----" + wxNotify.getOrderId().intValue());

        logger.info("????????????");
        //??????
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

        if (!flagSign) {//????????????
            logger.info("fail");
            response.getWriter().write(setXML("FAILURE", "444444"));
            return;
        }

        try {
            PreSalePay preSalePay = preSalePayService.findPayById(wxNotify.getOrderId().intValue());//?????????????????????
            if (preSalePay == null) {
                throw new PreSaleNotFoundException("???????????????");
            }
            PreSalePayCallBack checkRecord = preSalePayCallBackService.getPayCallbackRecordByPayId(preSalePay.getId());
            //???????????????
            if (checkRecord == null) {
                PreSalePayCallBack payCallback = new PreSalePayCallBack();

                payCallback.setContent(wxNotify.getXmlDoc());
                payCallback.setPayId(wxNotify.getOrderId().intValue());
                payCallback.setCreateTime(new Date());
                preSalePayCallBackService.addPayCallback(payCallback);
            }

            // this.setOrderSuccess(wxNotify.getOrderId().intValue(), wxNotify.getPayAccount(), RechargePayStyle.WEIXINP_PAY, wxNotify.getXmlDoc(), wxNotify.getTradeNo(), "??????????????????");
            this.setPayStatusSuccess(wxNotify.getOrderId().intValue(),OrderPayStyle.WEIXINP_PAY);
            this.addCode(wxNotify.getOrderId().intValue());
//            this.addExpress(wxNotify.getOrderId().intValue());

        } catch (PreSaleNotFoundException e) {
            e.printStackTrace();
            logger.info("--------------------wxpayNotify----payRechargeOrder--null---");
            logger.info("???????????????");
            response.getWriter().write(setXML("FAILURE", "recharge order has delete!"));
            return;
        }

        logger.info("--------------------wxPayNotify-------success-");
        System.out.println("success");
        response.getWriter().write(setXML("SUCCESS", ""));
        return;
    }


    /**
     * ??????????????????
     */
    private void setPayStatusSuccess(int payId,OrderPayStyle payStyle) throws PreSaleNotFoundException {
        //??????????????????????????????
        preSalePayService.setPayStatusSuccess(payId,payStyle);
    }

    /**
     * ???????????????
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

            PreSaleCode preSaleCode = codeAdminController.addCode(4, "????????????:" + activity.getContent(), VipGoodsStyle.getById(goods.getCodeType()), pay.getActivityId()).getPreSaleCode();
            pay.setCode(preSaleCode.getCode());
            logger.info("set pay code:"+activity.getId()+":"+preSaleCode.getCode());
            preSalePayService.update(pay);
        }

    }

    /**
     * ???????????????????????????
     */
    private void addExpress(int payId) throws PreSaleNotFoundException {
        PreSaleGift gift = preSaleGiftService.addGiftByPayId(payId);
        Activity activity = activityService.findById(gift.getActivityId());
        PreSaleGoods goods = preSaleGoodsService.findGoodsById(gift.getGoodsId());
        giftCountService.addItem(gift.getUserId(),2, gift.getId(), gift.getActivityId(), activity.getContent() + goods.getContent());

    }

    @RequestMapping(value = "/get_pay", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    PreSalePayResponse getPay(
            @ApiParam(value = "??????id") @RequestParam Integer payId
    ) {

        PreSalePayResponse response = new PreSalePayResponse();
        PreSalePay preSalePay = preSalePayService.findPayById(payId);
        response.setPreSalePay(preSalePay);
        return response;
    }


    @RequestMapping(value = "/set_address", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    PreSaleGiftResponse setAddress(
            @ApiParam(value = "??????id") @RequestParam Integer payId,
            @ApiParam(value = "?????????") @RequestParam String receiver,
            @ApiParam(value = "????????????") @RequestParam String phone,
            @ApiParam(value = "????????????") @RequestParam String address,
            @ApiParam(value = "??????id") @RequestParam(defaultValue = "1") Integer activityId
    ) {

        PreSaleGiftResponse response = new PreSaleGiftResponse();
        PreSaleGift giftByPayId = preSaleGiftService.getGiftByPayId(payId);
        PreSalePay preSalePay=preSalePayService.findPayById(payId);
        Activity activity=activityService.findById(preSalePay.getActivityId());
        if (giftByPayId != null && giftByPayId.getStatus() == 1) {
            response.getStatus().setCode(2);
            response.getStatus().setMsg("????????????");
            return response;
        }
        PreSaleGift preSaleGift = preSaleGiftService.setAddress(payId, receiver, phone, address);
        response.setPreSaleGift(preSaleGift);

        //?????????????????????
//        remoteAdminRoleApiService.addCenterOrder(preSaleGift.getName()
//                ,"SALE_ACTIVITY"
//                ,preSalePay.getSuccessTime()
//                ,"????????????"+activity.getContent()
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
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    PreSaleGiftResponse setExpress(
            @ApiParam(value = "??????id") @RequestParam Integer payId,
            @ApiParam(value = "????????????") @RequestParam String express_company_name,
            @ApiParam(value = "????????????") @RequestParam String express_code,
            @ApiParam(value = "????????????") @RequestParam Integer express_status
    ) {
        PreSaleGiftResponse response = new PreSaleGiftResponse();
        PreSaleGift preSaleGift = preSaleGiftService.setExpress(payId, express_company_name, express_code, express_status);
        response.setPreSaleGift(preSaleGift);
        return response;
    }


    @RequestMapping(value = "/get_gift_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    PreSaleGiftListResponse getGiftList(
            @ApiParam(value = "??????id") @RequestParam(required = true) Integer userId,
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
            response.getStatus().setMsg("userId????????????");
            return response;
        }
    }


    @RequestMapping(value = "/get_code_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????????????????????", notes = "?????????????????????")
    PreSaleCodeListResponse getCodeList(
            @ApiParam(value = "?????????id") @RequestParam Integer userId,
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
            response.getStatus().setMsg("userId????????????");
            return response;
        }
    }


    @RequestMapping(value = "/add_user", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????", notes = "????????????")
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
    @ApiOperation(value = "???????????????", notes = "???????????????")
    PreSaleCodeResponse getCode(
            @ApiParam(value = "??????id") @RequestParam Integer payId
    ) {

        PreSaleCodeResponse response = new PreSaleCodeResponse();
        PreSaleCode preSaleCode = preSaleCodeService.getCodeByPayId(payId);
        response.setCode(preSaleCode);
        return response;
    }


    @RequestMapping(value = "/get_gift_detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    PreSaleGiftResponse getGiftDetail(
            @ApiParam(value = "??????id") @RequestParam Integer Id
    ) {

        PreSaleGiftResponse response = new PreSaleGiftResponse();
        PreSaleGift preSaleGift = preSaleGiftService.getGiftById(Id);
        response.setPreSaleGift(preSaleGift);
        return response;
    }

    @RequestMapping(value = "/get_wx_data", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????????????????")
    WxJsonResponse getWxData(
            @ApiParam(value = "????????????url") @RequestParam String url
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
            String sign = "jsapi_ticket=" + ticket + "&noncestr="//??????????????????????????????
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
