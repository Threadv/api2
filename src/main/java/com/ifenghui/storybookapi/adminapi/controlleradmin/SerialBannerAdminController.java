package com.ifenghui.storybookapi.adminapi.controlleradmin;


import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialBanner;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.response.GetSerialBannerResponse;
import com.ifenghui.storybookapi.app.story.response.GetSerialBannersResponse;
import com.ifenghui.storybookapi.app.story.response.GetUserSerialStorysResponse;
import com.ifenghui.storybookapi.app.story.service.IpLabelService;
import com.ifenghui.storybookapi.app.story.service.SerialBannerService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "故事集管理", description = "故事集管理api")
@RequestMapping("/adminapi/serial/banner")
public class SerialBannerAdminController {

    @Autowired
    StoryService storyService;

    @Autowired
    SerialStoryService serialStoryService;


    @Autowired
    SerialBannerService serialBannerService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    CmsLogService cmsLogService;


    @ApiOperation(value = "获得系列故事集列表")
    @RequestMapping(value = "/get_serial_banner_list",method = RequestMethod.GET)
    @ResponseBody
    GetSerialBannersResponse getSerialBanners(
        @ApiParam(value = "serialId") @RequestParam(required = false,defaultValue = "") Integer serialId,
        @ApiParam(value = "status") @RequestParam(required = false,defaultValue = "") Integer status,
        @ApiParam(value = "pageNo") @RequestParam(required = false) Integer pageNo,
        @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        if(pageNo==null){
            pageNo=0;
        }

        GetSerialBannersResponse response=new GetSerialBannersResponse();
        SerialBanner serialBanner=new SerialBanner();
        if(status!=null){
            serialBanner.setStatus(status);
        }
        if(serialId!=null){
            serialBanner.setSerialId(serialId);
        }
        Page<SerialBanner> serialBanners = serialBannerService.findAll(serialBanner,new PageRequest(pageNo,pageSize));

        response.setSerialBanners(serialBanners.getContent());
        response.setJpaPage(serialBanners);
        return response;
    }

    @ApiOperation(value = "查询单个")
    @RequestMapping(value = "/get_serial_banner",method = RequestMethod.GET)
    @ResponseBody
    GetSerialBannerResponse getSerialBanner(
            @ApiParam(value = "id") @RequestParam(required = false,defaultValue = "") Integer id
    ) throws ApiNotTokenException {

        SerialBanner serialBanners = serialBannerService.findOne(id);
        GetSerialBannerResponse response=new GetSerialBannerResponse();
        response.setSerialBanner(serialBanners);
//        response.setJpaPage(serialBanners);
        return response;
    }

    @ApiOperation(value = "添加")
    @RequestMapping(value = "/add_serial_banner",method = RequestMethod.PUT)
    @ResponseBody
    GetSerialBannerResponse addSerialBanner(
            @ApiParam(value = "serialId") @RequestParam(required = false,defaultValue = "") Integer serialId,
            @ApiParam(value = "banner") @RequestParam(required = false,defaultValue = "") String banner,
            @ApiParam(value = "position") @RequestParam(required = false,defaultValue = "") Integer position,
            @ApiParam(value = "status") @RequestParam(required = false,defaultValue = "") Integer status,
            @ApiParam(value = "title") @RequestParam(required = false,defaultValue = "") String title,
            HttpServletRequest request
    ) throws ApiNotTokenException {

        SerialBanner serialBanners =new SerialBanner();
        serialBanners.setSerialId(serialId);
        serialBanners.setBanner(banner);
        serialBanners.setCreateTime(new Date());
        serialBanners.setPosition(position);
        serialBanners.setStatus(status);
        serialBanners.setTitle(title);

        serialBanners=serialBannerService.addSerialBanner(serialBanners);
        //添加操作日志
        cmsLogService.save("SerialBanner",serialBanners.getId().intValue(),1,request);
        GetSerialBannerResponse response=new GetSerialBannerResponse();
        response.setSerialBanner(serialBanners);
        return response;
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update_serial_banner",method = RequestMethod.POST)
    @ResponseBody
    GetSerialBannerResponse updateSerialBanner(
            @ApiParam(value = "id") @RequestParam(required = false,defaultValue = "") Integer id,
//            @ApiParam(value = "serialId") @RequestParam(required = false,defaultValue = "") Integer serialId,
            @ApiParam(value = "banner") @RequestParam(required = false,defaultValue = "") String banner,
            @ApiParam(value = "position") @RequestParam(required = false,defaultValue = "") Integer position,
            @ApiParam(value = "status") @RequestParam(required = false,defaultValue = "") Integer status,
            @ApiParam(value = "title") @RequestParam(required = false,defaultValue = "") String title,
            HttpServletRequest request
    ) throws ApiNotTokenException {

        SerialBanner serialBanners =serialBannerService.findOne(id);
//        serialBanners.setSerialId(serialId);
        if(banner!=null){
            serialBanners.setBanner(banner);
        }
        if(position!=null){
            serialBanners.setPosition(position);
        }
        if(status!=null){
            serialBanners.setStatus(status);
        }
        if(title!=null){
            serialBanners.setTitle(title);
        }

        serialBanners= serialBannerService.updateSerialBanner(serialBanners);
        //添加操作日志
        cmsLogService.save("SerialBanner",serialBanners.getId().intValue(),2,request);
        GetSerialBannerResponse response=new GetSerialBannerResponse();
        response.setSerialBanner(serialBanners);
        return response;
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete_serial_banner",method = RequestMethod.DELETE)
    @ResponseBody
    ApiResponse updateSerialBanner(
            @ApiParam(value = "id") @RequestParam(required = false,defaultValue = "") Integer id,
            HttpServletRequest request
    ) throws ApiNotTokenException {

        serialBannerService.deleteSerialBanner(id);
        //添加操作日志
        cmsLogService.save("SerialBanner",id,1,request);
        ApiResponse response=new BaseResponse();

        return response;
    }
}
