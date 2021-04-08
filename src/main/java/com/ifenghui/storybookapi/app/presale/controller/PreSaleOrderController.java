package com.ifenghui.storybookapi.app.presale.controller;



import com.ifenghui.storybookapi.adminapi.controlleradmin.express.ExpressCenterOrderAdminController;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleUser;
import com.ifenghui.storybookapi.app.presale.response.PreSalePayResponse;
import com.ifenghui.storybookapi.app.presale.response.PreSalePaysResponse;
import com.ifenghui.storybookapi.app.presale.service.*;
import com.ifenghui.storybookapi.style.ExpressSrcStyle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "订单", description = "订单相关接口")
@RequestMapping("/sale/order")
public class PreSaleOrderController {



    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    Environment env;

    @Autowired
    PreSalePayService payService;


    @Autowired
    PreSaleCodeService codeService;

    @Autowired
    PreSaleGoodsService goodsService;

    @Autowired
    YiZhiActivityUserService userService;

    @Autowired
    PreSaleUserService saleUserService;

    @Autowired
    ActivityService activityService;

//    @Autowired
//    RemoteAppApiService remoteAppApiService;

    @Autowired
    PreSaleGiftService giftService;

    private static Logger logger = Logger.getLogger(PreSaleOrderController.class);

    /**
     * 获取订单列表
     *
     * @param token
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/get_orders", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得订单记录", notes = "获得订单记录")
    @Transactional
    PreSalePayResponse getOrder(
            @ApiParam(value = "unionId/token") @RequestParam String token,
            @ApiParam(value = "activityId") @RequestParam(defaultValue = "0") Integer activityId,
            @ApiParam(value = "goodsId") @RequestParam(defaultValue = "0") Integer goodsId
    ) {
        PreSalePayResponse response=new PreSalePayResponse();
        PreSaleUser preSaleUser=saleUserService.getUserByUnionid(token);
        if(preSaleUser==null){
            response.getStatus().setCode(4);
            response.getStatus().setMsg("用户不存在！");
            return response;
        }
        PreSalePay preSalePay= payService.getPayByUserIdAndGoodsId(preSaleUser.getId(),goodsId,activityId);


        response.setPreSalePay(preSalePay);

        return response;
    }

    /**
     * 获取订单列表
     * 适合一个活动只有一件需要物流货物的情况
     * @param token
     * @return
     */
    @RequestMapping(value = "/get_all_orders", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得活动中的全部订单", notes = "获得活动中的全部订单")
    @Transactional
    PreSalePaysResponse getOrder(
            @ApiParam(value = "unionId/token") @RequestParam String token,
            @ApiParam(value = "activityId") @RequestParam(defaultValue = "0") Integer activityId
    ) {
        PreSalePaysResponse response=new PreSalePaysResponse();
        PreSaleUser preSaleUser=saleUserService.getUserByUnionid(token);
        if(preSaleUser==null){
            response.getStatus().setCode(4);
            response.getStatus().setMsg("用户不存在！");
            return response;
        }
//        Activity activity=activityService.findById(activityId);

        PreSalePay findPreSalePay=new PreSalePay();
        findPreSalePay.setUserId(preSaleUser.getId());
        findPreSalePay.setStatus(1);
//        findPreSalePay.setUserType(activity.getUserType());
        findPreSalePay.setActivityId(activityId);
        Page<PreSalePay> preSalePay= payService.findAll(findPreSalePay,new PageRequest(0,10,new Sort(Sort.Direction.DESC,"id")));
        for(PreSalePay salePay:preSalePay.getContent()){
            salePay.setActivity(activityService.findById(salePay.getActivityId()));
            salePay.setPreSaleGoods(goodsService.findGoodsById(salePay.getGoodsId()));
            salePay.setAddress(giftService.getGiftByPayId(salePay.getId()));
        }

        response.setPreSalePays(preSalePay.getContent());

        return response;
    }

    @RequestMapping(value = "/outputToExpressCenter", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出订单到物流中心", notes = "导出订单到物流中心")
    @Transactional
    BaseResponse outputToExpressCenter(
    ) {
        BaseResponse response=new BaseResponse();

//        Activity activity=activityService.findById(activityId);

        PreSalePay findPreSalePay=new PreSalePay();
        findPreSalePay.setIsExpressCenter(1);
        findPreSalePay.setStatus(1);

        Page<PreSalePay> preSalePay= payService.findAll(findPreSalePay,new PageRequest(0,10,new Sort(Sort.Direction.DESC,"id")));
        output(preSalePay.getContent());
        for(int i=1;i<preSalePay.getTotalPages();i++){
            preSalePay= payService.findAll(findPreSalePay,new PageRequest(i,10,new Sort(Sort.Direction.DESC,"id")));
            output(preSalePay.getContent());
        }


        return response;
    }

    @Autowired
    ExpressCenterOrderAdminController expressCenterOrderAdminController;

    private void output(List<PreSalePay> preSalePays){
        PreSaleGift preSaleGift;
        for(PreSalePay preSalePay:preSalePays){
            preSaleGift= giftService.getGiftByPayId(preSalePay.getId());
            if(preSaleGift==null){
                continue;
            }
            expressCenterOrderAdminController.addCenterOrder(ExpressSrcStyle.SALE_ACTIVITY
                    ,preSalePay.getSuccessTime()
                    ,"故事飞船1年份 服务号购买"
                    ,1
                    ,preSalePay.getId()+""
                    , preSaleGift.getPhone()
                    ,preSaleGift.getReceiver(),
                    "",
                    "",
                    ""
                    ,preSaleGift.getAddress()
                    ,23
                    ,0);
        }
    }

}
