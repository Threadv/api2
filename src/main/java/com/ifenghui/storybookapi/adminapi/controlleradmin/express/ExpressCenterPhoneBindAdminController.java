package com.ifenghui.storybookapi.adminapi.controlleradmin.express;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterPhoneBind;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterOrderResponse;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterOrdersResponse;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterPhoneBindResponse;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterPhoneBindsResponse;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterPhoneBindService;
import com.ifenghui.storybookapi.style.ExpressSrcStyle;
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
@Api(value = "物流中心，用户绑定", description = "物流中心，用户绑定")
@RequestMapping("/adminapi/expresscenter/user")
public class ExpressCenterPhoneBindAdminController {

    @Autowired
    ExpressCenterPhoneBindService phoneBindService;

    @ApiOperation(value = "获得手机号绑定列表")
    @RequestMapping(value = "/get_express_center_phone_binds",method = RequestMethod.GET)
    @ResponseBody
    ExpressCenterPhoneBindsResponse getCenterPhoneBinds(
            String phone,
            Integer pageNo,Integer pageSize){
        ExpressCenterPhoneBindsResponse response = new ExpressCenterPhoneBindsResponse();

        ExpressCenterPhoneBind phoneBind=new ExpressCenterPhoneBind();
        phoneBind.setPhone(phone);

        Page<ExpressCenterPhoneBind> page= phoneBindService.findAll(phoneBind,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));

        response.setExpressCenterPhoneBinds(page.getContent());
        response.setJpaPage(page);

        return response;
    }

    @ApiOperation(value = "获得绑定记录",notes = "")
    @RequestMapping(value = "/get_express_center_phone_bind",method = RequestMethod.GET)
    @ResponseBody
    ExpressCenterPhoneBindResponse getCenterPhoneBind(
            Integer id){
        ExpressCenterPhoneBindResponse response = new ExpressCenterPhoneBindResponse();

        ExpressCenterPhoneBind phoneBind=phoneBindService.findOne(id);
        response.setExpressCenterPhoneBind(phoneBind);

        return response;
    }

    @ApiOperation(value = "增加绑定记录",notes = "")
    @RequestMapping(value = "/add_express_center_phone_bind",method = RequestMethod.POST)
    @ResponseBody
    ExpressCenterPhoneBindResponse addCenterPhoneBind(
            Integer userType,
            String phone,
            Integer userOutId,
            Integer isActive
            ){
        ExpressCenterPhoneBindResponse response = new ExpressCenterPhoneBindResponse();

        ExpressCenterPhoneBind phoneBind=new ExpressCenterPhoneBind();
        phoneBind.setUserType(userType);
        phoneBind.setPhone(phone);
        phoneBind.setUserOutId(userOutId);
        phoneBind.setIsActive(isActive);
        phoneBindService.addExpressCenterPhoneBind(phoneBind);

        response.setExpressCenterPhoneBind(phoneBind);

        return response;
    }

    @ApiOperation(value = "增加绑定记录",notes = "")
    @RequestMapping(value = "/update_express_center_phone_bind",method = RequestMethod.PUT)
    @ResponseBody
    ExpressCenterPhoneBindResponse addCenterPhoneBind(
            Integer id,
            Integer userType,
            String phone,
            Integer userOutId,
            Integer isActive
    ){
        ExpressCenterPhoneBindResponse response = new ExpressCenterPhoneBindResponse();

        ExpressCenterPhoneBind phoneBind=phoneBindService.findOne(id);
        phoneBind.setUserType(userType);
        phoneBind.setPhone(phone);
        phoneBind.setUserOutId(userOutId);
        phoneBind.setIsActive(isActive);
        phoneBindService.addExpressCenterPhoneBind(phoneBind);

        response.setExpressCenterPhoneBind(phoneBind);

        return response;
    }

    @ApiOperation(value = "删除绑定记录",notes = "")
    @RequestMapping(value = "/delete_express_center_phone_bind",method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse deleteCenterPhoneBind(
            Integer id
    ){
         phoneBindService.delete(id);

        return new BaseResponse();
    }
}
