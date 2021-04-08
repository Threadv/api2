package com.ifenghui.storybookapi.app.presale.controller;



import com.ifenghui.storybookapi.adminapi.controlleradmin.code.CodeAdminController;
import com.ifenghui.storybookapi.app.presale.dao.PreSaleCodeDao;
import com.ifenghui.storybookapi.app.presale.entity.Activity;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGoods;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleUser;
import com.ifenghui.storybookapi.app.presale.response.GetVipCodeResponse;
import com.ifenghui.storybookapi.app.presale.response.PreSaleCodeListResponse;
import com.ifenghui.storybookapi.app.presale.response.PreSaleCodeResponse;
import com.ifenghui.storybookapi.app.presale.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "兑换码", description = "兑换码相关接口")
@RequestMapping("/sale/code")
public class CodeController {

    @Autowired
    PreSaleCodeService codeService;

    @Autowired
    YiZhiActivityUserService userService;

    @Autowired
    PreSaleUserService saleUserService;

    @Autowired
    PreSaleCodeDao codeDao;

//    @Autowired
//    RemoteAppApiService remoteAppApiService;

    @Autowired
    PreSaleGoodsService goodsService;

    @Autowired
    ActivityService activityService;

    @Autowired
    CodeAdminController codeAdminController;

//    /**
//     * wx用户通过unionId获得兑换码
//     *
//     * @param unionId
//     * @param activityId
//     * @return
//     */
//    @RequestMapping(value = "/get_codes_by_unionId", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "通过union获取兑换码", notes = "通过union获取兑换码")
//    PreSaleCodeListResponse getCodeListByUnionId(
//            @ApiParam(value = "用户token") @RequestParam String unionId,
//            @ApiParam(value = "活动id") @RequestParam Integer activityId
//    ) {
//
//        PreSaleCodeListResponse response = new PreSaleCodeListResponse();
//        PreSaleUser user = saleUserService.getUserByUnionid(unionId);
//        if (user == null) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("用户不存在！");
//            return response;
//        }
//        List<PreSaleCode> codeList = codeService.getCodeListByUserIdAndActivityId(user.getId(), activityId);
//        response.setCodeList(codeList);
//        return response;
//    }

//    /**
//     * app用户通过token获得兑换码
//     *
//     * @param token
//     * @param activityId
//     * @return
//     */
//    @RequestMapping(value = "/get_codes_by_token", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "通过token获取兑换码", notes = "通过token获取兑换码")
//    PreSaleCodeListResponse getCodeListByToken(
//            @ApiParam(value = "用户token") @RequestParam String token,
//            @ApiParam(value = "活动id") @RequestParam Integer activityId
//    ) {
//
//        PreSaleCodeListResponse response = new PreSaleCodeListResponse();
//        Integer userId;
//        try {
//            userId = userService.checkAndGetCurrentUserId(token);
//        } catch (Exception e) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("token相关用户不存在！");
//            return response;
//        }
//
//        List<PreSaleCode> codeList = codeService.getCodeListByUserIdAndActivityId(userId, activityId);
//        response.setCodeList(codeList);
//        return response;
//
//    }

    /**
     * 通过订单获取兑换码
     *
     * @param payId
     * @return
     */
    @RequestMapping(value = "/get_code_by_payId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过订单获取兑换码", notes = "通过订单获取兑换码")
    PreSaleCodeResponse getCodeByPayId(
            @ApiParam(value = "订单id") @RequestParam Integer payId
    ) {

        PreSaleCodeResponse response = new PreSaleCodeResponse();
        PreSaleCode code = codeService.getCodeByPayId(payId);
        response.setCode(code);
        return response;
    }

    /**
     * 生成vip兑换码 微信分销使用
     *
     * @return
     */
    @RequestMapping(value = "/add_vipCode", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "生成vip兑换码 微信分销使用", notes = "生成vip兑换码 微信分销使用")
    GetVipCodeResponse addVipCode(
            @ApiParam(value = "商品id") @RequestParam Integer goodsId,
            @ApiParam(value = "sign") @RequestParam String sign
    ) {

        GetVipCodeResponse response = new GetVipCodeResponse();
        if (sign.equals("vista688")) {
//            PreSaleCode code = codeService.addCode(0, 2, 4, goodsId, SaleStyle.WX_SERVICE);

            PreSaleGoods preSaleGoods= goodsService.findGoodsById(goodsId);
            Activity activity=activityService.findById(preSaleGoods.getActivityId());
             String codeStr=codeAdminController.addCodeByType(4,activity.getContent(),goodsId,preSaleGoods.getActivityId()).getPreSaleCode().getCode();
            response.setVipCode(codeStr);
        }
        return response;
    }


    /**
     * 兑换码是否激活
     *
     * @return
     */
    @RequestMapping(value = "/is_use", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "兑换码是否激活", notes = "兑换码是否激活")
    PreSaleCodeResponse isUse(
            @ApiParam(value = "code") @RequestParam String code
    ) {

        PreSaleCodeResponse response = new PreSaleCodeResponse();

        PreSaleCode saleCode = codeService.getCodeByCode(code);
        response.setCode(saleCode);
        return response;
    }



//    /**
//     * 兑换码过期处理
//     * 每天凌晨一点执行一次
//     */
//    @Scheduled(cron = "0 0 1 * * ?")
////    @Scheduled(cron = "*/5 * * * * ?")
//    public void timering() {
//        codeService.setExpire();
//    }



}
