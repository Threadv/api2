package com.ifenghui.storybookapi.adminapi.controlleradmin.express;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterMag;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterMagResponse;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterMagsResponse;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterOrdersResponse;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterMagService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterOrderService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterPhoneBindService;
import com.ifenghui.storybookapi.config.MyEnv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
//@EnableAutoConfiguration
@Api(value = "订单中心", description = "订单中心")
@RequestMapping("/adminapi/expresscenter/mag")
public class ExpressCenterMagAdminController {

    @Autowired
    ExpressCenterMagService centerMagService;



    @ApiOperation(value = "获得所有mag列表")
    @RequestMapping(value = "/get_express_mags",method = RequestMethod.GET)
    @ResponseBody
    ExpressCenterMagsResponse getCenterMags(
            Integer pageNo,
            Integer pageSize
    ){
        ExpressCenterMagsResponse response = new ExpressCenterMagsResponse();
        ExpressCenterMag mag = new ExpressCenterMag();
        Page<ExpressCenterMag> page= centerMagService.findAll(mag,new PageRequest(pageNo,pageSize));

        response.setExpressCenterMags(page.getContent());
        response.setJpaPage(page);

         return response;
    }

    @ApiOperation(value = "获得mag")
    @RequestMapping(value = "/get_express_mag",method = RequestMethod.GET)
    @ResponseBody
    ExpressCenterMagResponse getCenterMags(
            Integer id
    ){
        ExpressCenterMagResponse response = new ExpressCenterMagResponse();

        ExpressCenterMag mag = centerMagService.findOne(id);
        response.setExpressCenterMag(mag);
        return response;
    }

    @ApiOperation(value = "增加物流中的杂志")
    @RequestMapping(value = "/add_express_mag",method = RequestMethod.POST)
    @ResponseBody
    ExpressCenterMagResponse addCenterMag(
            String title,
            String cover,
            Integer year,
            Integer month
    ){
            ExpressCenterMagResponse response = new ExpressCenterMagResponse();
        ExpressCenterMag mag = new ExpressCenterMag();
        mag.setCover(cover);
        mag.setMonth(month);
        mag.setPosition(0);
        mag.setTitle(title);
        mag.setYear(year);
        centerMagService.addMag(mag);

        return response;
    }

    @ApiOperation(value = "修改杂志信息")
    @RequestMapping(value = "/update_express_mag",method = RequestMethod.PUT)
    @ResponseBody
    ExpressCenterMagResponse addCenterMag(
            Integer id,
            String title,
            String cover,
            Integer year,
            Integer month
    ){
        ExpressCenterMagResponse response = new ExpressCenterMagResponse();
        ExpressCenterMag mag =centerMagService.findOne(id);
        mag.setCover(cover);
        mag.setMonth(month);
        mag.setPosition(0);
        mag.setTitle(title);
        mag.setYear(year);
        centerMagService.addMag(mag);

        return response;
    }

    @ApiOperation(value = "删除杂志信息")
    @RequestMapping(value = "/delete_express_mag",method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse deleteCenterMag(
            Integer id
    ){
        centerMagService.deleteMag(id);

        return new BaseResponse();
    }

}
