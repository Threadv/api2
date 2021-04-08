package com.ifenghui.storybookapi.adminapi.controlleradmin;


import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.response.GetSerialStoryDetailResponse;
import com.ifenghui.storybookapi.app.story.response.GetUserSerialStorysResponse;
import com.ifenghui.storybookapi.app.story.service.IpLabelService;
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
@Api(value = "故事集中故事管理", description = "故事集中故事管理")
@RequestMapping("/adminapi/serial/story")
public class SerialStoryAdminController {

    @Autowired
    StoryService storyService;

    @Autowired
    SerialStoryService serialStoryService;


    @Autowired
    IpLabelService ipLabelService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    CmsLogService cmsLogService;


    @ApiOperation(value = "获得系列故事集列表")
    @RequestMapping(value = "/get_serial_story_list",method = RequestMethod.GET)
    @ResponseBody
    GetUserSerialStorysResponse getCommentSerialStoryPage(
        @ApiParam(value = "status") @RequestParam(required = false,defaultValue = "") Integer status,
        @ApiParam(value = "pageNo") @RequestParam(required = false) Integer pageNo,
        @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        if(pageNo==null){
            pageNo=0;
        }

        GetUserSerialStorysResponse response=new GetUserSerialStorysResponse();
        SerialStory serialStory=new SerialStory();
        if(status!=null){
            serialStory.setStatus(status);
        }
        Page<SerialStory> serialStoryPage = serialStoryService.getSerialStoryList(serialStory,new PageRequest(pageNo,pageSize));

        response.setSerialStoryList(serialStoryPage.getContent());
        response.setJpaPage(serialStoryPage);
        return response;
    }

    @ApiOperation(value = "增加系列")
    @RequestMapping(value = "/add_serial",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addSerial(
            @ApiParam(value = "status") @RequestParam(required = false,defaultValue = "") Integer status,
            @ApiParam(value = "ipBrandId") @RequestParam(required = false) Integer ipBrandId,
            @ApiParam(value = "banner") @RequestParam String banner,
            @ApiParam(value = "name") @RequestParam String name,
            @ApiParam(value = "content") @RequestParam String content,
            @ApiParam(value = "intro") @RequestParam String intro,
            @ApiParam(value = "shortIntro") @RequestParam String shortIntro,
            @ApiParam(value = "maxAmount") @RequestParam Integer maxAmount,
            @ApiParam(value = "price") @RequestParam Integer price,
            @ApiParam(value = "shareUrl") @RequestParam String shareUrl,
            HttpServletRequest request
    ) throws ApiNotTokenException {
        SerialStory serialStory=new SerialStory();
        serialStory.setIpBrandId(ipBrandId);
        serialStory.setBanner(banner);
        serialStory.setStatus(status);
        serialStory.setCreateTime(new Date());
        serialStory.setName(name);
        serialStory.setOrderBy(0);
        serialStory.setContent(content);
        serialStory.setShortIntro(shortIntro);
        serialStory.setIntro(intro);
        serialStory.setMaxAmount(maxAmount);
        serialStory.setParentId(0);
        serialStory.setPrice(price);
        serialStory.setShareUrl(shareUrl);

        serialStoryService.addSerialStory(serialStory);
        //添加操作日志
        cmsLogService.save("SerialStory",serialStory.getId().intValue(),1,request);

        return new BaseResponse();
    }

    @ApiOperation(value = "update系列")
    @RequestMapping(value = "/update_serial",method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse updateSerial(
            @ApiParam(value = "id") @RequestParam(required = false,defaultValue = "") Integer id,
            @ApiParam(value = "status") @RequestParam(required = false,defaultValue = "") Integer status,
            @ApiParam(value = "ipBrandId") @RequestParam(required = false) Integer ipBrandId,
            @ApiParam(value = "banner")  String banner,
            @ApiParam(value = "name")  String name,
            @ApiParam(value = "content")  String content,

            @ApiParam(value = "intro")  String intro,
            @ApiParam(value = "shortIntro")  String shortIntro,
            @ApiParam(value = "maxAmount")  Integer maxAmount,
            @ApiParam(value = "price")  Integer price,
            @ApiParam(value = "shareUrl")  String shareUrl,
            @ApiParam(value = "orderBy")  Integer orderBy,
             @ApiParam(value = "memberFree")  Integer memberFree,
            HttpServletRequest request

    ) throws ApiNotTokenException {
        SerialStory serialStory=serialStoryService.findOne(id);
        if(ipBrandId!=null){
            serialStory.setIpBrandId(ipBrandId);
        }
        if(banner!=null){
            serialStory.setBanner(banner);
        }
        if(status!=null){
            serialStory.setStatus(status);
        }
        if(name!=null){
            serialStory.setName(name);
        }
        if(content!=null){
            serialStory.setContent(content);
        }
        if(intro!=null){
            serialStory.setIntro(intro);
        }
        if(shortIntro!=null){
            serialStory.setShortIntro(shortIntro);
        }
        if(maxAmount!=null){
            serialStory.setMaxAmount(maxAmount);
        }
        if(price!=null){
            serialStory.setPrice(price);
        }
        if(shareUrl!=null){
            serialStory.setShareUrl(shareUrl);
        }
        if(orderBy!=null){
            serialStory.setOrderBy(orderBy);
        }
        if(memberFree!=null){
            serialStory.setMemberFree(memberFree);
        }

        serialStoryService.addSerialStory(serialStory);

        //添加操作日志
        cmsLogService.save("SerialStory",serialStory.getId().intValue(),2,request);
        return new BaseResponse();
    }

    @ApiOperation(value = "获得系列")
    @RequestMapping(value = "/get_serial",method = RequestMethod.GET)
    @ResponseBody
    GetSerialStoryDetailResponse GetSerial(
            @ApiParam(value = "id") @RequestParam(required = false,defaultValue = "") Integer id
    ) throws ApiNotTokenException {

        GetSerialStoryDetailResponse response=new GetSerialStoryDetailResponse();
        response.setSerialStory(serialStoryService.findOne(id));


        return response;
    }


}
