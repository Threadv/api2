package com.ifenghui.storybookapi.app.shop.controller;

import com.ifenghui.storybookapi.api.notify.WeixinNotify;
import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.*;
import com.ifenghui.storybookapi.app.shop.response.*;
import com.ifenghui.storybookapi.app.shop.service.*;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.response.GetUserResponse;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.AlipayNotify;
import com.ifenghui.storybookapi.util.NumberUtil;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@EnableAutoConfiguration
@Api(value = "飞船商城订单接口", description = "飞船商城订单接口")
@RequestMapping("/api/shop/order")
public class ShopOrderController {

    Logger logger = Logger.getLogger(ShopOrderController.class);

    @Autowired
    WalletService walletService;

    @Autowired
    ShopGoodsPriceService shopGoodsPriceService;

    @Autowired
    ShopGoodsService shopGoodsService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    WeiXinOrderService weiXinOrderService;

    @Autowired
    ShopExpressService shopExpressService;

    @Autowired
    ShopOrderService shopOrderService;

    @Autowired
    UserService userService;

    @Autowired
    ShopUserAddressService shopUserAddressService;

    @Autowired
    ShopPayCallBackService shopPayCallBackService;

    @Autowired
    private Environment env;

    @CrossOrigin
    @RequestMapping(value = "/shopWxPayNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "飞船商城微信支付回调地址", notes = "飞船商城微信支付回调地址")
    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "没有找到这个用户", response = ExceptionResponse.class)})
    void wxpayNotify(
    ) throws IOException, ApiException {
        logger.info("--- start shopwxpaynotify ---");
        String prefix = MyEnv.env.getProperty("shopOrder.prefix");
        WeixinNotify wxNotify = new WeixinNotify(request, prefix);
        String appid = MyEnv.env.getProperty("appid");
        String wxkey = MyEnv.env.getProperty("wxkey");
        String mch_id = MyEnv.env.getProperty("mch_id");
        boolean flagSign = wxNotify.checkWeixinSign(wxNotify, appid, wxkey, mch_id);
        logger.info("flagSign" + flagSign);
        if (flagSign) {
            int orderId = wxNotify.getOrderId().intValue();
            shopPayCallBackService.addShopPayCallBackRecord(wxNotify.getXmlDoc(), RechargePayStyle.WEIXINP_PAY, orderId);
            shopOrderService.updateShopPayOrderSuccess(orderId, OrderPayStyle.WEIXINP_PAY);
        }
    }

    /**
     * 商城支付宝支付回调地址
     *
     * @throws Exception
     */
    @RequestMapping(value = "/shopAliPayNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "支付宝支付回调地址", notes = "支付宝支付回调地址")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功", response = GetUserResponse.class),
            @ApiResponse(code = 201, message = "没有找到这个用户", response = ExceptionResponse.class)
    })
    void shopAliNotify() throws Exception {
        String alipayPublicKey = env.getProperty("alipay_public_key");
        boolean verifyStatus = AlipayNotify.rsaAliPayCheckV1(alipayPublicKey, request);
        if (verifyStatus) {
            String orderPrefix = env.getProperty("shopOrder.prefix");
            AlipayNotify alipayNotify = new AlipayNotify(request, orderPrefix);
            if (alipayNotify.getTradeSuccess()) {
                String callBackMsg = alipayNotify.getCallbackMsg();
                Integer orderId = alipayNotify.getOrderId().intValue();
                shopPayCallBackService.addShopPayCallBackRecord(callBackMsg, RechargePayStyle.ALI_PAY, orderId);
                shopOrderService.updateShopPayOrderSuccess(orderId, OrderPayStyle.ALI_PAY);
            }
        }
    }

    @RequestMapping(value = "/shopAliPayReturn", method = RequestMethod.GET)
    void shopAliReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String successReturnUrl = env.getProperty("shopOrder.success.url");
        String alipayPublicKey = env.getProperty("alipay_public_key");
        boolean verifyStatus = AlipayNotify.rsaAliPayCheckV1(alipayPublicKey, request);
        if (verifyStatus) {
            String orderPrefix = env.getProperty("shopOrder.prefix");
            AlipayNotify alipayNotify = new AlipayNotify(request, orderPrefix);
            Integer orderId = alipayNotify.getOrderId().intValue();
            response.sendRedirect(successReturnUrl + orderId);
        }
    }

    @CrossOrigin
    @ApiOperation(value = "支付宝支付")
    @RequestMapping(value = "/add_ali_order", method = RequestMethod.GET)
    void addAliOrder(
            @ApiParam(value = "token") @RequestParam() String token,
            @ApiParam(value = "订单id") @RequestParam() Integer orderId

    ) throws ApiException {

        Long userId = userService.checkAndGetCurrentUserId(token);
        try {
            shopOrderService.addAliOrder(orderId, userId.intValue());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @CrossOrigin
    @ApiOperation(value = "微信支付")
    @RequestMapping(value = "/add_weixin_order", method = RequestMethod.GET)
    @ResponseBody
    WeixinMwebUrlResponse addWeiXinOrder(
            @ApiParam(value = "token") @RequestParam() String token,
            @ApiParam(value = "订单id") @RequestParam() Integer orderId
    ) throws ApiException, Exception {

        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        WeixinMwebUrlResponse response = new WeixinMwebUrlResponse();
        ShopOrder shopOrder = shopOrderService.findOrderById(orderId);
        if (shopOrder.getUserId() != userId.intValue()) {
            response.getStatus().setMsg("非此用户订单");
            return response;
        }
        //微信下单支付
        String appid = MyEnv.env.getProperty("appid");
        String mch_id = MyEnv.env.getProperty("mch_id");
        String wxkey = env.getProperty("wxkey");
        String callBackUrl = env.getProperty("shopWeixinPay.notify");
        String outTradeNo = env.getProperty("shopOrder.prefix") + "_" + shopOrder.getId();

        String wapUrl = env.getProperty("shopWechatPayWapUrl");
        String sceneInfo = "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"" + wapUrl + "\",\"wap_name\": \"故事飞船商城\"}}";

        JSONObject jsonObject = weiXinOrderService.doUnifiedOrder(outTradeNo, "故事飞船商城订单", shopOrder.getTotalAmount(), shopOrder.getGoodsId().toString(), request, null, wxkey, callBackUrl, "MWEB", sceneInfo, appid, mch_id);
        Map jsonMap = JSONObject.fromObject(jsonObject);
        String webUrl = jsonMap.get("mweb_url").toString();
        String redirect = env.getProperty("shopOrder.success.url") + shopOrder.getId();
        String wechatPayUrl = webUrl + "&redirect_url=" + URLEncoder.encode(redirect, "utf-8");
        response.setWebUrl(wechatPayUrl);
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "生成订单")
    @RequestMapping(value = "/add_order", method = RequestMethod.POST)
    @ResponseBody
    ShopOrderResponse addOrder(
            @ApiParam(value = "token") @RequestParam() String token,
            @ApiParam(value = "商品id") @RequestParam() Integer goodsId,
            @ApiParam(value = "收件人") @RequestParam(required = false) String receiver,
            @ApiParam(value = "电话") @RequestParam(required = false) String phone,
            @ApiParam(value = "地址") @RequestParam(required = false) String address,
            @ApiParam(value = "area") @RequestParam(required = false) String area,
            @ApiParam(value = "商品数量") @RequestParam() Integer num,
            @ApiParam(value = "价格id") @RequestParam() Integer priceId
    ) throws ApiException {

        ShopOrderResponse response = new ShopOrderResponse();

        if (num <= 0) {
            throw new ApiNotFoundException("购买数量必须大于0！");
        }

        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        User user = userService.getUser(userId);
        ShopGoods goods = shopGoodsService.findById(goodsId);
        String channel = "";
        if (user.getChannel() != null) {
            channel = user.getChannel();
        }
        //生成订单
        ShopOrder shopOrder = shopOrderService.addOrder(userId.intValue(), goodsId, num, priceId, user.getIsTest(), channel, "", OrderPayStyle.DEFAULT_NULL);
        //扣除星星值
        if (shopOrder.getTotalStar() > 0) {
            //intro  飞船商城购买扣除星星值  购买商城商品
            walletService.addStarToWallet(userId.intValue(), StarRechargeStyle.STAR_SHOP_BUY_GOODS, NumberUtil.unAbs(shopOrder.getTotalStar()), StarContentStyle.SHOP_BUY.getName());
        }
        if (goods.getType() == 0) {//商品类型 实物类型 添加物流信息
            if (address == null || address.equals("")) {
                throw new ApiNotFoundException("地址不能为空！");
            }
            if (area == null || area.equals("")) {
                throw new ApiNotFoundException("地址不能为空！");
            }
            if (phone == null || phone.equals("")) {
                throw new ApiNotFoundException("电话号码不能为空！");
            }
            if (receiver == null || receiver.equals("")){
                throw new ApiNotFoundException("联系人不能为空！");
            }
            shopExpressService.addExpress(shopOrder.getId(), receiver, phone, address, area, ExpressStyle.DEFAULT_NULL, "", ExpressStatusStyle.HAS_NO_DELIVERY, OrderStyle.STAR_SHOP_ORDER);
        }
        ShopGoodsPrice shopGoodsPrice = shopGoodsPriceService.findPriceById(priceId);
        if (shopGoodsPrice.getType() == GoodsPriceStyle.STAR_PRICE.getId()) {//商品价格类型 纯星星值类型
            //添加故事券 代金券
            shopOrderService.addExchangeCoupon(userId.intValue(), goodsId, num);
            //修改订单
            shopOrderService.updateShopPayOrderSuccess(shopOrder.getId(), OrderPayStyle.CODE);
        }

        response.setShopOrder(shopOrder);
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "取消订单")
    @RequestMapping(value = "/cancel_order", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse cancelOrder(
            @ApiParam(value = "订单id") @RequestParam() Integer id
    ) {

        BaseResponse response = new BaseResponse();
        //取消订单
        ShopOrder shopOrder = shopOrderService.cancelOrder(id);
        //返还星星值
        if (shopOrder.getTotalStar() > 0) {
            //商城取消订单返还星星值
            walletService.addStarToWallet(shopOrder.getUserId(), StarRechargeStyle.STAR_SHOP_BACK, shopOrder.getTotalStar(), StarContentStyle.SHOP_BACK.getName());
        }
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "删除订单")
    @RequestMapping(value = "/delete_order", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse deleteOrder(
            @ApiParam(value = "订单id") @RequestParam() Integer id
    ) {

        BaseResponse response = new BaseResponse();

        shopOrderService.deleteOrder(id);
        return response;
    }


    @CrossOrigin
    @ApiOperation(value = "我的订单-全部 -待支付 - 已完成")
    @RequestMapping(value = "/get_order_list", method = RequestMethod.GET)
    @ResponseBody
    ShopOrderListResponse getOrderList(
            @ApiParam(value = "token") @RequestParam() String token,
            @ApiParam(value = "状态 0 待支付 1 已完成 2 全部") @RequestParam() Integer status,
            @ApiParam(value = "pageNo") @RequestParam() Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam() Integer pageSize
    ) throws ApiException {


        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }

        ShopOrderListResponse response = shopOrderService.getMyOrderList(userId.intValue(), status, pageNo, pageSize);

        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "查看物流")
    @RequestMapping(value = "/get_express", method = RequestMethod.GET)
    @ResponseBody
    GetExpressResponse getExpress(
            @ApiParam(value = "订单id") @RequestParam() Integer orderId
    ) throws ApiException {

        GetExpressResponse response = shopExpressService.findExpressByOrderId(orderId, OrderStyle.STAR_SHOP_ORDER);

        return response;
    }


    @CrossOrigin
    @ApiOperation(value = "单个订单")
    @RequestMapping(value = "/get_order", method = RequestMethod.GET)
    @ResponseBody
    ShopOrderResponse getOrder(
            @ApiParam(value = "订单id") @RequestParam() Integer orderId
    ) throws ApiException {

        ShopOrderResponse response = new ShopOrderResponse();

        ShopOrder order = shopOrderService.findOrderById(orderId);
        ShopGoods goods = shopGoodsService.findById(order.getGoodsId());
        ShopGoodsPrice price = shopGoodsPriceService.findPriceById(order.getPriceId());
        order.setShopGoods(goods);
        order.setShopGoodsPrice(price);
        order.setOrderCode(order.getId().toString());

        response.setShopOrder(order);
        return response;
    }


    @CrossOrigin
    @ApiOperation(value = "测试支付宝订单")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    void test(
            @ApiParam(value = "订单id") @RequestParam() Integer orderId,
            @ApiParam(value = "subject") @RequestParam() String subject,
            HttpServletResponse response
    ) throws ApiException {
        try {
            shopOrderService.initAliWapPay(orderId, subject, "0.01", "BODY", response);
        } catch (Exception e) {
        }
    }


    /**
     * 定时任务
     * 每一分钟执行一次
     * 新版本已经不再使用星乐园，注释掉,2018-10-9
     */
//    @Scheduled(cron = "*/5 * * * * ?")
//    @Scheduled(cron = "0 */1 * * * ?")
    void timerig() throws ApiNotTokenException {

        try {
            List<ShopOrder> allOrder = shopOrderService.findAllOrder();
            for (ShopOrder o : allOrder) {
                if (new Date().compareTo(o.getExpireTime()) > 0) {
                    //取消订单
                    ShopOrder shopOrder = shopOrderService.cancelOrder(o.getId());
                    //返还星星值
                    if (shopOrder.getTotalStar() > 0) {
                        walletService.addStarToWallet(shopOrder.getUserId(), StarRechargeStyle.STAR_SHOP_BACK, shopOrder.getTotalStar(), StarContentStyle.SHOP_BACK.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
