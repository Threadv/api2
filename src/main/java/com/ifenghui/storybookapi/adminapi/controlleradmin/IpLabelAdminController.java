package com.ifenghui.storybookapi.adminapi.controlleradmin;


import com.ifenghui.storybookapi.adminapi.controlleradmin.resp.GetIpBrandsResponse;
import com.ifenghui.storybookapi.adminapi.controlleradmin.resp.IpBrand;
import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.entity.IpLabel;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.response.GetIpLabelListResponse;
import com.ifenghui.storybookapi.app.story.response.GetIpLabelResponse;
import com.ifenghui.storybookapi.app.story.service.IpLabelService;
import com.ifenghui.storybookapi.app.story.service.IpLabelStoryService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.AdminResponseType;
import com.ifenghui.storybookapi.style.IpBrandStyle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "ipLabel管理", description = "ipLabel管理")
@RequestMapping("/adminapi/iplabel")
public class IpLabelAdminController {

    @Autowired
    StoryService storyService;

    @Autowired
    SerialStoryService serialStoryService;


    @Autowired
    IpLabelService ipLabelService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    IpLabelStoryService ipLabelStoryService;

    @Autowired
    CmsLogService cmsLogService;

    @ApiOperation(value = "get iplabel")
    @RequestMapping(value = "/get_ip_label",method = RequestMethod.GET)
    @ResponseBody
    GetIpLabelResponse getIpLabel(
            @ApiParam(value = "id") @RequestParam(required = false,defaultValue = "") Integer id
    ) throws ApiNotTokenException {


        IpLabel ipLabel=ipLabelService.findOne(id);

        GetIpLabelResponse response=new GetIpLabelResponse();
        response.setIpLabel(ipLabel);
        return response;
    }

    @ApiOperation(value = "添加iplabel")
    @RequestMapping(value = "/add_ip_label_by_ipbrand",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addIpLabel(
        @ApiParam(value = "status") @RequestParam(required = false,defaultValue = "") Integer status,
        @ApiParam(value = "ipBrandId") @RequestParam(required = false) Integer ipBrandId,
        @ApiParam(value = "content") @RequestParam String content,
         @ApiParam(value = "color")  String color,
        HttpServletRequest request
    ) throws ApiNotTokenException {

             if(color==null){
                 color="";
             }
        IpLabel ipLabel=new IpLabel();
        ipLabel.setColor(color);
        ipLabel.setCreateTime(new Date());
        ipLabel.setIpBrandId(ipBrandId);
        ipLabel.setContent(content);
        ipLabel.setIpId(0);
        ipLabel.setOrderBy(0);
        ipLabel.setParentId(0);
        ipLabel.setStatus(status);

        ipLabelService.addIpLabel(ipLabel);
        //添加操作日志
        cmsLogService.save("IpLabel",ipLabel.getId().intValue(),1,request);

        return new BaseResponse(AdminResponseType.SUCCESS);
    }

    @ApiOperation(value = "修改iplabel")
    @RequestMapping(value = "/update_ip_label",method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse updateIpLabel(
            @ApiParam(value = "id") @RequestParam(required = false) Integer id,
            @ApiParam(value = "status") @RequestParam(required = false,defaultValue = "") Integer status,
//            @ApiParam(value = "ipBrandId") @RequestParam(required = false) Integer ipBrandId,
            @ApiParam(value = "content") @RequestParam String content,
            @ApiParam(value = "color") @RequestParam String color,
            HttpServletRequest request
    ) throws ApiNotTokenException {
        IpLabel ipLabel=ipLabelService.findOne(id);
        if(color!=null){
            ipLabel.setColor(color);
        }
        if(content!=null){
            ipLabel.setContent(content);
        }
        if(status!=null){
            ipLabel.setStatus(status);
        }


        ipLabelService.addIpLabel(ipLabel);
        //添加操作日志
        cmsLogService.save("IpLabel",ipLabel.getId().intValue(),2,request);

        return new BaseResponse(AdminResponseType.SUCCESS);
    }

    @ApiOperation(value = "删除iplabel")
    @RequestMapping(value = "/delete_ip_label",method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse deleteIpLabel(
            @ApiParam(value = "id") @RequestParam(required = true) Integer id,
            HttpServletRequest request
    ) throws ApiNotTokenException {


        ipLabelService.deleteIpLabel(id);
//        同时删除label关联故事
        ipLabelStoryService.deleteIpLabelStoryByipLabelId(id);
        //添加操作日志
        cmsLogService.save("IpLabel",id,3,request);

        return new BaseResponse(AdminResponseType.SUCCESS);
    }

    @ApiOperation(value = "修改排序")
    @RequestMapping(value = "update_ip_label_position",method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse putDisPlayGroupStoryPosition(
            Integer[] ids
    ) throws ApiNotTokenException {
//        GroupRelevance groupRelevance=new GroupRelevance();

        IpLabel ipLabel;
        for(int i=0;i<ids.length;i++){
            ipLabel=ipLabelService.findOne(ids[i]);
            if(ipLabel.getOrderBy()==ids.length-i){
                continue;
            }
            ipLabel.setOrderBy(ids.length-i);

            ipLabelService.updateIpLabel(ipLabel);
        }
//        groupRelevanceService.deleteGroupRelevance(id);

        return new BaseResponse(AdminResponseType.SUCCESS);
    }
}
