package com.ifenghui.storybookapi.app.express.controller;

import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterPhoneBind;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterOrdersResponse;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterOrderService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterPhoneBindService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterTrackService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.remote.RemoteFenxiaoApiService;
import com.ifenghui.storybookapi.remote.resp.FenxiaoUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
//@EnableAutoConfiguration
@Api(value = "订单中心", description = "订单中心")
@RequestMapping("/api/expresscenter/order")
public class ExpressCenterOrderController {

    @Autowired
    ExpressCenterPhoneBindService phoneBindService;

    @Autowired
    ExpressCenterOrderService orderService;

    @Autowired
    ExpressCenterTrackService trackService;

    @Autowired
    UserService userService;

    @Autowired
    RemoteFenxiaoApiService remoteFenxiaoApiService;


//    @ApiOperation(value = "获得订单列表",notes = "返回某物流中心用户绑定某手机号的订单")
//    @RequestMapping(value = "/get_express_center_orders",method = RequestMethod.GET)
//    @ResponseBody
//    ExpressCenterOrdersResponse getCenterOrders(Integer bindPhoneId){
//        //TODO
//        //目前有两种方式跳转界面：app 和 微信 传入token ,根据token获取，根据user_id进入数据库比对，
//        // 同时对没有手机号绑定的进行绑定。
//        ExpressCenterOrdersResponse response = new ExpressCenterOrdersResponse();
//
//         phoneBindService.findOne(bindPhoneId).getPhone();
//
////        Page<ExpressCenterOrder> page= orderService.findByPhoneBindId(bindPhoneId,new PageRequest(0,99  ,new Sort(Sort.Direction.DESC,"id")));
//        Pageable pageable = new PageRequest(0, 99, new Sort(Sort.Direction.DESC, "id"));
//        Page<ExpressCenterOrder> page= orderService.findOByPhoneBindId(bindPhoneId,pageable);
//
//        response.setExpressCenterOrders(page.getContent());
//        response.setJpaPage(page);
//
//         return response;
//    }

    @ApiOperation(value = "获得订单列表",notes = "返回某物流中心用户绑定某手机号的订单")
    @RequestMapping(value = "/get_express_center_orders_token",method = RequestMethod.GET)
    @ResponseBody
    ExpressCenterOrdersResponse checkSsToken(@RequestHeader(value = "ssToken") String ssToken,
                                             @RequestHeader(value = "userType") Integer userType){
        ExpressCenterOrdersResponse response = new ExpressCenterOrdersResponse();
        //根据token查询用户id
        Integer userOutId=null;
        if(userType==1){
            Long userId = userService.checkAndGetCurrentUserId(ssToken);
            userOutId = userId.intValue();
        }else if(userType==2){
            FenxiaoUser fenxiaoUser= remoteFenxiaoApiService.getUserByUnionId(ssToken).getUser();
            userOutId = fenxiaoUser.getId();
        }
        ExpressCenterPhoneBind phoneBind = phoneBindService.findByUserIdAnd(userOutId,1);
        if (phoneBind == null){
            if (userType==2){
                response.getStatus().setCode(0);
                response.getStatus().setMsg("需绑定手机号！");
                return response;
            }else if (userType==1){
                User user = userService.getUser(userOutId);
                phoneBind = new ExpressCenterPhoneBind();
                phoneBind.setUserOutId(userOutId);
                phoneBind.setUserType(userType);
                phoneBind.setPhone(user.getPhone());
                phoneBind.setCreateTime(new Date());
                phoneBindService.addExpressCenterPhoneBind(phoneBind);
            }
        }
            //根据绑定手机号查询订单
//            Page<ExpressCenterOrder> page= orderService.findByPhone(phoneBind.getPhone(),new PageRequest(0,99,new Sort(Sort.Direction.DESC,"id")));
        List<ExpressCenterOrder> orders = new ArrayList<>();
        List<ExpressCenterOrder> list = orderService.findOrdersByPhone(phoneBind.getPhone());
        for (ExpressCenterOrder o:list){
            //查询该订单下是否有物流信息
            List<ExpressCenterTrack> tracks = trackService.findAlByOrderId(o.getId());
            if (tracks.size()>0){
                orders.add(o);
            }
        }
        response.setExpressCenterOrders(orders);
        response.setPhone(phoneBind.getPhone() .replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
//            response.setJpaPage(page);
        return response;
    }


}
