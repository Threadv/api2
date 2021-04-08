package com.ifenghui.storybookapi.adminapi.controlleradmin.express;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterOrderResponse;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterOrdersResponse;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterOrderService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterPhoneBindService;
import com.ifenghui.storybookapi.style.ExpressSrcStyle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
//@EnableAutoConfiguration
@Api(value = "订单中心", description = "订单中心")
@RequestMapping("/adminapi/expresscenter/order")
public class ExpressCenterOrderAdminController {

    @Autowired
    ExpressCenterPhoneBindService phoneBindService;

    @Autowired
    ExpressCenterOrderService orderService;

    @ApiOperation(value = "获得订单列表",notes = "返回某物流中心用户绑定某手机号的订单")
    @RequestMapping(value = "/get_express_center_orders",method = RequestMethod.GET)
    @ResponseBody
    ExpressCenterOrdersResponse getCenterOrders(
            @RequestParam(required = false) Integer id,
            ExpressSrcStyle expressSrcStyle,
            String phone,
            String fullname,
            String srcMark,
            String outOrderId,
            Integer isOpen,
            Integer pageNo,Integer pageSize){
        ExpressCenterOrdersResponse response = new ExpressCenterOrdersResponse();

         ExpressCenterOrder order=new ExpressCenterOrder();
         if ( null != id){
             order.setId(id);
         }
         if (isOpen != null){
             order.setIsOpen(isOpen);
         }
         if (srcMark != null && !"".equals(srcMark)){
             order.setSrcMark(srcMark);
         }
        if (phone != null && !"".equals(phone)){
            order.setPhone(phone);
        }
        if (fullname != null && !"".equals(fullname)){
            order.setFullname(fullname);
        }
        if (outOrderId != null && !"".equals(outOrderId)) {
            order.setOutOrderId(outOrderId);
        }
         if(expressSrcStyle!=null){
             order.setSrcStryle(expressSrcStyle);
         }

        Page<ExpressCenterOrder> page= orderService.findAll(order,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));

        response.setExpressCenterOrders(page.getContent());
        response.setJpaPage(page);

         return response;
    }

    @ApiOperation(value = "获得订单列表",notes = "")
    @RequestMapping(value = "/get_express_center_order",method = RequestMethod.GET)
    @ResponseBody
    ExpressCenterOrderResponse getCenterOrders(
            Integer id){
        ExpressCenterOrderResponse response = new ExpressCenterOrderResponse();

        ExpressCenterOrder order=orderService.findOne(id);

        response.setExpressCenterOrder(order);
        return response;
    }

    @ApiOperation(value = "删除订单列表",notes = "")
    @RequestMapping(value = "/delete_express_center_order",method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse deleteCenterOrder(
            Integer id){
        orderService.deleteExpressCenterOrder(id);
        return new BaseResponse();
    }

    @ApiOperation(value = "添加订单",notes = "")
    @RequestMapping(value = "/add_express_center_order",method = RequestMethod.POST)
    @ResponseBody
    public ExpressCenterOrderResponse addCenterOrder(
            ExpressSrcStyle srcStyle,
            @ApiParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date orderTime,
            String srcMark,
            //是否在配送期间，1为在期间
            Integer isOpen,
            //外部订单号
            String outOrderId,
            //订单手机号
            String phone,
            //收件人名称
            String fullname,
            //省
            String province,
            //市
            String city,
            //区
            String district,
            //地址
            String address,
            //订阅总期数
            Integer volCount,
            //已经配送的期数
            Integer volOver
            ){
        ExpressCenterOrderResponse response = new ExpressCenterOrderResponse();

        ExpressCenterOrder order=new ExpressCenterOrder();
        order.setSrcStryle(srcStyle);
        order.setOrderTime(orderTime);
        order.setSrcMark(srcMark);
        order.setProvince(province);
        order.setCity(city);
        order.setDistrict(district);
        order.setCreateTime(new Date());
        order.setIsOpen(isOpen);
        order.setOutOrderId(outOrderId);
        order.setPhone(phone);
        order.setFullname(fullname);
        order.setAddress(address);
        order.setVolCount(volCount);
        order.setVolOver(volOver);
        order.setOrderTime(orderTime);

        order=orderService.addExpressCenterOrder(order);
//        ExpressCenterOrder order=orderService.(id);

        response.setExpressCenterOrder(order);

        return response;
    }

    @ApiOperation(value = "编辑订单",notes = "")
    @RequestMapping(value = "/update_express_center_order",method = RequestMethod.PUT)
    @ResponseBody
    ExpressCenterOrderResponse updateCenterOrder(
            Integer id,
            ExpressSrcStyle srcStyle,
            @ApiParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date orderTimeFormat,
            String srcMark,
            //是否在配送期间，1为在期间
            Integer isOpen,
            //外部订单号
            String outOrderId,
            //订单手机号
            String phone,
            //收件人名称
            String fullname,
            //地址
            String address,
            //订阅总期数
            Integer volCount,
            //已经配送的期数
            Integer volOver
    ){
        ExpressCenterOrderResponse response = new ExpressCenterOrderResponse();

        ExpressCenterOrder order=orderService.findOne(id);
        if(srcStyle!=null){
            order.setSrcStryle(srcStyle);
        }
        if (orderTimeFormat != null){
            order.setOrderTime(orderTimeFormat);
        }
        if(srcMark!=null){
            order.setSrcMark(srcMark);
        }
        if(isOpen!=null){
            order.setIsOpen(isOpen);
        }
        if(outOrderId!=null){
            order.setOutOrderId(outOrderId);
        }
        if(phone!=null){
            order.setPhone(phone);
        }
        if(fullname!=null){
            order.setFullname(fullname);
        }
        if(address!=null){
            order.setAddress(address);
        }
        if(volCount!=null){
            order.setVolCount(volCount);
        }
        if(volOver!=null){
            order.setVolOver(volOver);
        }

        order=orderService.updateExpressCenterOrder(order);

        response.setExpressCenterOrder(order);

        return response;
    }

}
