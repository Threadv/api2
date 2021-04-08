package com.ifenghui.storybookapi.adminapi;

import com.ifenghui.storybookapi.adminapi.service.CleanCacheService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;

import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
//@EnableAutoConfiguration
@Api(value = "缓存管理", description = "缓存管理")
@RequestMapping("/adminapi/cacheclear")
public class CleanCacheController {
    @Autowired
    UserDao userDao;

    @Autowired
    StoryDao storyDao;

    @Autowired
    CleanCacheService cleanCacheService;

    @CacheEvict(cacheNames = "ship_user_v1",key = "'shipuser_v4'+#p0")
    @ApiOperation(value = "清理用户缓存",notes = "")
    @RequestMapping(value = "/cleanUserCache",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse cleanUserCache(
            @ApiParam(value = "userId")@RequestParam Integer userId){
        BaseResponse response = new BaseResponse();
        return response;
    }

    @CacheEvict(cacheNames = "getUserExtendByUserId",key = "'getUserExtendByUserId'+#p0")
    @ApiOperation(value = "清理userExtend缓存",notes = "")
    @RequestMapping(value = "/cleanUserExtendCache",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse cleanUserExtendCache(
            @ApiParam(value = "userId")@RequestParam Integer userId){
        BaseResponse response = new BaseResponse();
        return response;
    }



@Caching(evict = {@CacheEvict(cacheNames = "findAllStoryLabelsByStoryId",key = "'findAllStoryLabelsByStoryId'+#p0"),
        @CacheEvict(cacheNames = "storyship_story_v9",key = "'storyship_story_v9'+#p0")})

    @ApiOperation(value = "清理故事缓存",notes = "")
    @RequestMapping(value = "/cleanStroyCache",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse cleanStroyCache(
            @ApiParam(value = "storyId")@RequestParam Integer storyId){
        BaseResponse response = new BaseResponse();
        return response;
    }

    /*
     @CacheEvict(cacheNames = "findByStoryIdAndEngineTypeAndStatus",key = "'findByStoryIdAndEngineTypeAndStatus'+#root.args[0].storyId+'_'+#root.args[0].engineType+'_'+#root.args[0].status")
    public void clearStoryPackageCache(Integer storyId,Integer engineType,Integer status);
     */
    @CacheEvict(cacheNames = "findByStoryIdAndEngineTypeAndStatus_v5",key = "'findByStoryIdAndEngineTypeAndStatus_v5'+#p0+'_'+#p1+'_'+#p2")
    @ApiOperation(value = "清理故事包缓存",notes = "")
    @RequestMapping(value = "/cleanStroyPackageCache",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse cleanStroyPackageCache(Integer storyId,Integer engineType,Integer status){
        BaseResponse response = new BaseResponse();
        return response;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "ship_index_ads_v2",key = "'ship_index_ads_v4'+#p0+'_'+#p1"),
            @CacheEvict(cacheNames = "ship_index_ads_v2",key = "'ship_index_ads_v4'+#p0+'_'"),
            @CacheEvict(cacheNames = "ship_index_ads_v2",key = "'ship_index_ads_v4'+#p0+'_1'"),
            @CacheEvict(cacheNames = "ship_index_ads_v2",key = "'ship_index_ads_v4'+#p0+'_2'")})



    @ApiOperation(value = "清理故事包缓存",notes = "")
    @RequestMapping(value = "/cleanAdsCache",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse cleanAdsCache(Integer status,String platform){
        BaseResponse response = new BaseResponse();
        return response;
    }

//    @CacheEvict(cacheNames = "ship_index_ads",key = "'ship_index_ads'+#p0+'_'+#p1")
    @ApiOperation(value = "清理用户购买关系缓存-测试服可用",notes = "")
    @RequestMapping(value = "/cleanUserBuy",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse cleanUserBuy(Integer userId){

        long count=storyDao.count();
        long pageCount=count/10;
        List<Story> storys;
        for(int i=0;i<pageCount;i++){
            storys=storyDao.findAll(new PageRequest(i,10,new Sort(Sort.Direction.ASC,"id"))).getContent();
            for(Story story:storys){
                cleanCacheService.cleanUserBuyRecord(userId,story.getId().intValue());
            }
        }
        BaseResponse response = new BaseResponse();
        return response;
    }



    @ApiOperation(value = "后台调用，后台维护数据后调用会清理gouup，lesson相关缓存",notes = "")
    @RequestMapping(value = "/cleanCacheGroup",method = RequestMethod.GET)
    @ResponseBody
    @Caching(evict = {
            @CacheEvict(cacheNames = "lessonItemRelate_findOneByStoryId",allEntries = true),
            @CacheEvict(cacheNames = "lessonItemRelate_countByStoryId",allEntries = true),
            @CacheEvict(cacheNames = "lessonItemRelate_findOneByVideoId",allEntries = true),
            @CacheEvict(cacheNames = "CouponStoryExchange_findOne_",allEntries = true),
            @CacheEvict(cacheNames = "LessonItem_findOne_v1_",allEntries = true),
            @CacheEvict(cacheNames = "Lesson_findAll",allEntries = true),
            @CacheEvict(cacheNames = "SerialStory_findOne_v3",allEntries = true),
            @CacheEvict(cacheNames = "getGroupRelevancesByGroupIdAndStatus",allEntries = true),
            @CacheEvict(cacheNames = "DisplayGroup_findOne",allEntries = true),
            @CacheEvict(cacheNames = "storyship_story_v9",allEntries = true),
            @CacheEvict(cacheNames = "findByStoryIdAndEngineTypeAndStatus_v5",allEntries = true),
            @CacheEvict(cacheNames = "getDisplayGroupsByNewCategory",allEntries = true),
            @CacheEvict(cacheNames = "getCommonSerialStoryPageOnCache",allEntries = true),
            @CacheEvict(cacheNames = "ktx_labek_findone_",allEntries = true),
            @CacheEvict(cacheNames = "WeekPlanLabelDao_findOne",allEntries = true),
            @CacheEvict(cacheNames = "getWeekPlanLabelStoriesByTargetTypeAndTargetValue",allEntries = true),
            @CacheEvict(cacheNames = "WeekPlanLabelDaoByLabelType",allEntries = true),
            @CacheEvict(cacheNames = "getRadioList_v3",allEntries = true),
            @CacheEvict(cacheNames = "story_config",allEntries = true),
            @CacheEvict(cacheNames = "story_config_controller_",allEntries = true)


    })
    public BaseResponse cleanCacheGroup(){


        BaseResponse response = new BaseResponse();
        return response;
    }
}
