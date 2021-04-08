package com.ifenghui.storybookapi.adminapi.controlleradmin;


import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.controller.StoryController;
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.response.GetSerialStoryDetailResponse;
import com.ifenghui.storybookapi.app.story.response.GetUserSerialStorysResponse;
import com.ifenghui.storybookapi.app.story.service.*;
import com.ifenghui.storybookapi.app.transaction.dao.BuyStoryRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.AdminResponseType;
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
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "故事集管理", description = "故事集管理api")
@RequestMapping("/adminapi/serial")
public class SerialAdminController {

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
    @RequestMapping(value = "/get_serial_list",method = RequestMethod.GET)
    @ResponseBody
    GetUserSerialStorysResponse getCommentSerialStoryPage(
            @ApiParam(value = "status") @RequestParam(required = false,defaultValue = "") Integer status,
            @ApiParam(value = "type") @RequestParam(required = false,defaultValue = "") Integer type,
        @ApiParam(value = "pageNo") @RequestParam(required = false) Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize,
            @ApiParam(value = "id")  Integer id
    ) throws ApiNotTokenException {
        if(pageNo==null){
            pageNo=0;
        }

        GetUserSerialStorysResponse response=new GetUserSerialStorysResponse();
        SerialStory serialStory=new SerialStory();
        if(status!=null){
            serialStory.setStatus(status);
        }
        if(type!=null){
            serialStory.setType(type);
        }
        if(id!=null){
            serialStory.setId(id.longValue());
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
            @ApiParam(value = "maxAmount") @RequestParam Integer maxAmount,
            @ApiParam(value = "price") @RequestParam Integer price,
            @ApiParam(value = "shareUrl") @RequestParam String shareUrl,
            @ApiParam(value = "shortIntro")  String shortIntro,
            @ApiParam(value = "iconData") String iconData,
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
        serialStory.setIntro(intro);
        serialStory.setMaxAmount(maxAmount);
        serialStory.setParentId(0);
        serialStory.setPrice(price);
        serialStory.setShareUrl(shareUrl);
        serialStory.setShortIntro(shortIntro);
        serialStory.setIcon(iconData);

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
            @ApiParam(value = "maxAmount")  Integer maxAmount,
            @ApiParam(value = "price")  Integer price,
            @ApiParam(value = "shareUrl")  String shareUrl,
            @ApiParam(value = "orderBy")  Integer orderBy,
            @ApiParam(value = "memberFree")  Integer memberFree,
            @ApiParam(value = "storyCount")  Integer storyCount,
            @ApiParam(value = "shortIntro")  String shortIntro,
            @ApiParam(value = "iconData")  String iconData,
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
        if(content!=null&&content.length()>4){
            serialStory.setContent(content);
        }
        if(intro!=null&&content.length()>4){
            serialStory.setIntro(intro);
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
        if(storyCount!=null){
            serialStory.setStoryCount(storyCount);
        }
        if(shortIntro!=null){
            serialStory.setShortIntro(shortIntro);
        }
        if(iconData!=null){
            serialStory.setIcon(iconData);
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


    @ApiOperation(value = "修改排序")
    @RequestMapping(value = "update_serial_position",method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse putDIpLabelStoryPosition(
            Integer[] ids
    ) throws ApiNotTokenException {
//        GroupRelevance groupRelevance=new GroupRelevance();

        SerialStory serialStory;
        for(int i=0;i<ids.length;i++){
            serialStory=serialStoryService.findOne(ids[i]);
            if(serialStory.getOrderBy()==ids.length-i){
                continue;
            }
            serialStory.setOrderBy(ids.length-i);

            serialStoryService.updateSerialStory(serialStory);
        }
//        groupRelevanceService.deleteGroupRelevance(id);

        return new BaseResponse(AdminResponseType.SUCCESS);
    }

}
