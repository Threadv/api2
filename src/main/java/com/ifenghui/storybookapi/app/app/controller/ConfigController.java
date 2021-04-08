package com.ifenghui.storybookapi.app.app.controller;

import com.ifenghui.storybookapi.app.analysis.service.GroupRelevanceService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.response.GetAppNavButtonListResponse;
import com.ifenghui.storybookapi.app.app.response.GetAppNavStyleButtonListResponse;
import com.ifenghui.storybookapi.app.app.response.GetConfigByKeyResponse;
import com.ifenghui.storybookapi.app.app.entity.AppNavButton;
import com.ifenghui.storybookapi.app.app.entity.Config;
import com.ifenghui.storybookapi.app.app.service.AppNavButtonService;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.transaction.service.PaySubscriptionPriceService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@EnableAutoConfiguration
@Api(value = "配置管理", description = "配置管理")
@RequestMapping("/api/config")
public class ConfigController {


    @Autowired
    ConfigService configService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    AppNavButtonService appNavButtonService;

    @Autowired
    GroupRelevanceService groupRelevanceService;

    @Autowired
    UserService userService;

    @Autowired
    PaySubscriptionPriceService paySubscriptionPriceService;

    @ApiOperation(value = "获取配置信息",notes = "")
    @RequestMapping(value = "/getConfigByKey",method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(cacheNames = "story_config_controller_",key = "'storyconfigkey'+#p0")
    public GetConfigByKeyResponse getConfigByKey(
            @ApiParam(value = "key")@RequestParam String key
    ) {

        GetConfigByKeyResponse getConfigByKeyResponse=new GetConfigByKeyResponse();
        if(key.equals("version")){
            //马甲包固定版本号
            String userAgent = request.getHeader("User-Agent");

            if(userAgent.contains("appname:zhijianStory")){
                key = "zhijianStory_version";
            }
        }

        Config config=this.configService.getConfigByKey(key);

        getConfigByKeyResponse.setConfig(config);

        if(key.equals("app_open_notice")) {
            getConfigByKeyResponse.setConfig(null);
        }

//        if(ssToken != null && !ssToken.equals("")) {
//            Long userId = userService.checkAndGetCurrentUserId(ssToken);
//            boolean isNeedOpen = paySubscriptionPriceService.isNeedOpenNotice(userId);
//            if(key.equals("app_open_notice") && isNeedOpen) {
////                getConfigByKeyResponse.setConfig(config);
//            }
//        }

        return getConfigByKeyResponse;
    }

    @ApiOperation(value = "获取导航栏")
    @RequestMapping(value = "/nav_button_list", method = RequestMethod.GET)
    @ResponseBody
    GetAppNavButtonListResponse getAppNavButtonList(
            @ApiParam(value = "设备类型 1ios 2安卓 3ipad 4安卓pad")@RequestParam() Integer deviceType
    ){
        GetAppNavButtonListResponse response = new GetAppNavButtonListResponse();
        List<AppNavButton> appNavButtonList = appNavButtonService.getAppNavButtonListByDeviceType(deviceType, 1);
        if(appNavButtonList.size() == 4){
            if(appNavButtonList.get(0).getBgIcon() != null){
                response.setBgIcon(appNavButtonList.get(0).getBgIconUrl());
                response.setIsShowBg(1);
            } else {
                response.setIsShowBg(0);
            }
            response.setAppNavButtonList(appNavButtonList);
        } else {
            response.setIsShowBg(0);
        }
        return response;
    }

    @ApiOperation(value = "获取有主题导航栏")
    @RequestMapping(value = "/nav_style_button_list", method = RequestMethod.GET)
    @ResponseBody
    GetAppNavStyleButtonListResponse getAppNavStyleButtonList(
            @ApiParam(value = "设备类型 1ios 2安卓 3ipad 4安卓pad")@RequestParam() Integer deviceType
    ) {
        GetAppNavStyleButtonListResponse response = new GetAppNavStyleButtonListResponse();
        response.setAppNavContainStyleList(appNavButtonService.getAppNavContainStyleList(deviceType));
        return response;
    }
//    @ApiOperation(value = "补全故事新类型关联")
//    @RequestMapping(value = "/update_new_story_type", method = RequestMethod.GET)
//    @ResponseBody
//    BaseResponse updateNewStoryType(){
//        BaseResponse response = new BaseResponse();
//        groupRelevanceService.addGroupRelevanceByNewType();
//        return response;
//    }
//
//    @ApiOperation(value = "补全故事新类型关联测试")
//    @RequestMapping(value = "/update_new_story_type_test", method = RequestMethod.GET)
//    @ResponseBody
//    BaseResponse updateNewStoryTypeTest(){
//        BaseResponse response = new BaseResponse();
//        groupRelevanceService.addGroupRelevanceByNewTypeTest();
//        return response;
//    }
}
