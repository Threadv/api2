package com.ifenghui.storybookapi.app.presale.admin;




import com.ifenghui.storybookapi.app.presale.entity.PreSaleGoods;
import com.ifenghui.storybookapi.app.presale.response.ActivityResponse;
import com.ifenghui.storybookapi.app.presale.response.PreSaleGoodsListResponse;
import com.ifenghui.storybookapi.app.presale.response.PreSaleGoodsResponse;
import com.ifenghui.storybookapi.app.presale.service.PreSaleGoodsService;
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
@Api(value = "商品管理")
@RequestMapping("/sale/presaleadmin/goods")
public class SaleGoodsAdminController {


    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;


    @Autowired
    Environment env;

    @Autowired
    PreSaleGoodsService goodsService;

    private static Logger logger = Logger.getLogger(SaleGoodsAdminController.class);


    @RequestMapping(value = "/get_goodses", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "活动列表", notes = "活动列表")
    PreSaleGoodsListResponse getGoods(
            @ApiParam(value = "activityId") @RequestParam(required = false) Integer activityId,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) {
        PreSaleGoods preSaleGoods=new PreSaleGoods();
        preSaleGoods.setActivityId(activityId);
        PreSaleGoodsListResponse response = new PreSaleGoodsListResponse();
        Page<PreSaleGoods> preSaleGoodsPage = goodsService.findAll(preSaleGoods,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
        response.setPreSaleGoodsList(preSaleGoodsPage.getContent());
        response.setJpaPage(preSaleGoodsPage);
        return response;
    }

    @RequestMapping(value = "/get_goods", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "活动")
    PreSaleGoodsResponse getGood(
            @ApiParam(value = "id") @RequestParam Integer id
    ) {

        PreSaleGoodsResponse response = new PreSaleGoodsResponse();
        PreSaleGoods goods = goodsService.findGoodsById(id);
        response.setPreSaleGoods(goods);

        return response;
    }

    @RequestMapping(value = "/add_goods", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "增加商品")
    PreSaleGoodsResponse getActivity(
            @ApiParam(value = "activityId") @RequestParam(required = false) Integer activityId,
            @ApiParam(value = "content") @RequestParam String content,
            @ApiParam(value = "icon") @RequestParam String icon,
            @ApiParam(value = "price") @RequestParam(required = false) Integer price,
            @ApiParam(value = "type") @RequestParam(required = false) Integer type,
            @ApiParam(value = "storage") @RequestParam(required = false) Integer storage,
            @ApiParam(value = "codeType") @RequestParam(required = false) Integer codeType,
            @ApiParam(value = "isExpressCenter") @RequestParam(required = false) Integer isExpressCenter
    ) {

        PreSaleGoodsResponse response = new PreSaleGoodsResponse();
        PreSaleGoods preSaleGoods=new PreSaleGoods();
        preSaleGoods.setContent(content);
        preSaleGoods.setActivityId(activityId);
        preSaleGoods.setIcon(icon);
        preSaleGoods.setPrice(price);
        preSaleGoods.setType(type);
        preSaleGoods.setStorage(storage);
        preSaleGoods.setCodeType(codeType);
        preSaleGoods.setIsExpressCenter(isExpressCenter);
        goodsService.save(preSaleGoods);
//        response.setPreSaleActivity(activities);

        return response;
    }

    @RequestMapping(value = "/update_goods", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "修改商品")
    PreSaleGoodsResponse updateActivity(
            @ApiParam(value = "id") @RequestParam Integer id,
            @ApiParam(value = "content") @RequestParam String content,
            @ApiParam(value = "icon") @RequestParam String icon,
            @ApiParam(value = "price") @RequestParam(required = false) Integer price,
            @ApiParam(value = "type") @RequestParam(required = false) Integer type,
            @ApiParam(value = "storage") @RequestParam(required = false) Integer storage,
            @ApiParam(value = "codeType") @RequestParam(required = false) Integer codeType,
            @ApiParam(value = "isExpressCenter") @RequestParam(required = false) Integer isExpressCenter
    ) {

        PreSaleGoodsResponse response = new PreSaleGoodsResponse();
        PreSaleGoods preSaleGoods=goodsService.findGoodsById(id);
        preSaleGoods.setContent(content);
        preSaleGoods.setIcon(icon);
        preSaleGoods.setPrice(price);
        preSaleGoods.setType(type);
        preSaleGoods.setStorage(storage);
        preSaleGoods.setCodeType(codeType);
        preSaleGoods.setIsExpressCenter(isExpressCenter);
        goodsService.save(preSaleGoods);
//        response.setPreSaleActivity(activities);

        return response;
    }

    @RequestMapping(value = "/delete_goods", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除")
    ActivityResponse deleteGoods(
            @ApiParam(value = "id") @RequestParam Integer id
    ) {

//        goodsService.delete(id);

        return new ActivityResponse();
    }
}
