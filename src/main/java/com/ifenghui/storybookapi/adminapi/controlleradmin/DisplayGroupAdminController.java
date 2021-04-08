package com.ifenghui.storybookapi.adminapi.controlleradmin;


import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import com.ifenghui.storybookapi.app.analysis.entity.GroupRelevance;
import com.ifenghui.storybookapi.app.analysis.response.GroupStoryRelevanceResponse;
import com.ifenghui.storybookapi.app.analysis.service.GroupRelevanceService;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.service.DisplayGroupService;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.response.GetStoriesResponse;
import com.ifenghui.storybookapi.app.story.response.GetStoryDetailByIdResponse;
import com.ifenghui.storybookapi.app.story.response.GetStoryGroupResponse;
import com.ifenghui.storybookapi.app.story.response.GetStoryGroupsResponse;
import com.ifenghui.storybookapi.app.story.service.IpLabelService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.AdminResponseType;
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

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "故事管理", description = "故事管理api")
@RequestMapping("/adminapi/displaygroup")
public class DisplayGroupAdminController {

    @Autowired
    StoryService storyService;

    @Autowired
    DisplayGroupService displayGroupService;

    @Autowired
    GroupRelevanceService groupRelevanceService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    CmsLogService cmsLogService;


    @ApiOperation(value = "混合查询故事")
    @RequestMapping(value = "/get_group_list",method = RequestMethod.GET)
    @ResponseBody
    GetStoryGroupsResponse getDisPlayGroupAdminList(
        Integer pageNo,
        Integer pageSize
    ) throws ApiNotTokenException {
        if(pageNo==null){
            pageNo=0;
        }
        if(pageSize==null){
            pageSize=20;
        }

        GetStoryGroupsResponse response=new GetStoryGroupsResponse();

        DisplayGroup displayGroup=new DisplayGroup();

        Page<DisplayGroup> displayGroupPage=displayGroupService.getAllGroups(displayGroup,new PageRequest(pageNo,pageSize));

        response.setGroups(displayGroupPage.getContent());
        response.setJpaPage(displayGroupPage);
        return response;
    }

    @ApiOperation(value = "查询分组")
    @RequestMapping(value = "/get_group",method = RequestMethod.GET)
    @ResponseBody
    GetStoryGroupResponse getDisplayGroup(
            @ApiParam(value = "id") Integer id
    ) throws ApiNotTokenException {

        GetStoryGroupResponse response=new GetStoryGroupResponse();
        DisplayGroup displayGroup= displayGroupService.findOne(id);
        response.setGroup(displayGroup);
        return response;
    }

    @ApiOperation(value = "修改故事")
    @RequestMapping(value = "/update_group",method = RequestMethod.PUT)
    @ResponseBody
    GetStoryGroupResponse updateGroup(
            @ApiParam(value = "id") Integer id,
            @ApiParam(value = "name") String name,
            @ApiParam(value = "content") String content,
            @ApiParam(value = "targetType") Integer targetType,
            HttpServletRequest request
    ) throws ApiNotTokenException {
        DisplayGroup group=displayGroupService.findOne(id);
        if(name!=null){
            group.setName(name);
        }
        if(content!=null){
            group.setContent(content);
        }
        if(targetType!=null){
            group.setTargetType(targetType);
        }
        group=displayGroupService.update(group);
        //添加操作日志
        cmsLogService.save("DisplayGroup",group.getId().intValue(),2,request);

        GetStoryGroupResponse response=new GetStoryGroupResponse();
        response.setGroup(group);
        return response;
    }

    @ApiOperation(value = "增加故事")
    @RequestMapping(value = "/add_group",method = RequestMethod.POST)
    @ResponseBody
    GetStoryGroupResponse addGroup(
            @ApiParam(value = "id") Integer id,
            @ApiParam(value = "name") String name,
            @ApiParam(value = "content") String content,
            @ApiParam(value = "targetType") Integer targetType,
            HttpServletRequest request
    ) throws ApiNotTokenException {
        DisplayGroup group=new DisplayGroup();

            group.setName(name);
            group.setContent(content);
            group.setTargetType(targetType);

        group=displayGroupService.add(group);
        //添加操作日志
        cmsLogService.save("DisplayGroup",group.getId().intValue(),1,request);

        GetStoryGroupResponse response=new GetStoryGroupResponse();
        response.setGroup(group);
        return response;
    }


    @ApiOperation(value = "混合分组故事关联和故事")
    @RequestMapping(value = "/get_group_story_list",method = RequestMethod.GET)
    @ResponseBody
    GroupStoryRelevanceResponse getDisPlayGroupAndStoryAdminList(
            Integer pageNo,
            Integer pageSize,
            Integer groupId
    ) throws ApiNotTokenException {
        if(pageNo==null){
            pageNo=0;
        }
        if(pageSize==null){
            pageSize=20;
        }
        GroupStoryRelevanceResponse response=new GroupStoryRelevanceResponse();
        DisplayGroup displayGroup=new DisplayGroup();
        Page<GroupRelevance> displayGroupPage=groupRelevanceService.getRevelanceByGroupId(groupId.longValue(),pageNo,pageSize);
        response.setJpaPage(displayGroupPage);
        response.setGroupRelevances(displayGroupPage.getContent());
        return response;
    }

    @ApiOperation(value = "增加故事关联到分组")
    @RequestMapping(value = "/add_group_story",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addDisPlayGroupAndStoryAdminList(
                    Integer groupId,
                    Integer storyId
            ) throws ApiNotTokenException {
        GroupRelevance groupRelevance=new GroupRelevance();

        groupRelevanceService.addGroupRelevance(storyId.longValue(),groupId,1,0,0);

        return new BaseResponse(AdminResponseType.SUCCESS);
    }

    @ApiOperation(value = "删除关联")
    @RequestMapping(value = "/delete_group_story",method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse deleteDisPlayGroupAndStory(
                    Integer id
            ) throws ApiNotTokenException {
        GroupRelevance groupRelevance=new GroupRelevance();

        groupRelevanceService.deleteGroupRelevance(id);

        return new BaseResponse(AdminResponseType.SUCCESS);
    }

    @ApiOperation(value = "修改排序")
    @RequestMapping(value = "update_group_story_position",method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse putDisPlayGroupStoryPosition(
            Integer[] ids
    ) throws ApiNotTokenException {
        GroupRelevance groupRelevance=new GroupRelevance();

        GroupRelevance groupRelevanceItem;
        for(int i=0;i<ids.length;i++){
            groupRelevanceItem=groupRelevanceService.finOne(ids[i]);
            if(groupRelevanceItem.getOrderBy()==ids.length-i){
                continue;
            }
            groupRelevanceItem.setOrderBy(ids.length-i);

            groupRelevanceService.update(groupRelevanceItem);
        }
//        groupRelevanceService.deleteGroupRelevance(id);

        return new BaseResponse(AdminResponseType.SUCCESS);
    }
}
