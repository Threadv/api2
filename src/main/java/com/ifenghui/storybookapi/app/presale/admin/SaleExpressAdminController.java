package com.ifenghui.storybookapi.app.presale.admin;



import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;
import com.ifenghui.storybookapi.app.presale.response.PreSaleGiftListResponse;
import com.ifenghui.storybookapi.app.presale.service.ActivityService;
import com.ifenghui.storybookapi.app.presale.service.PreSaleGiftService;
import com.ifenghui.storybookapi.app.presale.service.PreSaleGoodsService;
import com.ifenghui.storybookapi.app.presale.service.PreSalePayService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "地址信息管理")
@RequestMapping("/sale/presaleadmin/express")
public class SaleExpressAdminController {


    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;


    @Autowired
    Environment env;

    @Autowired
    PreSalePayService preSalePayService;

    @Autowired
    PreSaleGoodsService preSaleGoodsService;

    @Autowired
    ActivityService activityService;
    @Autowired
    PreSaleGiftService giftService;

    private static Logger logger = Logger.getLogger(SaleExpressAdminController.class);


    @RequestMapping(value = "/get_express", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "活动订单", notes = "活动列表")
    PreSaleGiftListResponse getGoods(
            @ApiParam(value = "activityId") @RequestParam(required = false) Integer activityId,
            @ApiParam(value = "goodsId") @RequestParam(required = false) Integer goodsId,
            @ApiParam(value = "orderId") @RequestParam(required = false) Integer orderId,
            @ApiParam(value = "status") @RequestParam(required = false) Integer status,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) {
        PreSaleGift preSaleGift=new PreSaleGift();
        preSaleGift.setActivityId(activityId);
        preSaleGift.setGoodsId(goodsId);
        preSaleGift.setPayId(orderId);
        preSaleGift.setStatus(status);
        PreSaleGiftListResponse response = new PreSaleGiftListResponse();
        Page<PreSaleGift> preSaleGifts = giftService.findAll (preSaleGift,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));

        for(PreSaleGift preSalePayItem:preSaleGifts.getContent()){
            if(preSalePayItem.getGoodsId()!=null){
                preSalePayItem.setPreSaleGoods(preSaleGoodsService.findGoodsById(preSalePayItem.getGoodsId()));
            }
            if(preSalePayItem.getActivityId()!=null){
                preSalePayItem.setActivity(activityService.findById(preSalePayItem.getActivityId()));
            }

        }
        response.setPreSaleGiftList(preSaleGifts.getContent());
        response.setJpaPage(preSaleGifts);
        return response;
    }

}
