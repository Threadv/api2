package com.ifenghui.storybookapi.app.presale.admin;




import com.ifenghui.storybookapi.app.presale.entity.PreSaleUser;
import com.ifenghui.storybookapi.app.presale.response.PreSaleUsersResponse;
import com.ifenghui.storybookapi.app.presale.service.ActivityService;
import com.ifenghui.storybookapi.app.presale.service.PreSaleGoodsService;
import com.ifenghui.storybookapi.app.presale.service.PreSalePayService;
import com.ifenghui.storybookapi.app.presale.service.PreSaleUserService;
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
@Api(value = "参与活动用户")
@RequestMapping("/sale/presaleadmin/user")
public class SaleUserAdminController {


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
    PreSaleUserService userService;

    private static Logger logger = Logger.getLogger(SaleUserAdminController.class);


    @RequestMapping(value = "/get_users", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "用户列表", notes = "用户列表")
    PreSaleUsersResponse getUsersAdmin(
            @ApiParam(value = "nick") @RequestParam(required = false) String nick,
            @ApiParam(value = "nick") @RequestParam(required = false) Integer id,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) {
        PreSaleUser preSaleUser=new PreSaleUser();
        if(nick!=null&&!nick.equals("")){
            preSaleUser.setNick(nick);
        }
       if(id!=null){
           preSaleUser.setId(id);
       }


        PreSaleUsersResponse response = new PreSaleUsersResponse();
        Page<PreSaleUser> preSaleUsers = userService.findAll(preSaleUser,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));


        response.setPreSaleUsers(preSaleUsers.getContent());
        response.setJpaPage(preSaleUsers);
        return response;
    }



}
