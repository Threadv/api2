package com.ifenghui.storybookapi.adminapi.controlleradmin;



import com.ifenghui.storybookapi.adminapi.manager.entity.Manager;
import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerToken;
import com.ifenghui.storybookapi.adminapi.manager.response.ManagerResponse;
import com.ifenghui.storybookapi.adminapi.manager.response.ManagersResponse;
import com.ifenghui.storybookapi.adminapi.manager.response.RoleObj;
import com.ifenghui.storybookapi.adminapi.manager.response.RolesResponse;
import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerService;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerTokenService;
import com.ifenghui.storybookapi.api.response.base.ApiStatusResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.style.AdminResponseType;
import com.ifenghui.storybookapi.style.PublishType;
import com.ifenghui.storybookapi.style.RoleStyle;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@Component
//@RestController
@RequestMapping(value = "/adminapi/manager/", name = "后台管理员")
public class ManagerController {



    @Value("${oss.url}")
    String ossUrl;

    @Autowired
    ManagerService managerService;

    @Autowired
    ManagerTokenService managerTokenService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    CmsLogService cmsLogService;

    @RequestMapping(value = "/getManager", method = RequestMethod.GET)
    @ApiOperation("列表")
    @ResponseBody
    ManagerResponse getmanager(
            HttpServletRequest request,
            @ApiParam(value = "id") @RequestParam Integer id

    ) {
        ManagerResponse response=new ManagerResponse();
        Manager manager=managerService.findOne(id);

        response.setManager(manager);

        return response;
    }

    @RequestMapping(value = "/getManagers", method = RequestMethod.GET)
    @ApiOperation("列表")
    @ResponseBody
    ManagersResponse list(
            HttpServletRequest request,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize,
            @ApiParam(value = "publishType") @RequestParam PublishType publishType
    ) {
        ManagersResponse response=new ManagersResponse();
        Page<Manager> managerPage=managerService.findByPublishType(publishType,pageNo,pageSize);

        response.setManagers(managerPage.getContent());
        response.setJpaPage(managerPage);
        return response;
    }
    @RequestMapping(value = "/getRoles", method = RequestMethod.GET)
    @ApiOperation("设置权限")
    @ResponseBody
    RolesResponse getRoles(
//            HttpServletRequest request,
//            @ApiParam(value = "managerId") @RequestParam Integer managerId,
//            @ApiParam(value = "roles") @RequestParam(required = false) List<String> roles
            ) {
        RolesResponse rolesResponse=new RolesResponse();
        List<RoleObj> roles=new ArrayList<>();
        RoleObj roleObj;
        for(RoleStyle roleStyle:RoleStyle.values()){
            roleObj=new RoleObj();
            roleObj.setAppId(1);
            roleObj.setKey(roleStyle.toString());
            roleObj.setName(roleStyle.getTitle());
            roles.add(roleObj);
        }
        rolesResponse.setRoles(roles);
        return rolesResponse;
    }

    @RequestMapping(value = "/checkManagerRole", method = RequestMethod.GET)
    @ApiOperation("设置权限")
    @ResponseBody
    public BaseResponse checkManagerRole(
//            HttpServletRequest request,
//            @ApiParam(value = "managerId") @RequestParam Integer managerId,
//            @ApiParam(value = "roles") @RequestParam(required = false) List<String> roles
            String uri,
            String managerToken
    ) {
//传入域名之后的路径
        BaseResponse baseResponse=new BaseResponse();
        String token=managerToken;
        ManagerToken managerTokenObj= managerTokenService.findOneByToken(token);
        if(managerTokenObj==null){
            baseResponse=new BaseResponse(AdminResponseType.AUTH_ERR);
            return baseResponse;
        }
        Manager manager=managerService.findOne(managerTokenObj.getManagerId());
        boolean flag=managerService.checkRole(manager,uri);


        if(!flag){
            baseResponse=new BaseResponse(AdminResponseType.AUTH_LOW);
        }
        return baseResponse;
    }

    @RequestMapping(value = "/saveRole", method = RequestMethod.POST)
    @ApiOperation("设置权限")
    @ResponseBody
    BaseResponse setAuth(
            HttpServletRequest request,
            @ApiParam(value = "managerId") @RequestParam Integer managerId,
            @ApiParam(value = "roles") @RequestParam(required = false) List<String> roles
    ) {
        if(roles==null){
            roles=new ArrayList<>();
        }
        Manager manager1=managerService.findOne(managerId);
        manager1.setRoles(roles.toString().replace("[","").replace("]",""));
        managerService.save(manager1);
        return new BaseResponse();
    }


    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ApiOperation("设置权限")
    @ResponseBody
    BaseResponse setAuth(
            HttpServletRequest request,
            @ApiParam(value = "managerId") @RequestParam Integer managerId,
            @ApiParam(value = "password") @RequestParam(required = false) String password
    ) {

//        Manager manager=(Manager)request.getSession().getAttribute("manager");
        Manager manager1=managerService.findOne(managerId);
        manager1.setPassword(password);
        managerService.save(manager1);
        return new BaseResponse();
    }

    @RequestMapping(value = "/updateManager", method = RequestMethod.POST)
    @ApiOperation("修改发布状态")
    @ResponseBody
    BaseResponse updateManager(
            @ApiParam(value = "id") @RequestParam Integer id,
            @ApiParam(value = "publish") @RequestParam(required = false) PublishType publishType
    ) {

        Manager manager=managerService.findOne(id);
        manager.setPublishType(publishType);
        managerService.save(manager);
        BaseResponse response = new BaseResponse();
        return response ;
    }

    @RequestMapping(value = "/deleteManager", method = RequestMethod.DELETE)
    @ApiOperation("修改发布状态")
    @ResponseBody
    BaseResponse updateManager(
            @ApiParam(value = "id") @RequestParam Integer id
    ) {

        Manager manager=managerService.findOne(id);
        manager.setPublishType(PublishType.DELETE);
        managerService.save(manager);
        BaseResponse response = new BaseResponse();
        return response ;
    }

    @RequestMapping(value = "/addManager", method = RequestMethod.POST)
    @ApiOperation("增加管理员")
    @ResponseBody
    BaseResponse addManager(
            @ApiParam(value = "username") @RequestParam String username,
            @ApiParam(value = "publish") @RequestParam(required = false) String password,
            HttpServletRequest request
    ) {

        Manager manager=new Manager();
        manager.setUsername(username);
        manager.setPassword(password);
        manager.setNick(username);
        manager.setPublishType(PublishType.PUBLISH);
        managerService.save(manager);
        //添加操作日志
        cmsLogService.save("Manager",manager.getId().intValue(),1,request);

        BaseResponse response = new BaseResponse();
        return response ;
    }
}
