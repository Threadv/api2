package com.ifenghui.storybookapi.adminapi.manager.controlleradmin;


import com.ifenghui.storybookapi.adminapi.manager.entity.Manager;
import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerToken;
import com.ifenghui.storybookapi.adminapi.manager.response.ManagerTokenResponse;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerService;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerTokenService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.util.HeadManagerUtil;
import com.ifenghui.storybookapi.util.MD5Util;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.ifenghui.storybookapi.style.AdminResponseType.AUTH_ERR;
import static com.ifenghui.storybookapi.style.AdminResponseType.SUCCESS;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@Component
//@RestController
@RequestMapping(value = "/adminapi/auth", name = "后台权限")
public class AuthAdminController {



    @Autowired
    ManagerService managerService;

    @Autowired
    ManagerTokenService managerTokenService;


    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    @ApiOperation("验证登录")
    @ResponseBody
    BaseResponse checkLogin(
            HttpServletRequest request
    ) {
        String token= HeadManagerUtil.getManagerToken(request);
        Object obj=managerTokenService.findOneByToken(token);

        if(obj==null){
            return new BaseResponse(AUTH_ERR);
        }
        return new BaseResponse(SUCCESS);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("登录")
    @ResponseBody
    ManagerTokenResponse login(
            HttpServletRequest request,
            @ApiParam(value = "username") @RequestParam String username,
            @ApiParam(value = "password") @RequestParam String password
    ) {

        Manager manager=managerService.findByUserAndPwd(username,password);

        request.getSession().setAttribute("manager",manager);



        if(manager==null){
            return new ManagerTokenResponse(AUTH_ERR);
        }else{
            ManagerToken managerToken=new ManagerToken();
            managerToken.setManagerId(manager.getId());
            managerToken.setCreateTime(new Date());
            managerToken.setUserAgent(request.getHeader("user-agent"));
            managerToken.setIp(HeadManagerUtil.getLocalIp(request));
            managerToken.setToken(MD5Util.getMD5(System.currentTimeMillis()+""+manager.getId()));
            managerToken=managerTokenService.save(managerToken);

            ManagerTokenResponse managerTokenResponse=new ManagerTokenResponse(SUCCESS);
            managerTokenResponse.setManagerToken(managerToken);

            return managerTokenResponse;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiOperation("登出")
    @ResponseBody
    BaseResponse logout(
            HttpServletRequest request
    ) {
        String token=HeadManagerUtil.getManagerToken(request);
        managerTokenService.logout(token);
        request.getSession().removeAttribute("manager");
        return new BaseResponse(SUCCESS);
    }




}
