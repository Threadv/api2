package com.ifenghui.storybookapi.adminapi.controlleradmin;


import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.response.*;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "故事管理", description = "故事管理api")
@RequestMapping("/adminapi/story")
public class StoryAdminController {

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


    @ApiOperation(value = "混合查询故事")
    @RequestMapping(value = "/get_story_list",method = RequestMethod.GET)
    @ResponseBody
    GetStoriesResponse getStoriesResponse(
        @ApiParam(value = "keyword") @RequestParam(required = false,defaultValue = "") String keyword,
        Integer serialId,
        Integer pageNo,
        Integer pageSize
    ) throws ApiNotTokenException {
        if(pageNo==null){
            pageNo=0;
        }
        if(pageSize==null){
            pageSize=20;
        }


        GetStoriesResponse response=new GetStoriesResponse();

        Story story=new Story();
        story.setName(keyword);
        if(serialId!=null){
            story.setSerialStoryId(serialId.longValue());
        }


        Page<Story> stories=storyService.findAllByStory(story,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));

        response.setStories(stories.getContent());
        response.setJpaPage(stories);
        return response;
    }

    @ApiOperation(value = "查询故事")
    @RequestMapping(value = "/get_story",method = RequestMethod.GET)
    @ResponseBody
    GetStoryDetailByIdResponse findStory(
            @ApiParam(value = "id") Integer id
    ) throws ApiNotTokenException {

        GetStoryDetailByIdResponse response=new GetStoryDetailByIdResponse();
        Story stories=storyService.getStoryDetailById(id.longValue(),0L);
        response.setStory(stories);
        return response;
    }

    @ApiOperation(value = "修改故事")
    @RequestMapping(value = "/update_story",method = RequestMethod.PUT)
    @ResponseBody
    GetStoryDetailByIdResponse updateStory(
            @ApiParam(value = "id") Integer id,
            Integer serialId,
            HttpServletRequest request
    ) throws ApiNotTokenException {
        Story story=storyService.getStory(id.longValue());
        if(serialId!=null){
            story.setSerialStoryId(serialId.longValue());
        }

        story=storyService.saveStory(story);

        //添加操作日志
        cmsLogService.save("Story",story.getId().intValue(),2,request);
        GetStoryDetailByIdResponse response=new GetStoryDetailByIdResponse();
        response.setStory(story);
        return response;
    }

}
