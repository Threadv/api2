package com.ifenghui.storybookapi.app.transaction.controller;

import com.ifenghui.storybookapi.app.transaction.response.*;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.transaction.entity.goods.ExchangeRecord;
import com.ifenghui.storybookapi.app.transaction.entity.goods.Goods;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.app.transaction.service.StarShopService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/starshop")
@Api(value="星乐园积分商城",description = "星乐园相关,2.3已不再支持")
public class StarShopController {

    @Autowired
    UserService userService;

    @Autowired
    StarShopService starShopService;

    @Autowired
    WalletService walletService;
    @Autowired
    HttpServletRequest request;
    private static Logger logger = Logger.getLogger(StarShopController.class);
    @Deprecated
    @RequestMapping(value="/getGoodsPage",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "商品列表", notes = "")
    GetGoodsPageResponse getGoodsPage(
            @ApiParam(value = "用户token",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  token,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
    ) throws ApiException {

        Long userId;
        Integer starCount = 0;
        if(token == null || token.length() <= 0){
            userId = 0L;
        }else{
            userId = userService.checkAndGetCurrentUserId(token);
            //获取钱包星值
            Wallet wallet = walletService.getWalletByUserId(userId);
            if(wallet != null){
                starCount = wallet.getStarCount();
            }
        }
        String ver;
        String userAgent=request.getHeader("User-Agent");
        logger.info("--------------------getGoodsPage--------userAgent----"+userAgent);
        String[] agentArr = userAgent.split(":");

        if (userAgent.indexOf("ios") != -1) {
            //ios
            logger.info("--------------------getGoodsPage--------ios----"+userAgent);
            ver = agentArr[3];
        }else if(userAgent.indexOf("Android") != -1){
            //安卓
            ver = agentArr[0].split("_")[1];
//            ver = "1.5.0";
        }else{
            ver = "";
        }

        GetGoodsPageResponse response = new GetGoodsPageResponse();
        response.setStarCount(starCount);
        Page<Goods> goodsPage = starShopService.getGoodsPage(pageNo,pageSize,ver);
        response.setGoodsList(goodsPage.getContent());
        response.setJpaPage(goodsPage);
        return response;
    }
    @Deprecated
    @RequestMapping(value="/getGoodsById",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "商品详情", notes = "")
    GetGoodsResponse getGoodsById(
            @ApiParam(value = "用户token",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  token,
            @ApiParam(value = "商品ID")@RequestParam Long goodsId
    ) throws ApiException {

        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        }else{
            userId = userService.checkAndGetCurrentUserId(token);
        }

        GetGoodsResponse response = new GetGoodsResponse();
        Goods goods = starShopService.getGoodsById(goodsId);
        response.setGoods(goods);
        return response;
    }
    @Deprecated
    @RequestMapping(value="/shop_goods_intro",method = RequestMethod.GET)
//    @ResponseBody
    @ApiOperation(value = "商品介绍详情", notes = "")
    void getGoodsIntroById(
            @ApiParam(value = "商品ID")@RequestParam Long goodsId,
            HttpServletResponse httpServletResponse
    ) throws ApiException {

        GetGoodsResponse response = new GetGoodsResponse();
        Goods goods = starShopService.getGoodsById(goodsId);
        try {
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.getWriter().print("<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            httpServletResponse.getWriter().print("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1.0, maximum-scale=1,user-scalable=yes\" />");
            httpServletResponse.getWriter().print("<meta name=\"format-detection\" content=\"telephone=no\" />");
            httpServletResponse.getWriter().print("<style>p{width:100%;display:block;margin:auto;font-size: 14px;color: #666666;margin: 0px auto;line-height: 21px;}img{display:block;margin-bottom:0px;width:100%;}body{margin:0px;}</style></head><body>");
            httpServletResponse.getWriter().print(goods.getGoodsIntro());
            httpServletResponse.getWriter().print("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return response;
    }



    @Deprecated
    @RequestMapping(value="/getExchangeRecordPageByUserId",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取兑换记录", notes = "")
    GetExchangeRecordPageResponse getExchangeRecordPageByUserId(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
    ) throws ApiNotTokenException {

        Long userId;
        try{
            userId= userService.checkAndGetCurrentUserId(token);
        }catch (ApiNotTokenException e){
            e.printStackTrace();
            userId=-1L;
        }

        GetExchangeRecordPageResponse response = new GetExchangeRecordPageResponse();
        Page<ExchangeRecord> exchangeRecordPage = starShopService.getExchangeRecordPageByUserId(userId,pageNo,pageSize);
        response.setExchangeRecordList(exchangeRecordPage.getContent());
        response.setJpaPage(exchangeRecordPage);
        return response;
    }
    @Deprecated
    @RequestMapping(value="/buyGoods",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "兑换星乐园商品", notes = "")
    BuyGoodsResponse buyGoods(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "商品ID")@RequestParam Long goodsId,
            @ApiParam(value = "购买数量")@RequestParam Integer buyNumber,
            @ApiParam(value = "星星数量")@RequestParam Integer amount,
            @ApiParam(value = "收件人（可选）",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  receiver,
            @ApiParam(value = "收件电话（可选）",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  phone,
            @ApiParam(value = "收件地址(可选)",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  address
    ) throws ApiNotTokenException {

        Long userId;
        try{
            userId= userService.checkAndGetCurrentUserId(token);
        }catch (ApiNotTokenException e){
            e.printStackTrace();
            userId=-1L;
        }

        BuyGoodsResponse response = new BuyGoodsResponse();
        ExchangeRecord exchangeRecord = starShopService.addExchangeRecord(userId,goodsId,buyNumber,amount,receiver,phone,address);
        response.setExchangeRecord(exchangeRecord);
        return response;
    }

    @Deprecated
    @RequestMapping(value="/getLogistics",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取物流信息", notes = "")
    GetLogisticsResponse getLogistics(
            @ApiParam(value = "用户token",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  token,
            @ApiParam(value = "订单号(兑换记录id)")@RequestParam Long orderId
            ) throws ApiException {

        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        }else{
            userId = userService.checkAndGetCurrentUserId(token);
        }

        GetLogisticsResponse response = starShopService.getLogistics(userId,orderId);

        return response;
    }

}
