package com.ifenghui.storybookapi.adminapi.controlleradmin.settings;


import com.ifenghui.storybookapi.adminapi.manager.entity.Manager;
import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerToken;
import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.Config;
import com.ifenghui.storybookapi.app.app.response.*;
import com.ifenghui.storybookapi.app.app.service.AdService;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.story.service.IpLabelService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.AdminResponseType;
import com.ifenghui.storybookapi.util.HeadManagerUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "APP配置", description = "APP配置api")
@RequestMapping("/adminapi/setting")
public class AppConfigController {

    @Autowired
    ConfigService configService;

    @Autowired
    CmsLogService cmsLogService;


    @ApiOperation(value = "获得APP配置列表")
    @RequestMapping(value = "/get_app_config_list",method = RequestMethod.GET)
    @ResponseBody
    GetConfigListResponse getAppConfigs(
        @ApiParam(value = "pageNo") @RequestParam(required = false) Integer pageNo,
        @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        if(pageNo==null){
            pageNo=0;
        }
        GetConfigListResponse response = new GetConfigListResponse();
        Config config = new Config();
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<Config> page = configService.findAll(config,pageable);
        response.setConfigs(page.getContent());
        response.setJpaPage(page);
        return response;
    }

    @ApiOperation(value = "获得APP配置")
    @RequestMapping(value = "/get_app_config",method = RequestMethod.GET)
    @ResponseBody
    GetConfigByKeyResponse getAppConfig(
             Integer id
    ) throws ApiNotTokenException {
        GetConfigByKeyResponse response=new GetConfigByKeyResponse();
        Config config = configService.findOne(id);
        response.setConfig(config);
        return response;
    }

    @ApiOperation(value = "添加APP配置")
    @RequestMapping(value = "/add_app_config",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addAppConfig(
            String intro,
            String key,
            String val,
            HttpServletRequest request
    ) throws ApiNotTokenException {
        BaseResponse response=new BaseResponse();
        if (intro == null || key == null || val == null || intro.equals("") || key.equals("") || val.equals("")){
            response=new BaseResponse(AdminResponseType.ERROR);
            return response;
        }
        Config config = new Config();
        config.setIntro(intro);
        config.setKey(key);
        config.setVal(val);
        config.setCreateTime(new Date());
        configService.save(config);
        //添加操作日志
        cmsLogService.save("Config",config.getId().intValue(),1,request);
        return response;
    }

    @ApiOperation(value = "编辑APP配置")
    @RequestMapping(value = "/edit_app_config",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse editAppConfig(
            Integer id,
            String intro,
            String key,
            String val,
            HttpServletRequest request
    ) throws ApiNotTokenException {
        BaseResponse response=new BaseResponse();
        if (id == null || intro == null || key == null || val == null || intro.equals("") || key.equals("") || val.equals("")){
            response=new BaseResponse(AdminResponseType.ERROR);
            return response;
        }
        Config config = configService.findOne(id);
        config.setIntro(intro);
        config.setKey(key);
        config.setVal(val);
        configService.save(config);
        cmsLogService.save("Config",config.getId().intValue(),2,request);
        return response;
    }


    @ApiOperation(value = "删除APP配置")
    @RequestMapping(value = "/del_app_config",method = RequestMethod.GET)
    @ResponseBody
    BaseResponse ditAppConfig(
            Integer id,
            HttpServletRequest request
    ) throws ApiNotTokenException {
        BaseResponse response=new BaseResponse();
        configService.del(id);
        cmsLogService.save("Config",id,3,request);
        return response;
    }
}
