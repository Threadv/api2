package com.ifenghui.storybookapi.adminapi.controlleradmin;


import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.entity.IpLabel;
import com.ifenghui.storybookapi.app.story.entity.IpLabelStory;
import com.ifenghui.storybookapi.app.story.service.IpLabelService;
import com.ifenghui.storybookapi.app.story.service.IpLabelStoryService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.AdminResponseType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "iplabel和故事关联", description = "iplabel和故事关联")
@RequestMapping("/adminapi/iplabelstory")
public class IpLabelStoryAdminController {

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


    @ApiOperation(value = "添加iplabelstory")
    @RequestMapping(value = "/add_ip_label_story",method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse addIpLabelStory(
        @ApiParam(value = "ipLabelId") @RequestParam(required = false,defaultValue = "") Integer ipLabelId,
        @ApiParam(value = "storyId") @RequestParam(required = false) Integer storyId,
        HttpServletRequest request
    ) throws ApiNotTokenException {
        IpLabelStory ipLabelStory=new IpLabelStory();
        ipLabelStory.setIpLabelId(ipLabelId);
        ipLabelStory.setCreateTime(new Date());
        ipLabelStory.setIpId(0);
        ipLabelStory.setIpLabelParentId(0);
        ipLabelStory.setStoryId(storyId);
        ipLabelStory.setOrderBy(0);

        ipLabelStoryService.addIpLabelStory(ipLabelStory);
        //添加操作日志
        cmsLogService.save("IpLabelStory",ipLabelStory.getId().intValue(),1,request);

        return new BaseResponse(AdminResponseType.SUCCESS);
    }

    @ApiOperation(value = "删除iplabelstory")
    @RequestMapping(value = "/delete_ip_label_story",method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse deleteIpLabelStory(
            @ApiParam(value = "id") @RequestParam(required = false,defaultValue = "") Integer id,
            HttpServletRequest request
    ) throws ApiNotTokenException {

        ipLabelStoryService.deleteIpLabelStory(id);
        //添加操作日志
        cmsLogService.save("IpLabelStory",id,3,request);

        return new BaseResponse(AdminResponseType.SUCCESS);
    }

//    @ApiOperation(value = "修改iplabel")
//    @RequestMapping(value = "/update_ip_label_story",method = RequestMethod.PUT)
//    @ResponseBody
//    BaseResponse updateIpLabel(
//            @ApiParam(value = "id") @RequestParam(required = false) Integer id,
//            @ApiParam(value = "status") @RequestParam(required = false,defaultValue = "") Integer status,
////            @ApiParam(value = "ipBrandId") @RequestParam(required = false) Integer ipBrandId,
//            @ApiParam(value = "content") @RequestParam String content,
//            @ApiParam(value = "color") @RequestParam String color
//    ) throws ApiNotTokenException {
//        IpLabel ipLabel=new IpLabel();
//        if(color!=null){
//            ipLabel.setColor(color);
//        }
//        if(content!=null){
//            ipLabel.setContent(content);
//        }
//        if(status!=null){
//            ipLabel.setStatus(status);
//        }
//
//
//        ipLabelService.addIpLabel(ipLabel);
//
//
//        return new BaseResponse(AdminResponseType.SUCCESS);
//    }

//    @ApiOperation(value = "删除iplabel")
//    @RequestMapping(value = "/delete_ip_label_story",method = RequestMethod.PUT)
//    @ResponseBody
//    BaseResponse deleteIpLabel(
//            @ApiParam(value = "id") @RequestParam(required = true) Integer id
//    ) throws ApiNotTokenException {
//
//
//        ipLabelService.deleteIpLabel(id);
//
//
//        return new BaseResponse(AdminResponseType.SUCCESS);
//    }
@ApiOperation(value = "修改排序")
@RequestMapping(value = "update_ip_label_story_position",method = RequestMethod.PUT)
@ResponseBody
BaseResponse putDIpLabelStoryPosition(
        Integer[] ids
) throws ApiNotTokenException {
//        GroupRelevance groupRelevance=new GroupRelevance();

    IpLabelStory ipLabelStory;
    for(int i=0;i<ids.length;i++){
        ipLabelStory=ipLabelStoryService.findOne(ids[i]);
        if(ipLabelStory.getOrderBy()==ids.length-i){
            continue;
        }
        ipLabelStory.setOrderBy(ids.length-i);

        ipLabelStoryService.updateIpLabelStory(ipLabelStory);
    }
//        groupRelevanceService.deleteGroupRelevance(id);

    return new BaseResponse(AdminResponseType.SUCCESS);
}

}
